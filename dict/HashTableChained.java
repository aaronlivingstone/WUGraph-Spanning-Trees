/* HashTableChained.java */

package dict;
import list.*;

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
    DList [] buckets;
    int numBuckets;
    final int p = 222222227;
    int a;
    int b;
    int numEntries;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
      numBuckets = sizeEstimate * 2 + 1;
      buckets = new DList [numBuckets];
      for (int i = 0; i < numBuckets; i++) {
          buckets[i] = new DList();
      }
      java.util.Random rand = new java.util.Random();
      a = rand.nextInt(p-1) + 1;
      b = rand.nextInt(p);
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
      buckets = new DList [101]; 
      for (int i = 0; i < 101; i++) {
          buckets[i] = new DList();
      }
      numBuckets = 101;  
      java.util.Random rand = new java.util.Random();
      a = rand.nextInt(p-1) + 1;
      b = rand.nextInt(p);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
      return (int) ((Math.abs(a * code + b) % p) % numBuckets); 
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return numEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
      if (numEntries == 0)
          return true;
      return false;
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
      Entry newEntry = new Entry ();
      newEntry.key = key;
      newEntry.value = value;
      int code = newEntry.key.hashCode();
      int index = compFunction(code);
      buckets[index].insertFront(newEntry);
      numEntries++;
      return newEntry;
      
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
      int code = key.hashCode();
      int index = compFunction (code);
      if (buckets[index].isEmpty()) {
          return null;
      }
      else {
          DListNode node = buckets[index].front();
          Entry check = node.item;
          while (node != null) {
              check = node.item;
              if (check.key.equals(key)) {
                  return check;
	      }
              node = buckets[index].next(node);
	  }
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
      int code = key.hashCode();
      int index = compFunction (code);
      if (buckets[index].isEmpty()) {
          return null;
      }
      else {
          DListNode node = buckets[index].front();
          Entry check = node.item;
          while (node != null) {
              check = node.item;
              if (check.key.equals(key)) {
                  buckets[index].remove(node);
                  numEntries--;
                  return check;
	      }
              node = buckets[index].next(node);
	  }
      }
      return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
      for (int i = 0; i < buckets.length - 1; i++) {
          buckets[i] = new DList();
      }
      numEntries = 0;
  }

}
