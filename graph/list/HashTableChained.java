/* HashTableChained.java */

package graph.list;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	protected int size;
	protected DList hashArray[];
	protected int hashSize;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
		hashSize = getPrimeGreaterOrEqual((int)(sizeEstimate * 1.5));
		hashArray = new DList[hashSize];
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
		hashSize = 101;
		hashArray = new DList[hashSize];
  }

	public boolean isPrime(int n) {
		if (n <= 1) {
			return false;
		} 
		if (n == 2) {
			return true;
		}
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i <= Math.sqrt(n) + 1; i = i + 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public int getPrimeGreaterOrEqual(int n) {
		if (n % 2 == 0) {
			n++;
		}
		while (!(isPrime(n))) {
			n = n + 2;
		}
		return n;
	}

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
		int a = 5;
		int b = 73;
		int p = 16908799;
		int n = hashArray.length;
		int retValue = (((a * code + b) % p) % n);
		if (retValue < 0) {
			retValue = retValue + hashSize;
		}	
    return retValue;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
		Entry e = new Entry();
		e.key = key;
		e.value = value;
		size++;
    
    // auto resizing 
    if (size >= hashSize * 2) {
      hashSize = hashSize * 2;
      DList[] temp = hashArray;
      hashArray = new DList[hashSize];
      DList tempDList;
      DListNode tempDListNode;
      Entry tempEntry;
      for (int i = 0; i < temp.length; i++) {
        if (temp[i] != null) {
          tempDList = temp[i];
          tempDListNode = tempDList.front();
          while (tempDListNode != null) {
            if (tempDListNode.item !=null) {
              tempEntry = (Entry)tempDListNode.item;
              this.insert(tempEntry.key(), tempEntry.value());
              tempDListNode = tempDList.next(tempDListNode);
            }
          }
        }
      }
    }
    

		int coord = compFunction(key.hashCode());
		if (hashArray[coord] == null) {
			hashArray[coord] = new DList();
		}

	  hashArray[coord].insertFront(e);
    return (Entry)hashArray[coord].front().item;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
		int coord = compFunction(key.hashCode());
		if (hashArray[coord] == null) {
    	return null;
		}
		
		Entry e;
		DListNode temp = hashArray[coord].front();
		while (temp != null) {
			e = (Entry)temp.item;
			if (e.key.equals(key)) {
				return (Entry)temp.item;
			}
			temp = hashArray[coord].next(temp);
		}
		return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
		Entry e;
		int coord = compFunction(key.hashCode());
		
		if (hashArray[coord] == null) {
			return null;
		}

		DListNode temp = hashArray[coord].front();
		while (temp != null) {
			e = (Entry)temp.item;
			if (e.key.equals(key)) {
				hashArray[coord].remove(temp);
				size--;
				return e;
			}
			temp = hashArray[coord].next(temp);
		}
		return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
		hashArray = new DList[hashSize];
		size = 0;
  }

}
