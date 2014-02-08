/* EListNode.java */

package graph.list;

/**
 *  A EListNode is a mutable node in a EList (doubly-linked list).
 **/

public class EListNode {

  /**
   *  (inherited)  item references the item stored in the current node.
   *  (inherited)  myList references the List that contains this node.
   *  prev references the previous node in the EList.
   *  next references the next node in the EList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   **/

  protected Object item;
  protected int weight;
  protected EListNode partner;
  protected EListNode prev;
  protected EListNode next;
  protected EList myList; 

  /**
   *  EListNode() constructor.
   *  @param i the item to store in the node.
   *  @param l the list this node is in.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  EListNode(Object i, EList l, EListNode p, EListNode n) {
    item = i;
    myList = l;
    prev = p;
    next = n;
    weight = 0;
  }

  /**
   *  isValidNode returns true if this node is valid; false otherwise.
   *  An invalid node is represented by a `myList' field with the value null.
   *  Sentinel nodes are invalid, and nodes that don't belong to a list are
   *  also invalid.
   *
   *  @return true if this node is valid; false otherwise.
   *
   *  Performance:  runs in O(1) time.
   */
  public boolean isValidNode() {
    return myList != null;
  }
  
  /**
   *  item() returns this node's item.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the item stored in this node.
   *
   *  Performance:  runs in O(1) time.
   */
  public Object item() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    return item;
  }
  
  /**
   *  setItem() sets this node's item to "item".  If this node is invalid,
   *  throws an exception.
   *
   *  Performance:  runs in O(1) time.
   */
  public void setItem(Object item) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    this.item = item;
  }
  
  /**
   *  item() returns this node's item.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the item stored in this node.
   *
   *  Performance:  runs in O(1) time.
   */
  public int weight() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    return weight;
  }
  
  /**
   *  setWeight() sets this node's weight to "weight".  If this node is invalid,
   *  throws an exception.
   *
   *  Performance:  runs in O(1) time.
   */
  public void setWeight(int weight) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    this.weight = weight;
  }
  
  /**
   *  partner() returns this node's item.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the partner stored in this node.
   *
   *  Performance:  runs in O(1) time.
   */
  public EListNode partner() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    return partner;
  }
  
  /**
   *  setItem() sets this node's item to "item".  If this node is invalid,
   *  throws an exception.
   *
   *  Performance:  runs in O(1) time.
   */
  public void setPartner(EListNode partner) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException();
    }
    this.partner = partner;
  }

  /**
   *  next() returns the node following this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the node following this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public EListNode next() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("next() called on invalid node");
    }
    return next;
  }

  /**
   *  prev() returns the node preceding this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node preceding this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public EListNode prev() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("prev() called on invalid node");
    }
    return prev;
  }

  /**
   *  insertAfter() inserts an item immediately following this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("insertAfter() called on invalid node");
    }
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what EList it's in.  You should use myList.newNode() to create the
    //   new node.
    EListNode temp = myList.newNode(item, myList, this, next);
    next.prev = temp;
    next = temp;
    myList.size++;
  }

  /**
   *  insertBefore() inserts an item immediately preceding this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("insertBefore() called on invalid node");
    }
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what EList it's in.  You should use myList.newNode() to create the
    //   new node.
    EListNode temp = myList.newNode(item, myList, prev, this);
    prev.next = temp;
    prev = temp;
    myList.size++;
  }

  /**
   *  remove() removes this node from its EList.  If this node is invalid,
   *  throws an exception.
   *
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void remove() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("remove() called on invalid node");
    }
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what EList it's in.

    prev.next = next;
    next.prev = prev;
    myList.size--;

    // Make this node an invalid node, so it cannot be used to corrupt myList.
    myList = null;
    // Set other references to null to improve garbage collection.
    next = null;
    prev = null;
  }

}
