/*
 * (C) Copyright IBM Corp. 2002
 */
//$Id: NoOptCompilePragma.java,v 1.1 2004/09/09 06:40:51 steveb-oss Exp $
package org.vmmagic.pragma; 

import com.ibm.JikesRVM.classloader.*;
/**
 * This pragma indicates that a particular method should never be 
 * compiled by the optimizing compiler. It also implies that the
 * method will never be inlined by the optimizing compiler.
 * 
 * @author Dave Grove
 */
public class NoOptCompilePragma extends PragmaException {
  private static final VM_TypeReference me = getTypeRef("Lorg/vmmagic/pragma/NoOptCompilePragma;");
  public static boolean declaredBy(VM_Method method) {
    return declaredBy(me, method);
  }
}
