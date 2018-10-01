package edu.haverford.cs.squirrelfacts;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * TODO: Implement all of this...
 */
public class SquirrelListAdapter extends BaseAdapter implements ListAdapter {
        private SquirrelList mSquirrelsList;
        private SquirrelList mOgSquirrelsList;

    public SquirrelListAdapter(Context context, SquirrelList sl) {
        super();
        mSquirrelsList = sl;
        mOgSquirrelsList = sl;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        mSquirrelsList.addObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        mSquirrelsList.removeObserver(dataSetObserver);
    }

    @Override
    public int getCount() {
        return mSquirrelsList.size();
    }

    @Override
    public Squirrel getItem(int i) {
        return mSquirrelsList.getItem(i);
    }




    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewGroup parent = viewGroup;
        final Squirrel s = getItem(i);
        final int pos =i;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.squirrel_item_template, parent, false);
        }
        TextView name = (TextView) view.findViewById(R.id.squirrelName);
        TextView location = (TextView) view.findViewById(R.id.squirrelLocation);
        name.setText(s.getName());
        location.setText(s.getLocation());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent.getContext(), SquirrelInfoActivity.class);
                i.putExtra("name", s.getName());
                i.putExtra("location", s.getLocation());
                i.putExtra("picture", s.getPicture());
                //check getContext()
                parent.getContext().startActivity(i);
            }
        });
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public void add(Squirrel squirrel) {
        mSquirrelsList.addToFront(squirrel);
    }

    public void clear() {
        mSquirrelsList.clear();
    }
}
