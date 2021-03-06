/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: VM_TrapConstants.java,v 1.5 2003/09/19 18:43:13 dgrove-oss Exp $
package com.ibm.JikesRVM;

/**
 * Trap constants for IA32 platform.
 * 
 * @author Bowen Alpern
 * @author David Grove
 */
public interface VM_TrapConstants {

  /** 
   * This base is added to the numeric trap codes in VM_Runtime.java
   * to yield the intel trap number that is given to INT instructions
   */
  public final static byte RVM_TRAP_BASE = (byte) 0x40;
  
}
