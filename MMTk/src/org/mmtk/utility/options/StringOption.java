/*
 * (C) Copyright Department of Computer Science,
 * Australian National University. 2004
 */
package org.mmtk.utility.options;

import org.vmmagic.pragma.UninterruptiblePragma;

/**
 * An option that has a simple string value.
 *
 * @author Daniel Frampton
 * @version $Revision: 1.1 $
 * @date $Date: 2004/12/16 06:13:01 $
 */
public class StringOption extends Option {
  // values
  private String defaultValue;
  private String value;

  /**
   * Create a new string option.
   *
   * @param name The space separated name for the option.
   * @param desc The purpose of the option
   * @param defaultValue The default value of the option.
   */ 
  protected StringOption(String name, String desc, String defaultValue) { 
    super(STRING_OPTION, name, desc);
    this.value = this.defaultValue = defaultValue;
  }

  /**
   * Read the current value of the option.
   *
   * @return The option value.
   */
  public String getValue() throws UninterruptiblePragma {
    return this.value;
  }

  /**
   * Read the default value of the option
   *
   * @return The default value.
   */
  public String getDefaultValue() throws UninterruptiblePragma {
    return this.defaultValue;
  }

  /**
   * Update the value of the option, echoing the change if the echoOptions 
   * option is set. This method also calls the validate method to allow 
   * subclasses to perform any required validation.
   *
   * @param value The new value for the option.
   */
  public void setValue(String value) {
    this.value = value;
    validate();
  }
}
