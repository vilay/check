/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: OPT_RegisterRestrictions.java,v 1.6 2002/08/23 11:27:02 dgrove-oss Exp $
package com.ibm.JikesRVM.opt;

import com.ibm.JikesRVM.opt.ir.*;

/**
 * An instance of this class encapsulates restrictions on register
 * allocation.
 * 
 * @author Stephen Fink
 */
public final class OPT_RegisterRestrictions extends OPT_GenericRegisterRestrictions {
  /**
   * Default Constructor
   */
  public OPT_RegisterRestrictions(OPT_PhysicalRegisterSet phys) {
    super(phys);
  }
  /**
   * Is it forbidden to assign symbolic register symb to physical register r
   * in instruction s?
   */
  public boolean isForbidden(OPT_Register symb, OPT_Register r,
                             OPT_Instruction s) {
    return false;
  }

}
