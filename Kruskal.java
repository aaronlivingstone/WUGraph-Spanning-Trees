/* Kruskal.java */

import graph.*;
import set.*;
import dict.*;

/**
 *  The Kruskal class contains the method minSpanTree(), which implements
 *  Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   *  minSpanTree() returns a WUGraph that represents the minimum spanning tree
   *  of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g) {
 	  WUGraph t = new WUGraph();
  	
    /**
     *  [1]
     */
    Object[] verticesInG = g.getVertices();
  	for (int i = 0; i < verticesInG.length; i++) {
  	  t.addVertex(verticesInG[i]);
  	}
  	
    /**
     *  [2]
     */
    Edge[] edgesInG = new Edge[g.edgeCount()];
  	int edgeIndex = 0;
  	for (int i = 0; i < verticesInG.length; i++) {
  	  Neighbors neighborsOfV = g.getNeighbors(verticesInG[i]);
  	  if (neighborsOfV != null) {
  	    for(int k = 0; k < neighborsOfV.neighborList.length; k++) {
  	     // Edge edge = new Edge(verticesInG[i], neighborsOfV.neighborList[k], neighborsOfV.weightList[k]);
  	      edgesInG[edgeIndex] = new Edge(verticesInG[i], neighborsOfV.neighborList[k], neighborsOfV.weightList[k]);
  	      edgeIndex++;
  	    }
  	  }
  	}
    
    /**
     *  [3]
     */
    quicksort(edgesInG);
    
    /**
     *  [4]
     */
    DisjointSets forest = new DisjointSets(verticesInG.length);
    HashTableChained disjointTable = new HashTableChained(verticesInG.length);
    for (int i = 0; i < verticesInG.length; i++) {
      disjointTable.insert(verticesInG[i], i);
    }
    for (Edge e: edgesInG) {
      int rootOfv1 = forest.find(disjointTable.find(e.vertex1));
      int rootOfv2 = forest.find(disjointTable.find(e.vertex2));
      if (rootOfv1 != rootOfv2) {
        t.addEdge(e.vertex1, e.vertex2, e.weight);
        forest.union(rootOfv1, rootOfv2);
      }
    }
    
    return t;
  }

  /**
   *  Quicksort algorithm for edges based on the quicksort algorithm
   *  from lab 12.
   *  @param a an array of edge items.
   **/
  public static void quicksort(Edge[] a) {
    quicksort(a, 0, a.length - 1);
  }

  /**
   *  Method to swap two edges in an array.
   *  @param a an array of edges.
   *  @param index1 the index of the first edge to be swapped.
   *  @param index2 the index of the second edge to be swapped.
   **/
  public static void swapReferences(Edge[] a, int index1, int index2) {
    Edge tmp = a[index1];
    a[index1] = a[index2];
    a[index2] = tmp;
  }

  /**
   *  This is a generic version of C.A.R Hoare's Quick Sort algorithm.  This
   *  will handle arrays that are already sorted, and arrays with duplicate
   *  keys.
   *
   *  If you think of an array as going from the lowest index on the left to
   *  the highest index on the right then the parameters to this function are
   *  lowest index or left and highest index or right.  The first time you call
   *  this function it will be with the parameters 0, a.length - 1.
   *
   *  @param a       an edge array
   *  @param lo0     left boundary of array partition
   *  @param hi0     right boundary of array partition
   **/
   private static void quicksort(Edge a[], int lo0, int hi0) {
     int lo = lo0;
     int hi = hi0;
     int mid;

     if (hi0 > lo0) {

       // Arbitrarily establishing partition element as the midpoint of
       // the array.
       swapReferences(a, lo0, (lo0 + hi0)/2);
       mid = a[(lo0 + hi0) / 2].weight;

       // loop through the array until indices cross.
       while (lo <= hi) {
         // find the first element that is greater than or equal to 
         // the partition element starting from the left Index.
         while((lo < hi0) && (a[lo].weight < mid)) {
           lo++;
         }

         // find an element that is smaller than or equal to 
         // the partition element starting from the right Index.
         while((hi > lo0) && (a[hi].weight > mid)) {
           hi--;
         }
         // if the indices have not crossed, swap them.
         if (lo <= hi) {
           swapReferences(a, lo, hi);
           lo++;
           hi--;
         }
       }

       // If the right index has not reached the left side of array
       // we must now sort the left partition.
       if (lo0 < hi) {
         quicksort(a, lo0, hi);
       }

       // If the left index has not reached the right side of array
       // must now sort the right partition.
       if (lo < hi0) {
         quicksort(a, lo, hi0);
       }
     }
   }
}











