/*
 * (C) Copyright IBM Corp. 2001
 */
//BootImageWriterConstants.java
//$Id: BootImageWriterConstants.java,v 1.8 2005/02/25 18:45:18 dgrove-oss Exp $

import com.ibm.JikesRVM.*;
import org.vmmagic.unboxed.Offset;

/**
 * Manifest constants for bootimage writer.
 *
 * @author Derek Lieber
 * @version 03 Jan 2000
 */
public interface BootImageWriterConstants extends VM_Constants {

  /**
   * Offset to associate with objects that haven't yet been placed into image.
   * Any offset that's outside image will do.
   */
  public static final Offset OBJECT_NOT_ALLOCATED = Offset.fromIntSignExtend(0xeeeeeee1);

  /**
   * Offset to associate with objects that are not to be placed into image.
   * Any offset that's outside image will do.
   */
  public static final Offset OBJECT_NOT_PRESENT = Offset.fromIntSignExtend(0xeeeeeee2);

  /**
   * Starting index for objects in VM_TypeDictionary.
   * = 1, since slot 0 is reserved for null
   */
  public static final int FIRST_TYPE_DICTIONARY_INDEX = 1;
}

