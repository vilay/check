/*
 * (C) Copyright Department of Computer Science,
 * Australian National University. 2002
 */
package com.ibm.JikesRVM.memoryManagers.mmInterface;

import org.mmtk.utility.Constants;
import org.mmtk.vm.Plan;

import com.ibm.JikesRVM.VM_Callbacks;

import org.vmmagic.pragma.*;

/**
 * This class allows JMTk to register call backs with VM_Callbacks.
 * 
 * @author <a href="http://cs.anu.edu.au/~Steve.Blackburn">Steve Blackburn</a>
 *
 * @version $Revision: 1.9 $
 * @date $Date: 2005/01/19 02:49:05 $
 */
public class Monitor 
  implements Constants, Uninterruptible, VM_Callbacks.ExitMonitor {
  public final static String Id = "$Id: Monitor.java,v 1.9 2005/01/19 02:49:05 steveb-oss Exp $"; 

  /**
   * Register the exit monitor at boot time.
   */
  public static void boot() throws InterruptiblePragma {
    VM_Callbacks.addExitMonitor(new Monitor());
  }

  /**
   * The VM is about to exit.  Notify the plan.
   *
   * @param value The exit value
   */
  public void notifyExit(int value) {
    Plan.getInstance().notifyExit(value);
  }
}
