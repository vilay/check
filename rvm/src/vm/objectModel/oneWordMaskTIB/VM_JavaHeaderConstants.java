/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: VM_JavaHeaderConstants.java,v 1.3 2002/08/15 14:05:05 sjfink-oss Exp $ 
package com.ibm.JikesRVM;

/**
 * Constants for the JavaHeader. 
 *
 * @see VM_ObjectModel
 * 
 * @author David Bacon
 * @author Steve Fink
 * @author Dave Grove
 */
public interface VM_JavaHeaderConstants {

  static final int JAVA_HEADER_END = -8;

  static final int ARRAY_LENGTH_OFFSET = -4;

  /** How many bits in the header are available for the GC and MISC headers? */
  static final int NUM_AVAILABLE_BITS = 2;

  /**
   * Does this object model use the same header word to contain
   * the TIB and a forwarding pointer?
   */
  static final boolean FORWARDING_PTR_OVERLAYS_TIB = true;

  static final int HASH_STATE_UNHASHED         = 0x0; // 00xx
  static final int HASH_STATE_HASHED           = 0x4; // 01xx
  static final int HASH_STATE_HASHED_AND_MOVED = 0xc; // 11xx
}
