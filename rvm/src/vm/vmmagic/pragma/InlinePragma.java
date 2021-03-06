/*
 * (C) Copyright IBM Corp. 2002
 */
//$Id: InlinePragma.java,v 1.1 2004/09/09 06:40:50 steveb-oss Exp $
package org.vmmagic.pragma;

import com.ibm.JikesRVM.classloader.*;
/**
 * This pragma indicates that a particular method should always be inlined
 * by the optimizing compiler.
 * 
 * @author Stephen Fink
 */
public class InlinePragma extends PragmaException {
  private static final VM_TypeReference me = getTypeRef("Lorg/vmmagic/pragma/InlinePragma;");
  public static boolean declaredBy(VM_Method method) {
    return declaredBy(me, method);
  }
}
