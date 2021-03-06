/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: OPT_RegisterPool.java,v 1.7 2003/02/20 18:36:02 dgrove-oss Exp $
package com.ibm.JikesRVM.opt.ir;

import com.ibm.JikesRVM.*;
import com.ibm.JikesRVM.classloader.*;

/**
 * Pool of symbolic registers.
 * powerPC specific implementation where JTOC is stored in a reserved register.
 * Each IR contains has exactly one register pool object associated with it.
 * 
 * @see OPT_Register
 * 
 * @author Dave Grove
 * @author Mauricio J. Serrano
 * @author John Whaley
 * @modified Vivek Sarkar
 * @author Peter Sweeney
 */
public class OPT_RegisterPool extends OPT_GenericRegisterPool {

  /**
   * Initializes a new register pool for the method meth.
   * 
   * @param meth the VM_Method of the outermost method
   */
  public OPT_RegisterPool(VM_Method meth) {
    super(meth);
  }

  /**
   * Get the JTOC register
   * 
   * @return the JTOC register
   */ 
  public OPT_Register getJTOC() {
    return physical.getJTOC();
  }

  /**
   * Get a temporary that represents the JTOC register (as an INT)
   * 
   * @param ir  
   * @param s  
   * @return the temp
   */ 
  public OPT_RegisterOperand makeJTOCOp(OPT_IR ir, OPT_Instruction s) {
    return new OPT_RegisterOperand(getJTOC(),VM_TypeReference.Address);
  }

  /**
   * Get a temporary that represents the JTOC register (as an Object)
   * 
   * @return the temp
   */ 
  public OPT_RegisterOperand makeTocOp() {
    return new OPT_RegisterOperand(getJTOC(),VM_TypeReference.JavaLangObject);
  }

}
