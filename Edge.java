/* Edge.java */

/**
 * The Edge Class is an object that references the two vertices that
 * make up an edge and and edge's weight.
 */

public class Edge {
  
  /**
  * Objects vertex1 and vertex2 reference the vertices of the edge and
  * weight references the weight of the edge.
  */
  public Object vertex1;
  public Object vertex2;
  public int weight;

  /**
   *  Edge constructs and Edge object.
   **/
  public Edge(Object v1, Object v2, int w) {
  	vertex1 = v1;
  	vertex2 = v2;
  	weight = w;
  }
}
