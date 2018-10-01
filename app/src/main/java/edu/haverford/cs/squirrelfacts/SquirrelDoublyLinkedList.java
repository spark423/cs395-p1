package edu.haverford.cs.squirrelfacts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO: Implement the rest of this iterator
 * Note that we extend from SquirrelIterator so that we do not duplicate the code in that class
 */
class SquirrelDoublyLinkedListIterator extends SquirrelIterator implements BackIterator<Squirrel> {
    protected boolean recentMoveWasPrev = false;

    public SquirrelDoublyLinkedListIterator(SquirrelLink firstLink, SquirrelDoublyLinkedList linkedList) {
        super(firstLink, linkedList);
    }
    @Override
    public Squirrel next() {
        recentMoveWasPrev = false;
        return super.next();
    }

    @Override
    public boolean hasPrev() {
        return mPrev != null;
    }

    @Override
    public Squirrel prev() {
        if (!hasPrev()) {
            throw new NoSuchElementException();
        } else {
            recentMoveWasPrev = true;
            mCur = mPrev;
            mPrev = mPrev2;
            if (mPrev2 != null) {
                mPrev2 = ((SquirrelDoubleLink) mPrev2).getPrev();
            }
            Squirrel s = mCur.getSquirrel();
            return s;
        }
    }

    /**
     * This is method is used to remove the most recent Squirrel returned by the next method
     * or the prev method from the list
     * @return Nothing
     * @exception IllegalStateException when called more than once after each next call or prev call.
     */
    @Override
    public void remove() {
        if (recentMoveWasPrev) {
            if (mPrev == mCur) {
                throw new IllegalStateException();
            } else if (mPrev == null) {
                mList.setHead(mCur.getNext());
                mCur = mPrev;
            } else {
                mPrev.setNext(mCur.getNext());
                mCur = mPrev;
            }
        } else {
            super.remove();
        }
    }
}

/**
 * TODO: Implement the rest of this double link structure.
 */
class SquirrelDoubleLink extends SquirrelLink {
    SquirrelDoubleLink mPrev = null;

    public SquirrelDoubleLink(Squirrel squirrel, SquirrelLink next) {
        super(squirrel,next);
        if (next != null) {
            ((SquirrelDoubleLink) mNext).setPrev(this);
        }
    }

    public SquirrelDoubleLink getPrev() {
        return mPrev;
    }


    public void setPrev(SquirrelLink sl) {
        mPrev = (SquirrelDoubleLink) sl;
    }

    public SquirrelDoubleLink getNext() {
        return (SquirrelDoubleLink) mNext;
    }

    public void setNext(SquirrelDoubleLink sl) {
        mNext = (SquirrelDoubleLink) sl;
    }

}

/**
 * TODO: Implement the rest of this class.
 */
public class SquirrelDoublyLinkedList extends SquirrelList {
    SquirrelDoublyLinkedList() {
        super();
    }

    @Override
    public SquirrelDoublyLinkedList addToFront(Squirrel squirrel) {
        mFirst = new SquirrelDoubleLink(squirrel, mFirst);
        return this;
    }

    @Override
    public BackIterator<Squirrel> iterator() {
        return new SquirrelDoublyLinkedListIterator(mFirst, this);
    }

}
