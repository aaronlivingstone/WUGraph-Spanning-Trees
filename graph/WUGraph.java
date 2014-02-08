/* WUGraph.java */

package graph;
import graph.list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */


public class WUGraph {

	protected HashTableChained vertexHashTable;
  protected HashTableChained edgeHashTable;
  protected VList vertexList;
  
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
		vertexHashTable = new HashTableChained();
    edgeHashTable = new HashTableChained();
    vertexList = new VList();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
		return vertexHashTable.size();
	}

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
		return edgeHashTable.size();
	}

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    try {
      
      Object[] verticesArray = new Object[vertexHashTable.size()];
      VListNode current = vertexList.front();
      
      for (int i = 0; i < vertexHashTable.size(); i++) {
        verticesArray[i] = current.item();
        current = current.next();
      }
      return verticesArray;
      
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
    return new Object[0]; // if try fails
	}

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if (vertexHashTable.find(vertex) != null) { // I need to check this one
      return;
    }
    vertexList.insertBack(vertex);
    VListNode value = vertexList.back();
    vertexHashTable.insert(vertex, value);
	}

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if (vertexHashTable.find(vertex) == null) {
      return;
    }
    
    try {
      
      VListNode vert = (VListNode)vertexHashTable.find(vertex).value();
      EList e = vert.edgelist();
      EListNode eNode;
      VertexPair vp;
      
      while (e.length() > 0) {
        eNode = e.front();
        // remove partner
        if (eNode.partner() != eNode) {
          eNode.partner().remove();
        }
        // delete edgeHashTable reference
        vp = new VertexPair(vertex, eNode.item());
        edgeHashTable.remove(vp);
        // delete self
        eNode.remove();
      }
      vert.remove();
      vertexHashTable.remove(vertex);
      
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
	}

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
		if (vertexHashTable.find(vertex) !=null) {
      return true;
    }
    return false;
	}

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    try {
      if (!isVertex(vertex)) {
        return 0;
      }
      VListNode vert = (VListNode)vertexHashTable.find(vertex).value();
      return vert.edgelist().length();
    }  catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
    return 0; // if try fails
	}

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    if (!isVertex(vertex) || degree(vertex) == 0) {
      return null;
    }
    
    Neighbors n = new Neighbors();
    
    try {
      
      VListNode vert = (VListNode)vertexHashTable.find(vertex).value();
      n.neighborList = new Object[vert.edgelist().length()];
      n.weightList = new int[vert.edgelist().length()];
      EListNode eNode = vert.edgelist().front();
      
      for (int i = 0; i < vert.edgelist().length(); i++) {
        n.neighborList[i] = eNode.item();
        n.weightList[i] = eNode.weight();
        eNode = eNode.next();
      }
      
    }  catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
    return n;
  }
	
  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (!isVertex(u) || !isVertex(v)) {
      return;
    }
    
    try {
      
      VertexPair vp = new VertexPair(u, v);
      if (edgeHashTable.find(vp) != null) {
        EListNode e = (EListNode)edgeHashTable.find(vp).value();
        e.partner().setWeight(weight);
        e.setWeight(weight);
        return;
      }
      
      VListNode vertU = (VListNode)vertexHashTable.find(u).value();
      VListNode vertV = (VListNode)vertexHashTable.find(v).value();
      EList edgeListU = vertU.edgelist();
      EList edgeListV = vertV.edgelist();
      
      
      edgeListV.insertFront(u);
      edgeListV.front().setWeight(weight);
      if (vertU != vertV) {
        edgeListU.insertFront(v);
        edgeListU.front().setWeight(weight);
        edgeListU.front().setPartner(edgeListV.front());
      }
      edgeListV.front().setPartner(edgeListU.front());
      
      edgeHashTable.insert(vp, edgeListV.front());
      
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
	}


  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
  
    try {
      
      if (!isVertex(u) || !isVertex(v)) {
        return;
      }
      
      VertexPair vp = new VertexPair(u, v);
      if (edgeHashTable.find(vp) == null) {
        return;
      }
      EListNode e = (EListNode)edgeHashTable.find(vp).value();
      edgeHashTable.remove(vp);
      

      if (e.partner() != e) {
        e.partner().remove();
      }
      e.remove();
            
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
	}

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
		VertexPair vp = new VertexPair(u, v);
    if (edgeHashTable.find(vp) == null) {
      return false;
    }
    return true;
	}

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (!isVertex(u) || !isVertex(v)) {
      return 0;
    }
		VertexPair vp = new VertexPair(u, v);
    if (edgeHashTable.find(vp) == null) {
      return 0;
    }
    try {
      
    EListNode e = (EListNode)edgeHashTable.find(vp).value();
    return e.weight();
      
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
    return 0; // if try fails
	}
  
  // for TESTING
  public void printEdgeList(Object vertex) {
    try {
      VListNode vert = (VListNode)vertexHashTable.find(vertex).value();
      System.out.print("\n EDGE LIST (" + vertex + "): ");
      EListNode e = vert.edgelist().front();
      for (int i = 0; i < vert.edgelist().length(); i++) {
        System.out.print("[" + e.item() + ":" + e.partner().item() + "]");
        e = e.next();
      }
      System.out.print("\n");
        //System.out.println("EDGE LIST: " + (int)vert.edgelist().);
    } catch (InvalidNodeException ine) {
      System.out.println("INE: " + ine);
    }
    
  }
}















