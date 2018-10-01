package edu.haverford.cs.squirrelfacts;

import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.lang.StringBuilder;

import android.view.View;



/**
 * Downloads JSON, parses each squirrel, and adds it to the collection via a task.
 */
class GetNewSquirrelsTask extends AsyncTask<String, Void, JSONArray> {

    /**
     * TODO: Implement this method to download a list of squirrels and parse it
     * @param strings
     * @return
     */
    protected SquirrelListAdapter mListAdapter;

    public GetNewSquirrelsTask(SquirrelListAdapter adapter) {
        mListAdapter = adapter;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        JSONArray jsonArray = null;
        String jsonString = null;
        String line;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;

        try {
            URL u = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }

        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        String name;
        String location;
        String picture;
        for (int i = 0; i < result.length(); i++) {
            try {
                name = (String) result.getJSONObject(i).get("name");
                location = (String) result.getJSONObject(i).get("location");
                picture = (String) result.getJSONObject(i).get("picture");
                Squirrel newSquirrel = new Squirrel(name,location,picture);
                mListAdapter.add(newSquirrel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MainActivity extends AppCompatActivity {
    private SquirrelListAdapter mAdapter;
    private Button clearButton;
    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.squirrel_list);
//        Squirrel s = new Squirrel("Black Squirrel",
//                "Haverford, PA",
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Black_Squirrel.jpg/220px-Black_Squirrel.jpg");
        SquirrelList sl = (new SquirrelList());
//        for (int i = 0; i < 100; i++) {
//            sl.addToFront(s);
//        }
        ArrayList<Squirrel> al = sl.toArrayList();
        //will the final being there cause a problem?
//        SquirrelArrayAdapter adapter = new SquirrelArrayAdapter(this, al);
        /**
         * TODO: Uncomment this and make sure you can use your adapter
         */
        final SquirrelListAdapter adapter = new SquirrelListAdapter(this, sl);
        mAdapter = adapter;
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onInvalidated() {
                if (mAdapter != null) {
                    mAdapter.notifyDataSetInvalidated();

                }
            }
        });
        listView.setAdapter(adapter);
        GetNewSquirrelsTask task = new GetNewSquirrelsTask(adapter);
        task.execute("https://raw.githubusercontent.com/kmicinski/squirreldata/master/squirrels.json");
        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new ClickListener());
        fetchButton = (Button) findViewById(R.id.fetch_button);
        fetchButton.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            switch(view.getId()){

                case R.id.clear_button:
                    mAdapter.clear();
                    break;

                case R.id.fetch_button:
                    GetNewSquirrelsTask task = new GetNewSquirrelsTask(mAdapter);
                    task.execute("https://raw.githubusercontent.com/kmicinski/squirreldata/master/squirrels.json");
                    break;
            }
        }
    }

}
