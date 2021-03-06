/*
 * (C) Copyright IBM Corp. 2004
 */
//$Id: VM_CallSite.java,v 1.2 2004/11/05 17:51:45 hoffmann-oss Exp $
package com.ibm.JikesRVM.adaptive;

import com.ibm.JikesRVM.classloader.VM_Method;

/**
 * A call site is a pair: <VM_Method, bcIndex>
 *
 * @author Dave Grove
 */
public final class VM_CallSite {

  /**
   * Caller method
   */
  private final VM_Method method;

  /**
   * bytecode index of callsite in caller method
   */
  private final int bcIndex;

  /**
   * @param m the VM_Method containing the callsite
   * @param bci the bytecode index of the callsite within m
   */
  public VM_CallSite(VM_Method m, int bci) {
    if (com.ibm.JikesRVM.VM.VerifyAssertions) com.ibm.JikesRVM.VM._assert(m != null);
    method = m;
    bcIndex = bci;
  }

  /**
   * @return method containing the callsite
   */
  public VM_Method getMethod() { return method; }

  /**
   * @return call site's bytecode index in its method
   */
  public int getBytecodeIndex() {return bcIndex;}
  
  /**
   * @return string representation of call site
   */
  public String toString() {
    return "<"+method+", "+bcIndex+">";
  }

  /**
   * Determine if two call sites are the same.  Exact match: no wild cards.
   *
   * @param obj call site to compare to
   * @return true if call sites are the same; otherwise, return false
   */
  public boolean equals(Object obj) {
    if (obj instanceof VM_CallSite) { 
      VM_CallSite cs = (VM_CallSite)obj;
      return method.equals(cs.method) && bcIndex == cs.bcIndex;
    } else {
      return false;
    }
  }

  /**
   * @return hash code
   */
  public int hashCode() {
    return bcIndex + method.hashCode();
  }
}
