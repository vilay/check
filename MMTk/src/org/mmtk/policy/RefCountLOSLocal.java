/*
 * (C) Copyright Department of Computer Science,
 * Australian National University. 2002
 */
package org.mmtk.policy;

import org.mmtk.utility.alloc.LargeObjectAllocator;
import org.mmtk.vm.Assert;
import org.mmtk.utility.Constants;

import org.vmmagic.unboxed.*;
import org.vmmagic.pragma.*;

/**
 *
 * @author <a href="http://cs.anu.edu.au/~Steve.Blackburn">Steve Blackburn</a>
 * @version $Revision: 1.11 $
 * @date $Date: 2005/01/19 02:49:02 $
 */
public final class RefCountLOSLocal extends LargeObjectAllocator
  implements Constants, Uninterruptible {
  public final static String Id = "$Id: RefCountLOSLocal.java,v 1.11 2005/01/19 02:49:02 steveb-oss Exp $"; 

   public RefCountLOSLocal(LargeObjectSpace space) {
     super(space);
     Assert._assert(false);
  }

  /****************************************************************************
   *
   * Allocation
   */

  /**
   *  This is called each time a cell is alloced (i.e. if a cell is
   *  reused, this will be called each time it is reused in the
   *  lifetime of the cell, by contrast to initializeCell, which is
   *  called exactly once.).
   *
   * @param cell The newly allocated cell
   */
  protected final void postAlloc(Address cell) throws InlinePragma {};

  /****************************************************************************
   *
   * Miscellaneous size-related methods
   */
  /**
   * Return the size of the per-superpage header required by this
   * system.  In this case it is just the underlying superpage header
   * size.
   *
   * @param sizeClass The size class of the cells contained by this
   * superpage.
   * @return The size of the per-superpage header required by this
   * system.
   */
  protected final int superPageHeaderSize()
    throws InlinePragma {
    return 0;
  }

  /**
   * Return the size of the per-cell header for cells of a given class
   * size.
   *
   * @return The size of the per-cell header for cells of a given class
   * size.
   */
  protected final int cellHeaderSize()
    throws InlinePragma {
    return 0;
  }
}
