/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: OPT_SingletonIterator.java,v 1.7 2002/08/20 21:39:23 sjfink-oss Exp $
package com.ibm.JikesRVM.opt;

/**
 * @author Mauricio J. Serrano
 * @author John Whaley
 */
class OPT_SingletonIterator
    implements java.util.Iterator {

  OPT_SingletonIterator (Object o) {
    item = o;
    not_done = true;
  }
  boolean not_done;
  Object item;

  public boolean hasNext () {
    return  not_done;
  }

  public Object next () {
    if (not_done) {
      not_done = false;
      return  item;
    }
    throw  new java.util.NoSuchElementException();
  }

  public void remove () {
    throw  new java.lang.UnsupportedOperationException();
  }
}
