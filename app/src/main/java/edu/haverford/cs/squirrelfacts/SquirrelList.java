package edu.haverford.cs.squirrelfacts;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A single link of a linked list
 */

class SquirrelIterator implements Iterator<Squirrel> {
    protected SquirrelList mList;
    protected SquirrelLink mCur;
    protected SquirrelLink mPrev = null;
    protected SquirrelLink mPrev2 = null;

    public SquirrelIterator(SquirrelLink firstLink, SquirrelList linkedList) {
        mCur = firstLink;
        mList = linkedList;
    }

    public boolean hasNext() {
        return mCur != null;
    }

    public Squirrel next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            Squirrel s = mCur.getSquirrel();
            mPrev2 = mPrev;
            mPrev = mCur;
            mCur = mCur.getNext();
            return s;
        }
    }

    /**
     * Wrong :-(
     */
    /**
     * This is method is used to remove the most recent Squirrel returned by the next method from the list
     * @return Nothing
     * @exception IllegalStateException when called more than once after each next call.
     */
    public void remove() {
//          mPrev.setNext(mCur.getNext());
        if (mPrev == mPrev2) {
            throw new IllegalStateException();
        } else if (mPrev2 == null) {
            mList.setHead(mCur);
            mPrev = mPrev2;
        } else {
            mPrev2.setNext(mCur);
            mPrev = mPrev2;
        }
    }
}

class SquirrelLink {
    protected Squirrel mSquirrel;
    public SquirrelLink mNext;

    public SquirrelLink(Squirrel squirrel, SquirrelLink next) {
        mSquirrel = squirrel;
        mNext = next;
    }

    public Squirrel getSquirrel() {
        return mSquirrel;
    }
    public SquirrelLink getNext() {
        return mNext;
    }

    public void setNext(SquirrelLink sl) {
        mNext = sl;
    }

}



public class SquirrelList implements Iterable<Squirrel>, Collection<Squirrel> {
    protected SquirrelLink mFirst;
    protected ArrayList<DataSetObserver> mObservers;


    public SquirrelList() {
        mFirst = null;
        mObservers = new ArrayList<>();
    }

    /**
     * TODO: Create a constructor that creates a list of squirrels from an array.
     * @param squirrels
     */
    SquirrelList(Squirrel[] squirrels) {
        mFirst = null;
        mObservers = new ArrayList<DataSetObserver>();
        for (int i = squirrels.length - 1; i>=0; i--) {
            addToFront(squirrels[i]);
        }
    }


    public void setHead(SquirrelLink sl) {
        mFirst = sl;
    }

    /**
     * Adds a squirrel to the front of the list.
     * @param squirrel The squirrel to add to the list
     * @return {this}, the updated object after adding the squirrel to the front of the list.
     */
    public SquirrelList addToFront(Squirrel squirrel) {
        mFirst = new SquirrelLink(squirrel, mFirst);
        for (DataSetObserver dataSetObserver : mObservers) {
            dataSetObserver.onChanged();
        }
        return this;
    }

    /**
     * Get the item at the `m`th position in the list.
     * @param m The index of the squirrel to fetch
     * @return The squirrel at that index
     * @throws IndexOutOfBoundsException if `m` > getLength()-1
     */
    public Squirrel getItem(int m) {
        Iterator<Squirrel> i = iterator();
        Squirrel s = null;
        while (m >= 0 && i.hasNext()) {
            s = i.next();
            m--;
        }
        if (m >= 0) {
            // Ran out of list
            throw new IndexOutOfBoundsException();
        } else {
            return s;
        }
    }

    /**
     * Get the first Squirrel in the list
     * @return first squirrel when list not empty
     * @throws NullPointerException when list is empty
     */
    public Squirrel getFirst() {
        if (mFirst != null) {
            return mFirst.getSquirrel();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Returns the size of the list.
     * @return
     */
    @Override
    public int size() {
        int i = 0;
        for (SquirrelLink c = mFirst; c != null; c = c.getNext()) {
            i++;
        }
        return i;
    }

    /**
     * TODO: Implement this
     * @return
     */
    @Override
    public boolean isEmpty() {
        return mFirst == null;
    }

    /**
     * TODO: Implement this
     * @param o
     * @return
     */
    @Override
    public boolean contains(Object o) {
        Iterator<Squirrel> i = iterator();
        boolean found = false;
        while (i.hasNext() && !found) {
            found = (i.next().equals(o));
        }
        return found;
    }

    @NonNull
    @Override
    public Iterator<Squirrel> iterator() {
        return new SquirrelIterator(mFirst, this);
    }

    /**
     * TODO: Implement this method
     * @param ts
     * @param <T>
     * @return T[] An array of items of type specified by generic type parameter T.
     * @exception ArrayStoreException On generic type parameter T that is not a subclass of Squirrel.
     */
    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        if (ts.length < size()) {
            T[] new_ts = (T[]) new Object[size()];
            int j = 0;
            for (Iterator<Squirrel> i = iterator(); i.hasNext(); j++) {
                try {
                    new_ts[j] = (T)i.next();
                } catch (ClassCastException e) {
                    throw new ArrayStoreException();
                }
            }
            return new_ts;
        } else {
            Iterator<Squirrel> i = iterator();
            int j = 0;
            while (j<ts.length) {

                if (i.hasNext()) {
                    try {
                        ts[j] = (T)i.next();
                    } catch (ClassCastException e) {
                        throw new ArrayStoreException();
                    }
                } else {
                    ts[j] = null;
                }
                j++;
            }
            return ts;
        }
    }

    @NonNull
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int j = 0;
        for (Iterator<Squirrel> i = iterator(); i.hasNext(); j++) {
            arr[j] = i.next();
        }
        return arr;
    }

    @Override
    public boolean add(Squirrel squirrel) {
        addToFront(squirrel);
        return true;
    }

    /**
     * TODO: Implement this. Be sure to make the comment more thoughtful.
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        Iterator<Squirrel> i = iterator();
        while (i.hasNext()) {
            if (i.next().equals(o)) {
                i.remove();
                for (DataSetObserver dataSetObserver : mObservers) {
                    dataSetObserver.onChanged();
                }
                return true;
            }
        }
        for (DataSetObserver dataSetObserver : mObservers) {
            dataSetObserver.onChanged();
        }
        return false;
    }

    /**
     * TODO: implement `addAll`.
     * @param collection
     * @return
     */
    @Override
    public boolean addAll(@NonNull Collection<? extends Squirrel> collection) {
        for (Squirrel squirrel : collection) {
            add(squirrel);
        }
        return true;
    }

    /**
     * TODO: Implement the `clear` method, which removes all of the elements from the array
     */
    @Override
    public void clear() {
        while (mFirst != null) {
            mFirst = mFirst.getNext();
        }
        for (DataSetObserver dataSetObserver : mObservers) {
            dataSetObserver.onChanged();
        }
    }

    public ArrayList<Squirrel> toArrayList() {
        ArrayList<Squirrel> l = new ArrayList<Squirrel>();
        l.addAll(this);
        return l;
    }


    public void addObserver(DataSetObserver dataSetObserver) {
        mObservers.add(dataSetObserver);
    }

    public void removeObserver(DataSetObserver dataSetObserver) {
        mObservers.remove(dataSetObserver);
    }

    // No need to implement the following three methods

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    // No need to implement the above three methods
}