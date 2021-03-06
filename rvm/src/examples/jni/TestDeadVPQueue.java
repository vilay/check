/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: TestDeadVPQueue.java,v 1.4 2003/12/05 23:45:04 augart-oss Exp $
import com.ibm.JikesRVM.*;
/**
 * @author unascribed
 */
class TestDeadVPQueue
   {
   public static void 
   main(String args[])
      throws Exception
      {

        TestDeadVPQueueWorker w = null;

      System.out.println("TestDeadVPQueue");

      // load the native code library
      System.out.println("Attempting to load dynamic library ...");
      System.out.println("(the LIBPATH env variable must be set for this directory)");

      System.loadLibrary("TestDeadVPQueueWorker");

     
      // create a thread and wait for it to terminate
      System.out.println(Thread.currentThread().getName() + ": creating worker 1");
      TestDeadVPQueueWorker w1 = new TestDeadVPQueueWorker("worker 1");
      
      System.out.println(Thread.currentThread().getName() + ": starting worker 1");
      w1.start();
      while (w1.state != TestDeadVPQueueWorker.ending) {
         try {Thread.currentThread().sleep(100); } catch (InterruptedException e) {}
      }
      System.out.println(Thread.currentThread().getName() + ": ending worker 1");
         try { Thread.currentThread().sleep(100); } catch (InterruptedException e) {}

      // create a second thread and wait for it to terminate
    System.out.println(Thread.currentThread().getName() + ": creating worker 2");
       TestDeadVPQueueWorker w2 = new TestDeadVPQueueWorker("worker 2");
      
      System.out.println(Thread.currentThread().getName() + ": starting worker 2");
      w2.start();
      while (w2.state != TestDeadVPQueueWorker.ending) {
         try {Thread.currentThread().sleep(100); } catch (InterruptedException e) {}
      }
      System.out.println(Thread.currentThread().getName() + ": ending worker 2");
         try { Thread.currentThread().sleep(100); } catch (InterruptedException e) {}

    System.out.println("end synchronous test- dump VM state");
     VM_Scheduler.dumpVirtualMachine();


    System.out.println("start semi asynchronous test- then dump VM state");

      // create start and run 9 threads
      for ( int i = 1; i < 10; i++){

       String s = " worker " + i;  
       // create a thread and start it
       System.out.println(Thread.currentThread().getName() + ":creating" +s);
       w = new TestDeadVPQueueWorker(s);
       System.out.println(Thread.currentThread().getName() + ":starting" + s);
       w.start();
       // wait a bit before we start the next
       try {Thread.currentThread().sleep(10); } catch (InterruptedException e) {}
      }
      while (w.state != TestDeadVPQueueWorker.ending) {
        try {Thread.currentThread().sleep(500); } catch (InterruptedException e) {}
      }

     
      System.out.println("main: bye");
      VM_Scheduler.dumpVirtualMachine();
      }
   }
