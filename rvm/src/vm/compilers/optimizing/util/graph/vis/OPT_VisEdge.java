/*
 * (C) Copyright IBM Corp. 2001
 */
//OPT_VisEdge.java
//$Id: OPT_VisEdge.java,v 1.3 2002/08/20 21:38:36 sjfink-oss Exp $
package com.ibm.JikesRVM.opt;

import java.util.Enumeration;

/**
 * OPT_VisEdge provides the minimum set of routines for graph edge
 * visualization.  The graph should implement OPT_VisGraph interface, and
 * its nodes - OPT_VisNode interface.
 *
 * @author Igor Pechtchanski
 * @see OPT_VisGraph
 * @see OPT_VisNode
 */

public interface OPT_VisEdge {
  /**
   * Returns the source node of the edge.
   * The node has to implement the OPT_VisNode interface
   * @return edge source node
   */
  public OPT_VisNode sourceNode();

  /**
   * Returns the target node of the edge.
   * The node has to implement the OPT_VisNode interface
   * @return edge target node
   */
  public OPT_VisNode targetNode();
}


