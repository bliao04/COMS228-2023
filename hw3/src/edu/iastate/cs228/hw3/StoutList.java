package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * @author Brandon Liao
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Comparator used to compare values inside a node, used in sorting algorithm. 
   */
  protected Comparator<E> eComparator = null;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) 
    	throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }
  
  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	  
	if (item == null) {
		throw new NullPointerException();
	}
	
	if (size == 0) {
		
		Node addNode = new Node();
		addNode.addItem(item);
		head.next = addNode;
		addNode.previous = head;
		addNode.next = tail;
		tail.previous = addNode;
		
	} else {
		
		if (tail.previous.count < nodeSize) {
			
			tail.previous.addItem(item);
			
		} else {
			
			Node addNode = new Node();
			addNode.addItem(item);
			Node tempNode = tail.previous;
			tempNode.next = addNode;
			addNode.previous = tempNode;
			addNode.next = tail;
			tail.previous = addNode;
			
		}
		
	}
	
	size++;
    return true;
    
}

  @Override
  public void add(int pos, E item)
  {
	  
	if (pos < 0 || pos > size) {
		throw new IndexOutOfBoundsException();
	}
	  
    if (head.next == tail) {
    	add(item);
    }
    
    NodeInfo curNodeInfo = find(pos);
    Node curNode = curNodeInfo.n;
    int offset = curNodeInfo.offset;
    
    if (offset == 0) {
    	
    	if (curNode.previous.count < nodeSize && curNode.previous != head) {
    		
    		curNode.previous.addItem(item);
    		size++;
    		return;
    		
    	} else if (curNode == tail) {
    		
    		add(item);
    		size++;
    		return;
    		
    	}
    	
    }
    
    if (curNode.count < nodeSize) {
    	
    	curNode.addItem(offset, item);
    	
    } else {
    	
    	Node newNode = new Node();
    	int midPoint = nodeSize / 2;
    	int i = 0;
    	
    	while (i < midPoint) {
    		
    		newNode.addItem(curNode.data[midPoint]);
    		curNode.removeItem(midPoint);
    		i++;
    		
    	}
    	
    	Node oldNode = curNode.next;
    	curNode.next = newNode;
    	newNode.previous = curNode;
    	newNode.next = oldNode;
    	oldNode.previous = newNode;
    	
    	if (offset <= midPoint) {
    		curNode.addItem(offset, item);
    	}
    	
    	if (offset > midPoint) {
    		newNode.addItem(offset - midPoint, item);
    	}
    	
    }
    
    size++;
	  
  }

  @Override
  public E remove(int pos)
  {
	  
	NodeInfo curNodeInfo = find(pos);
	Node curNode = curNodeInfo.n;
	int offset = curNodeInfo.offset;
	E nodeValue = curNode.data[offset];
	int midPoint = nodeSize / 2;
	
	if (curNode.next == tail && curNode.count == 1) {
		
		Node prevNode = curNode.previous;
		prevNode.next = curNode.next;
		curNode.next.previous = prevNode;
		curNode = null;
		
	} else if (curNode.next == tail || curNode.count > midPoint) {
		
		curNode.removeItem(offset);
		
	} else {
		
		curNode.removeItem(offset);
		Node nextNode = curNode.next;
		
		if (nextNode.count > midPoint) {
			
			curNode.addItem(nextNode.data[0]);
			nextNode.removeItem(0);
			
		} else if (nextNode.count <= midPoint) {
			
			for (int i = 0; i < nextNode.count; i++) {
				
				curNode.addItem(nextNode.data[i]);
				
			}
			
			curNode.next = nextNode.next;
			nextNode.next.previous = curNode;
			nextNode = null;
			
		}
		
	}
	
	size--;
	return nodeValue;
	  
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  
	E[] sortedList = (E[]) new Comparable[size];
	Node curNode = head.next;
	int index = 0;
		
	while (curNode != tail) {
			
		for (int i = 0; i < curNode.count; i++) {
				
			sortedList[index] = curNode.data[i];
			index++;
				
		}
			
		curNode = curNode.next;
			
	}
	
	head.next = tail;
	tail.previous = head;
	
	eComparator = new Comparator<E>() {
		@Override
		public int compare(E e1, E e2) {
			
			return e1.compareTo(e2);
			
		}
	};
	
	insertionSort(sortedList, eComparator);
	size = 0;
	
	for (int j = 0; j < sortedList.length; j++) {
		
		add(sortedList[j]);
		
	}
 	  	  
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  
	E[] rSortedList = (E[]) new Comparable[size];
	Node curNode = head.next;
	int index = 0;
	
	while (curNode != tail) {
		
		for (int i = 0; i < curNode.count; i++) {
				
			rSortedList[index] = curNode.data[i];
			index++;
			
		}
			
		curNode = curNode.next;
			
	}
	
	head.next = tail;
	tail.previous = head;
	bubbleSort(rSortedList);
	size = 0;
	
	for (int i = 0; i < rSortedList.length; i++) {
		add(rSortedList[i]);
	}
	
  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {    
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	
	final int prevAction = 0;
	
	final int nextAction = 1;
	
	int lastAction;
	
	int curPosition;
	
	public E[] valList;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	
    	curPosition = 0;
    	lastAction = -1;
    	
    	valList = (E[]) new Comparable[size];
		Node curNode = head.next;
		
		while (curNode != tail) {
			
			for (int i = 0; i < curNode.count; i++) {
				
				valList[i] = curNode.data[i];
				
			}
			
			curNode = curNode.next;
			
		}
    	
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	
    	curPosition = pos;
    	lastAction = -1;
    	
    	valList = (E[]) new Comparable[size];
		Node curNode = head.next;
		
		while (curNode != tail) {
			
			for (int i = 0; i < curNode.count; i++) {
				
				valList[i] = curNode.data[i];
				
			}
			
			curNode = curNode.next;
			
		}
		
    }

    @Override
    public boolean hasNext()
    {
    	
    	if (curPosition < size) {
    		return true;
    	} else {
    		return false;
    	}
    	
    }

    @Override
    public E next()
    {
    	
    	if (!hasNext()) {
    		throw new NoSuchElementException();
    	}
    	
    	lastAction = nextAction;
    	return valList[curPosition++];
    	
    }

    @Override
    public void remove()
    {
    	
    	if (lastAction == prevAction) {
    		
    		StoutList.this.remove(curPosition);
    		lastAction = -1;
    		
    		valList = (E[]) new Comparable[size];
    		Node curNode = head.next;
    		
    		while (curNode != tail) {
    			
    			for (int i = 0; i < curNode.count; i++) {
    				
    				valList[i] = curNode.data[i];
    				
    			}
    			
    			curNode = curNode.next;
    			
    		}
    		
    	} else if (lastAction == nextAction) {
    		
    		StoutList.this.remove(curPosition - 1);
    		lastAction = -1;
    		curPosition--;
    		
    		if (curPosition < 0) {
    			curPosition = 0;
    		}
    		
    		valList = (E[]) new Comparable[size];
    		Node curNode = head.next;
    		
    		while (curNode != tail) {
    			
    			for (int i = 0; i < curNode.count; i++) {
    				
    				valList[i] = curNode.data[i];
    				
    			}
    			
    			curNode = curNode.next;
    			
    		}
    		
    	} else {
    		
    		throw new IllegalStateException();
    		
    	}
    	
    }

	@Override
	public boolean hasPrevious() {
		
		if (curPosition > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public E previous() {
		
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		
		lastAction = prevAction;
		curPosition--;
		
		return valList[curPosition];
	}

	@Override
	public int nextIndex() {
		return curPosition;
	}

	@Override
	public int previousIndex() {
		return curPosition - 1;
	}

	@Override
	public void set(E e) {
		
		if (lastAction == prevAction) {
			
			NodeInfo prevNodeInfo = find(curPosition);
			prevNodeInfo.n.data[prevNodeInfo.offset] = e;
			valList[curPosition] = e;
			
		} else if (lastAction == nextAction) {
			
			NodeInfo nextNodeInfo = find(curPosition - 1);
			nextNodeInfo.n.data[nextNodeInfo.offset] = e;
			valList[curPosition - 1] = e;
			
		} else {
			
			throw new IllegalStateException();
			
		}
		
	}

	@Override
	public void add(E e) {
		
		StoutList.this.add(curPosition, e);
		curPosition++;
		lastAction = -1;
	
		valList = (E[]) new Comparable[size];
		Node curNode = head.next;
		
		while (curNode != tail) {
			
			for (int i = 0; i < curNode.count; i++) {
				
				valList[i] = curNode.data[i];
				
			}
			
			curNode = curNode.next;
			
		}
		
	}
    
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  
	  for (int i = 1; i < arr.length; i++) {
		  
		  E temp = arr[i];
		  int j = i - 1;
		  
		  while (j >= 0 && comp.compare(arr[j], temp) > 0) {
			  
			  arr[j + 1] = arr[j];
			  j--;
					  
		  }
		  
		  arr[j + 1] = temp;
		  
	  }
	  
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  
	  for (int i = 0; i < arr.length - 1; i++) {
		  for (int j = 0; j < arr.length - i - 1; j++) {
			  
			  if (arr[j].compareTo(arr[j+1]) < 0) {
				  
				  E temp = arr[j];
				  arr[j] = arr[j+1];
				  arr[j+1] = temp;	
				  
			  }
			  
		  }
	  }
	  
  }
  
  /**
   * Helper class used to gather and represent the information at a certain point in the list
   */
  private class NodeInfo {
	  
	  public Node n;
	  public int offset;
	  
	  public NodeInfo(Node n, int offset) {
		  this.n = n;
		  this.offset = offset;
	  }
	  
  }
  
  /**
   * Finds the location of a specific item 
   * @param pos position of the given item
   * @return foundInfo of a specific node
   */
  private NodeInfo find(int pos) {
	  
	  Node curNode = head.next;
	  int curPosition = 0;
	  
	  while (curNode != tail) {
		  
		  if (curPosition + curNode.count <= pos) {
			  curPosition += curNode.count;
			  curNode = curNode.next;
			  continue;
		  }
		  
		  NodeInfo foundInfo = new NodeInfo(curNode, pos - curPosition);
		  return foundInfo;
		  
	  }
	  
	  return null;
	  
  }
 
}