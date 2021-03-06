/*
 * (C) Copyright IBM Corp 2003, 2004
 */
//$Id: VMRuntime.java,v 1.14 2005/01/14 05:44:38 augart-oss Exp $
package java.lang;

import java.io.File;
import java.util.Properties;

import com.ibm.JikesRVM.*;
import com.ibm.JikesRVM.classloader.VM_ClassLoader;
import com.ibm.JikesRVM.memoryManagers.mmInterface.*;
import com.ibm.JikesRVM.VM_Configuration;


import gnu.classpath.VMSystemProperties;

/**
 * Jikes RVM implementation of GNU Classpath's java.lang.VMRuntime.
 * See reference implementation for javadoc.
 *
 * @author Julian Dolby
 * @author Dave Grove
 */
final class VMRuntime {

  private static boolean runFinalizersOnExit = false;
  
  private VMRuntime() { }

  static int availableProcessors() {
    return VM_Scheduler.numProcessors;
  }
    
  static long freeMemory() {
    return MM_Interface.freeMemory().toLong();
  }
    
  static long totalMemory() {
    return MM_Interface.totalMemory().toLong();
  }
    
  static long maxMemory() {
    return MM_Interface.maxMemory().toLong();
  }
    
  static void gc() {
    MM_Interface.gc();
  }
    
  static void runFinalization() {
    // TODO: talk to Steve B & Perry and figure out what to do.
    // as this is a hint, we can correctly ignore it.
    // However, there might be something else we should do.
  }
    
  static void runFinalizationForExit() {
    if (runFinalizersOnExit) {
      // TODO: talk to Steve B & Perry and figure out what to do.
      throw new VM_UnimplementedError();
    }
  }
    
  static void traceInstructions(boolean on) {
    // VMs are free to ignore this...
  }
    
  static void traceMethodCalls(boolean on) {
    // VMs are free to ignore this...
  }

  static void runFinalizersOnExit(boolean value) {
    runFinalizersOnExit = value;
  }

  static void exit(int status) {
    VM.sysExit(status);
  }    

  //-#if RVM_WITH_CLASSPATH_0_10 || RVM_WITH_CLASSPATH_0_11 || RVM_WITH_CLASSPATH_0_12 || RVM_WITH_CLASSPATH_0_13
  static int nativeLoad(String libName) {
    return VM_DynamicLibrary.load(libName);
  }
  //-#else
  /** As of January 7, 2005, 0.13+ CVS Head.  <b>XXX TODO</b> We currently ignore the
   * <code>loader</code> parameter.
   * @param loader Ignored.  null means the bootstrap class loader.
   * @return nonzero on success, zero on failure. */
  static int nativeLoad(String libName, ClassLoader loader) {
    return VM_DynamicLibrary.load(libName);
  }
  //-#endif


  //-#if RVM_WITH_CLASSPATH_0_10 || RVM_WITH_CLASSPATH_0_11 || RVM_WITH_CLASSPATH_0_12
  //-#else
  /** Mangle a short-name to the file name (not the full pathname) for a
   *  dynamically loadable library.  (Used only in Classpath 0.13 and later)
   */
  static String mapLibraryName(String libname) {
    return nativeGetLibname(null, libname);
  }
  //-#endif

  /** This method is used in Classpath 0.10, 0.11, and 0.12.
   *  Classpath 0.13 and later use {@link #mapLibraryName} instead, which
   *  calls this function for help. */
  //-#if RVM_WITH_CLASSPATH_0_10 || RVM_WITH_CLASSPATH_0_11 || RVM_WITH_CLASSPATH_0_12
  //-#else
  private
    //-#endif
    static String nativeGetLibname(String pathname, String libname) {
    String libSuffix;
    if (VM.BuildForLinux) {
      libSuffix = ".so";
    } else if (VM.BuildForOsx) {
      libSuffix = ".jnilib";
    } else {
      libSuffix = ".a";
    }
    if (pathname != null && !("".equals(pathname)))
      return pathname + File.separator + "lib" + libname + libSuffix;
    else
      return "lib" + libname + libSuffix;
  }

  static Process exec(String[] cmd, String[] env, File dir) {
    String dirPath = (dir != null) ? dir.getPath() : null;
    return new VM_Process(cmd[0], cmd, env, dirPath);
  }

  /**
   * This is used by Runtime.addshutdownHook().
   *
   * TODO: I don't THINK there's anything we need to do for this, but we should
   * look it over more carefully.  Perhaps we want to add something so that we
   * will try to run the hooks in case of an abnormal  exit (such a
   * control-C)?  */ 
  static void enableShutdownHooks() {
  }


  //-#if RVM_WITH_CLASSPATH_0_10 || RVM_WITH_CLASSPATH_0_11 || RVM_WITH_CLASSPATH_0_12
  /** VMRuntime.insertSystemProperties is used by Classpath versions through
   *  Classpath  0.12.   Starting with Classpath 0.13, we use
   *  gnu.classpath.VMSystemProperties.preInit and
   *  gnu.classpath.VMSystemProperties.postInit.
   *
   */
  static void insertSystemProperties(Properties p) {
    VMSystemProperties.preInit(p);
    VMSystemProperties.postInit(p);
  }
    
  //-#endif
  
}
