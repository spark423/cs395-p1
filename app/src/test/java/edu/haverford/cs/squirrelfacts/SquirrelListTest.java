package edu.haverford.cs.squirrelfacts;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

import edu.haverford.cs.squirrelfacts.Squirrel;
import edu.haverford.cs.squirrelfacts.SquirrelList;

public class SquirrelListTest {
    protected Squirrel mTestSquirrel;
    protected Squirrel mTestSquirrel2;
    protected Squirrel mTestSquirrel3;
    protected SquirrelList mEmptySquirrelList;

    public SquirrelListTest() {
        mTestSquirrel = new Squirrel("a","b","c");
        mTestSquirrel2 = new Squirrel ("e","f","g");
        mTestSquirrel3 = new Squirrel ("i","j","k");

        mEmptySquirrelList = new SquirrelList();
    }

    @Test
    public void list_startsLengthZero() {
        assertEquals(0, mEmptySquirrelList.size());
    }

    @Test
    public void list_addOneLengthOne() {
        assertEquals(1, mEmptySquirrelList.addToFront(mTestSquirrel).size());
    }

    @Test
    public void list_getBackFirst() {
        assertEquals(mTestSquirrel, mEmptySquirrelList.addToFront(mTestSquirrel).getFirst());
    }

    @Test
    public void list_notIsEmpty() {
        assertEquals(false, mEmptySquirrelList.addToFront(mTestSquirrel).isEmpty());
    }

    @Test
    public void list_getBackInsertedPosition() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel2);
        assertEquals(mTestSquirrel,sl.getItem(2));
    }

    @Test
    public void list_removeLink() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.remove();
        assertEquals(mTestSquirrel3, sl.getItem(1));
    }

    @Test
    public void list_removeLink2() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.next();
        i.remove();
        Squirrel b = i.next();
        assertEquals(2, sl.size());
    }

    @Test
    public void list_containsSquirrel() {
        mEmptySquirrelList.add(mTestSquirrel);
        assertEquals(true, mEmptySquirrelList.contains(mTestSquirrel));
    }

    @Test
    public void list_clearIsEmpty() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        sl.clear();
        assertEquals(true, sl.isEmpty());
        assertEquals(0, sl.size());
    }

    /**
     * Part 2: Failing test for iteration removal here..
     * TODO: change this from {testname} to your thoughtful name!!
     */
    @Test
    public void list_removefirstLink() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.remove();
        assertEquals(mTestSquirrel2, sl.getItem(0));
        assertEquals(mTestSquirrel3, sl.getItem(1));
    }

    @Test (expected = IllegalStateException.class)
    public void testDoubleRemoveAfterNext(){
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.next();
        i.remove();
        i.remove();
    }

    @Test(expected = ArrayStoreException.class)
    public void testIllegalGenericParameterTypeForToArray(){
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Integer[] integers = new Integer[sl.size()];
        sl.toArray(integers);
    }
}
