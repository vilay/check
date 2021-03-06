/*
 * (C) Copyright Richard Jones, 2002
 * Computing Laboratory, University of Kent at Canterbury
 * All rights reserved.
 */
package org.mmtk.vm.gcspy;

import org.vmmagic.unboxed.*;
import org.vmmagic.pragma.*;

/**
 * VM-neutral stub file for generic GCspy server interpreter
 *
 * This class implements the GCspy server. 
 * Mostly it forwards calls to the C gcspy library.
 *
 * $Id: ServerInterpreter.java,v 1.1 2004/12/06 05:25:22 steveb-oss Exp $
 *
 * @author <a href="http://www.ukc.ac.uk/people/staff/rej">Richard Jones</a>
 * @version $Revision: 1.1 $
 * @date $Date: 2004/12/06 05:25:22 $
 */
public class ServerInterpreter implements Uninterruptible {
  public static void init (String name,
                           int port,
                           String[] eventNames,
                           boolean verbose,
                           String generalInfo) {}
  public static boolean isConnected (int event) { return false; }
  public static void startServer(boolean wait) {}
  public static boolean shouldTransmit(int event) { return false; }
  public static void startCompensationTimer() {}
  public static void stopCompensationTimer() {}
  public static void serverSafepoint (int event) {}
  public static int computeHeaderSize() { return 0; }
}
