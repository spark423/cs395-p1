package edu.haverford.cs.squirrelfacts;

import android.widget.ListAdapter;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

import edu.haverford.cs.squirrelfacts.Squirrel;
import edu.haverford.cs.squirrelfacts.SquirrelList;
import edu.haverford.cs.squirrelfacts.SquirrelListAdapter;
import edu.haverford.cs.squirrelfacts.GetNewSquirrelsTask;

public class AdapterTest extends SquirrelListTest {
    protected SquirrelListAdapter mAdapter;

    public AdapterTest() {
        super();
        mAdapter = new SquirrelListAdapter(null, mEmptySquirrelList);
    }

    @Test
    public void testAdapterClear(){
        SquirrelListAdapter ad = mAdapter;
        ad.add(mTestSquirrel);
        ad.add(mTestSquirrel3);
        ad.add(mTestSquirrel3);
        assertEquals(ad.getCount(), 3);
        ad.clear();
        assertEquals(mAdapter.getCount(), 0);
    }

}
