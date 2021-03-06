/*
 * (C) Copyright IBM Corp. 2001
 */
//$Id: OPT_TreeNode.java,v 1.4 2002/08/23 11:35:55 dgrove-oss Exp $
package com.ibm.JikesRVM.opt;

import  java.util.Enumeration;

/**
 *  This class is a node in a tree.  Both up and down pointers are used.
 *
 *  @author Michael Hind
 */
public class OPT_TreeNode {

  /**
   *  The parent of this node
   */
  private OPT_TreeNode parent;

  /**
   *  The first (leftmost) child
   */
  private OPT_TreeNode leftChild;

  /**
   *  The next node on the child list that I am on
   */
  private OPT_TreeNode rightSibling;

  /**
   * Constructor
   */
  public OPT_TreeNode() {
    parent = null;
    leftChild = null;
    rightSibling = null;
  }

  /**
   * return the parent of this node
   * @return my parent
   */
  public OPT_TreeNode getParent() {
    return  parent;
  }

  /**
   * returns the first child of this node
   * @return the first child of this node
   */
  public OPT_TreeNode getLeftChild() {
    return  leftChild;
  }

  /**
   * returns the next node with the same parent as me 
   * @return the next node with the same parent as me 
   */
  public OPT_TreeNode getRightSibling() {
    return  rightSibling;
  }

  /**
   *  adds a child to this node
   *  @param node the new child
   */
  public void addChild(OPT_TreeNode node) {
    if (leftChild == null) {
      leftChild = node;
    } 
    else {
      // get to the last sibling
      OPT_TreeNode siblingNode = leftChild;
      while (siblingNode.rightSibling != null) {
        siblingNode = siblingNode.rightSibling;
      }
      siblingNode.rightSibling = node;
    }
    node.parent = this;
  }

  /**
   *  Sets all tree pointers to null
   */
  public void clear() {
    leftChild = null;
    rightSibling = null;
    parent = null;
  }

  public Enumeration getChildren() {
    return  new OPT_TreeNodeChildrenEnumerator(this);
  }

}



