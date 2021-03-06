/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: Worker.java,v 1.5 2004/09/10 19:34:19 augart-oss Exp $
/**
 * @author unascribed
 */
class Worker extends Thread  {
  private String name;
  volatile boolean readyFlag = false;
  volatile boolean doneFlag = false;
  Object theLock;
  int rc;

  /**
   * Constructor
   */
  Worker(String name, Object lockObject) {
    this.name = name;
    theLock = lockObject;
    readyFlag = false;
    doneFlag = false;
    
  }



  // overrides Thread
  public void start()  {
    super.start();
  }    

  // overrides Thread
  public void run() {

    // signal ready and wait for the main thread to tell to start
    readyFlag = true;
    MonitorTest.printVerbose(".... " + name + " ready to start");
    while (!MonitorTest.startCounting) {
    }

    // call the native code to contend for the lock from native
    MonitorTest.printVerbose(".... " + name + " calling native monitor");
    rc = MonitorTest.accessMonitorFromNative(theLock);

    if (rc!=0)
      MonitorTest.setFailFlag();

    MonitorTest.printVerbose(".... " + name + " done.");
    doneFlag = true;

    

  }


}
