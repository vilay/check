/*
 * (C) Copyright IBM Corp. 2001, 2004
 */
//$Id: OPT_LiveSet.java,v 1.7 2004/12/19 04:42:11 augart-oss Exp $
package com.ibm.JikesRVM.opt;

import com.ibm.JikesRVM.opt.ir.*;

/**
 * This file provides a sorted set (of registers) ADT with the 
 *  following public operations:
 * 
 *    clear()  - empties the set
 *    contains(reg) - checks if reg is in the set
 *    add(reg)  - adds reg to the set
 *    add(set2)  - adds the contents of set2 to the set
 *    remove(reg)  - removes reg from the set
 *    remove(set2) - removes the contents of set2 from the set
 *    enumerator() - returns an enumeration of the set
 *    toString() - returns a string version of the set
 *    isEmpty() - returns true, iff the set is empty
 *
 * @author Michael Hind 
 */
public class OPT_LiveSet {

  /**
   *  The beginning of the list
   */
  private OPT_LiveSetElement first;

  /**
   * just used for debugging
   */
  private static final boolean debug = false;

  /**
   * Empties the set
   */
  public final void clear() {
    first = null;
  }

  /**
   * Determines if the item passed is in the current set
   * @param item the register to search for
   * @return whether the item was found
   */
  public boolean contains(OPT_Register item) {
    if (debug) {
      System.out.println("looking for " + item + " in " + this);
    }
    // simply linear search
    OPT_LiveSetEnumerator enum = enumerator();
    while (enum.hasMoreElements()) {
      OPT_Register elem = ((OPT_RegisterOperand)enum.nextElement()).register;
      if (item == elem) {
        if (debug) {
          System.out.println("found it, returning true");
        }
        return  true;
      }
    }
    if (debug) {
      System.out.println("didn't find it, returning false");
    }
    return  false;
  }

  /**
   * create a new object from the passed parameter and add it to the list
   * @param item an object that contains the register to used in the newly
   *             created object
   */
  public void add(OPT_RegisterOperand item) {
    if (debug) {
      System.out.println("\t OPT_LiveSet.add (item) called with reg " + 
          item);
      System.out.println("\t before add:" + this);
    }
    // for each item in OPT_LiveSet add it to this.set
    if (first == null) {
      // add at the front
      createAndAddToCurrentList(item, null);
    } 
    else {
      OPT_LiveSetElement current = first;
      OPT_LiveSetElement prev = null;
      // traverse the current list looking for the appropriate place
      int itemNumber = item.register.number;      // cache the item's number
      while (current != null && current.getRegister().number < itemNumber) {
        prev = current;
        current = current.getNext();
      }
      // check if there is a next item
      if (current != null) {
        if (current.getRegister().number == itemNumber) {
          // already in there.  Check to see if we have an Address/Reference confusion.
          // If we do, then prefer to have the Reference in the LiveSet as that will
          // include item in the GC maps from this program point "up"
          if (current.getRegisterType().isWordType() && item.type.isReferenceType()) {
            current.setRegisterOperand(item);
          }
        } else {
          createAndAddToCurrentList(item, prev);
        }
      } else {                    // current == null
        // we ran off the end of the list, but prev still has the last element
        createAndAddToCurrentList(item, prev);
      }
    }
    if (debug) {
      System.out.println("\tafter add:" + this);
    }
  }

  /**
   * adds the contents of set2 to the set
   * @param additionList 
   * @return whether any additions were made
   */
  public boolean add(OPT_LiveSet additionList) {
    // for each item in OPT_LiveSet add it to this.set
    // recording if it was an addition
    // first make sure there is something to add
    if (additionList == null) {
      return  false;
    }
    OPT_LiveSetEnumerator enum = additionList.enumerator();
    if (!enum.hasMoreElements()) {
      return  false;
    }
    if (debug) {
      System.out.println("\t OPT_LiveSet.add called");
      System.out.println("\t   currentList: " + this);
      System.out.println("\t   additionList: " + additionList);
    }

    boolean change = false;
    if (first == null) {
      // current list is empty, just deep copy the passed list
      // handle the 1st element outside the loop
      OPT_RegisterOperand newElem = (OPT_RegisterOperand)enum.nextElement();
      first = new OPT_LiveSetElement(newElem);
      OPT_LiveSetElement existingPtr = first;
      while (enum.hasMoreElements()) {
        newElem = (OPT_RegisterOperand)enum.nextElement();
        // copy additionList and add it to first list
        OPT_LiveSetElement elem = new OPT_LiveSetElement(newElem);
        existingPtr.setNext(elem);
        existingPtr = elem;
      }
      change = true;
    } 
    else {
      // both (sorted) lists have at least 1 element
      // walk down the lists in parallel looking for items
      // in the addition list that aren't in the current list
      // We don't use the enumeration here, because it is more
      // familiar not too.
      OPT_LiveSetElement newPtr = additionList.first;
      OPT_LiveSetElement curPtr = first;
      // this is always one node before "curPtr". It is used for inserts
      OPT_LiveSetElement curPrevPtr = null;
      while (newPtr != null && curPtr != null) {
        if (newPtr.getRegister().number < curPtr.getRegister().number) {
          // found one in new list that is not in current list
          // When we add, the "prev" ptr will be updated
          curPrevPtr = createAndAddToCurrentList(newPtr.getRegisterOperand(), 
              curPrevPtr);
          // don't forget to update curPtr
          curPtr = getNextPtr(curPrevPtr);
          newPtr = newPtr.getNext();
          change = true;
        } 
        else if (newPtr.getRegister().number > curPtr.getRegister().number) {
          // need to move up current list
          curPrevPtr = curPtr;
          curPtr = curPtr.getNext();
        } 
        else {
          // item is already in current list, update both list ptrs
          curPrevPtr = curPtr;
          curPtr = curPtr.getNext();
          newPtr = newPtr.getNext();
        }
      }
      // while there is still more on the new list, add them 
      while (newPtr != null) {
        // When we add, the "prev" ptr will be updated
        curPrevPtr = createAndAddToCurrentList(newPtr.getRegisterOperand(), 
            curPrevPtr);
        // don't forget to update curPtr
        curPtr = getNextPtr(curPrevPtr);
        newPtr = newPtr.getNext();
        change = true;
      }
    }
    if (debug) {
      System.out.println("\tafter add:" + this + "\n Change:" + change);
    }
    return  change;
  }

  /**
   * removes the contents of the passed set from the currrent set, i.e.,
   *    this = this - removeList
   * @param removalList the list to remove from
   */
  public void remove(OPT_LiveSet removalList) {
    // for each item in the OPT_LiveSet
    // remove it from this.set
    // Since the "removalList" set is sorted we can perform the
    // remove in 1 pass over the "this" set.
    // first make sure there is something to remove
    if (removalList == null)
      return;
    OPT_LiveSetEnumerator enum = removalList.enumerator();
    if (!enum.hasMoreElements())
      return;
    // if current list is empty, there is nothing to remove
    if (first == null)
      return;
    if (debug) {
      System.out.println("\t OPT_LiveSet.remove called");
      System.out.println("\t   currentList: " + this);
      System.out.println("\t   removalList: " + removalList);
    }
    // both (sorted) lists have at least 1 element
    // walk down the lists in parallel looking for items
    // in the removal list that are in the current list
    // We don't use the enumeration here, because it is more
    // familiar not too.
    OPT_LiveSetElement newPtr = removalList.first;
    OPT_LiveSetElement curPtr = first;
    // this is always one node before "curPtr". It is used for removal
    OPT_LiveSetElement curPrevPtr = null;
    while (newPtr != null && curPtr != null) {
      if (newPtr.getRegister().number < curPtr.getRegister().number) {
        // found one in removal list that is not in current list
        // move to next on removal list
        newPtr = newPtr.getNext();
      } 
      else if (newPtr.getRegister().number > curPtr.getRegister().number) {
        // need to move up current list, found 1 on current list not on
        // removal list
        curPrevPtr = curPtr;
        curPtr = curPtr.getNext();
      } 
      else {
        // found one on both lists, remove it!
        if (curPrevPtr != null) {
          curPrevPtr.setNext(curPtr.getNext());
        } 
        else {
          // removing first item on list
          first = curPtr.getNext();
        }
        // move up both lists, curPrevPtr is already correct
        curPtr = curPtr.getNext();
        newPtr = newPtr.getNext();
      }
    }
    // once we leave the loop, we may have items on 1 list, but not
    // on the other.  these can't be removed so there is nothing to
    // be done with them
    if (debug) {
      System.out.println("\tafter remove:" + this);
    }
  }

  /**
   * removes the passed reg from the set
   * @param item the registerOperand holding the register of interest
   */
  public void remove(OPT_RegisterOperand item) {
    if (debug) {
      System.out.println("\tOPT_LiveSet.remove (item) called with reg "
          + item);
    }
    // only something to do if the set is non-empty
    if (first != null) {
      int itemNumber = item.register.number;    // cache the item's number
      // special case the first element
      if (first.getRegister().number == itemNumber) {
        first = first.getNext();
      } 
      else {
        OPT_LiveSetElement current = first.getNext();
        OPT_LiveSetElement prev = first;
        // run down the current list looking for appropriate place
        while (current != null && current.getRegister().number < itemNumber) {
          prev = current;
          current = current.getNext();
        }
        // did we find it?
        if (current != null && current.getRegister().number == itemNumber) {
          prev.setNext(current.getNext());
        }
      }
    }
  }

  /**
   * Is the current set empty?
   * @return true iff the set is empty
   */
  public boolean isEmpty() {
    return first == null;
  }
  
  /**
   * String-i-fy the current list
   * @return the string-i-fied version
   */
  public String toString() {
    StringBuffer buf = new StringBuffer("");
    ;
    if (first == null)
      buf.append("empty"); 
    else {
      OPT_LiveSetElement ptr = first;
      while (ptr != null) {
        buf.append(ptr.getRegisterOperand() + "  ");
        ptr = ptr.getNext();
      }
    }
    return  buf.toString();
  }

  /**
   * Returns an enumerator of the list
   * @return an enumerator of the list
   */
  public final OPT_LiveSetEnumerator enumerator() {
    return  new OPT_LiveSetEnumerator(first);
  }

  /**
   * Copy the newElement into a new object and add it to the list 
   * after prevElement.  If prevElement is null, update the "start"
   * data member by inserting at the begining.
   * @param  register the element to copy and insert
   * @param  prevElement the element on the current list to insert after
   *                     or null, indicating insert at the front
   * @return the element that is prior to the newly inserted element
   */
  private OPT_LiveSetElement createAndAddToCurrentList(
      OPT_RegisterOperand register, 
      OPT_LiveSetElement prevElement) {
    OPT_LiveSetElement newElement = new OPT_LiveSetElement(register);
    if (prevElement == null) {
      // insert at front of list
      newElement.setNext(first);
      first = newElement;
    } 
    else {
      // insert at non-front of list
      newElement.setNext(prevElement.getNext());
      prevElement.setNext(newElement);
    }
    // new Element is now the previous element to the "curent" one
    // which was the node that followed prevElement on entry to this method

    return  newElement;
  }

  /**
   *  Inspects the passed ptr, if it is nonnull it returns its next field
   *  otherwise, it returns "first"
   *  @param ptr  the ptr to look at it
   *  @return     the next field (if ptr is nonnull) or first (if ptr is null)
   */
  private OPT_LiveSetElement getNextPtr(OPT_LiveSetElement ptr) {
    if (ptr != null)
      return  ptr.getNext(); 
    else 
      return  first;
  }

}



