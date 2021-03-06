/*
 * (C) Copyright IBM Corp. 2001
 */
// $Id: OPT_RegSpillListEnumerator.java,v 1.4 2002/08/23 11:28:09 dgrove-oss Exp $
package com.ibm.JikesRVM.opt.ir;

import com.ibm.JikesRVM.opt.OPT_LinkedList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * This class provides an enumerator for a list of OPT_RegSpillElements
 * @author Michael Hind
 */
public class OPT_RegSpillListEnumerator implements Enumeration {

  /**
   *  The next element to return when called
   */
  private OPT_RegSpillListElement nextElementToReturn;

  /**
   * constructor
   * @param list the list to enumerate over
   */
  OPT_RegSpillListEnumerator(OPT_LinkedList list) {
    nextElementToReturn = (OPT_RegSpillListElement)list.first();
  }

  /**
   * Any elements left?
   * @return if any elements left
   */
  public final boolean hasMoreElements() {
    return nextElementToReturn != null;
  }

  /**
   * Returns the next element or throws an exception if none exist
   * @return the next element
   */
  public final Object nextElement() {
    if (nextElementToReturn != null) {
      return next();
    } 
    else {
      throw new NoSuchElementException("OPT_RegSpillListElementEnumerator");
    }
  }

  /**
   * Returns the next element or throws an exception if none exist
   * @return the next element
   */
  public final OPT_RegSpillListElement next() {
    OPT_RegSpillListElement ret = nextElementToReturn;
    nextElementToReturn = (OPT_RegSpillListElement)ret.getNext();
    return  ret;
  }
}



