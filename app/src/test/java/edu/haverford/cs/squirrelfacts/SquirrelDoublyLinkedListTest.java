package edu.haverford.cs.squirrelfacts;

import org.junit.Test;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

import edu.haverford.cs.squirrelfacts.BackIterator;

public class SquirrelDoublyLinkedListTest extends SquirrelListTest {
    public SquirrelDoublyLinkedListTest() {
        super();
    }

    /**
     * This test should test a basic property: that you can go forward and back in the list and
     * get to the same element.
     */
    @Test
    public void list_prevRemove() {
        SquirrelDoublyLinkedList sl = new SquirrelDoublyLinkedList();
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();
        Squirrel a1 = i.next();
        Squirrel a2 = i.next();
        Squirrel a3 = i.next();
        Squirrel a4 = i.prev();
        i.remove();
        Squirrel a5 = i.prev();
        i.remove();
        Squirrel a6 = i.prev();
        i.remove();
        assertEquals(true, sl.isEmpty());
        assertEquals(0, sl.size());
    }

    @Test
    public void list_forwardBack() {
        // ...
        SquirrelDoublyLinkedList sl = new SquirrelDoublyLinkedList();
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();
        Squirrel a1 = i.next();
        Squirrel a2 = i.next();
        Squirrel a3 = i.next();
        Squirrel a4 = i.prev();
        Squirrel a5 = i.prev();
        Squirrel a6 = i.prev();
        assertEquals(a1,a6);
        assertEquals(a2,a5);
        assertEquals(a3,a4);

    }

    @Test (expected = IllegalStateException.class)
    public void testDoubleRemoveAfterPrev(){
        SquirrelDoublyLinkedList sl = new SquirrelDoublyLinkedList();
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();
        i.next();
        i.next();
        i.prev();
        i.prev();
        i.remove();
        i.remove();
    }
}
