/*
 * (C) Copyright Department of Computer Science,
 * Australian National University. 2004
 */
package org.mmtk.utility.options;

/**
 * Port number for GCSpy server to connect with visualiser.
 *
 * @author Daniel Frampton
 * @version $Revision: 1.1 $
 * @date $Date: 2004/12/16 06:13:00 $
 */
public class GCspyPort extends IntOption {
  /**
   * Create the option.
   */
  public GCspyPort() {
    super("GCSpy Port",
          "Port number for GCSpy server to connect with visualiser",
          0);
  }

  /** 
   * Ensure the port is valid.
   */
  protected void validate() {
    failIf(this.value <= 0, "Unreasonable GCSpy port value");
  }
}
