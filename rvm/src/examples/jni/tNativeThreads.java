/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: tNativeThreads.java,v 1.4 2003/12/05 23:45:04 augart-oss Exp $

import com.ibm.JikesRVM.*;

/**
 * Test native method with threads
 *
 * @author unascribed
 */

class tNativeThreads
{

  static final int NUMBER_OF_WORKERS = 5;

  public static native int nativeFoo(int count);

  public static        int javaFoo(int count) { 
    NativeThreadsWorker.say("tNativeThreads.javaFoo"," - entered and about to return");
    return count +1;
  }

  public static void main(String args[])
  {

    // VM_Scheduler.dumpVirtualMachine();

    System.out.println("Attempting to load dynamic library ...");
    System.out.println("(the LIBPATH env variable must be set for this directory)");

    System.loadLibrary("tNativeThreads");



      System.out.println("starting TestDispatch stuff");
      
      NativeThreadsWorker a[] = new NativeThreadsWorker[NUMBER_OF_WORKERS];
      for ( int wrk = 0; wrk < NUMBER_OF_WORKERS; wrk++ )
         {
           a[wrk] = new NativeThreadsWorker("ping"); 
           a[wrk].start();
         }

      NativeThreadsWorker b = new NativeThreadsWorker("pong");
      b.start();

      while ( ! b.isFinished )
          Thread.currentThread().yield();

      //count number of workers that completed
      //
      int cntr = 0;
      for ( int i = 0; i < NUMBER_OF_WORKERS; i ++ ) {
          if ( a[i].isFinished)
             cntr++;
      }
      if ( cntr < NUMBER_OF_WORKERS) {

        //     VM_Scheduler.dumpVirtualMachine();
      }

      
      for ( int wrk = 0; wrk < NUMBER_OF_WORKERS; wrk ++ )
        while ( ! a[wrk].isFinished ) {
          try {              
            //say(name, "sleeping");
            Thread.currentThread().sleep(300);
          } 
          catch (InterruptedException e) {}
          Thread.currentThread().yield();
        }

      //      VM_Scheduler.dumpVirtualMachine();    
  }
}






