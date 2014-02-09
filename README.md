WUGraph-Spanning-Trees
======================

![alt tag](https://raw.github.com/aaron-feldman/WUGraph-Spanning-Trees/master/example_image.png)

This project was completed as part of coursework for CS61B at the University of California, Berkeley.

<b>Specifications:</b>

Implement a well-encapsulated ADT called WUGraph in a package called graph. A WUGraph represents a weighted, undirected graph in which self-edges are allowed.  Any object whatsoever can serve as a vertex of a WUGraph.

For maximum speed, you must store edges in two data structures:  unordered doubly-linked adjacency lists and a hash table.  You are expected to support the following public methods in the running times specified.  (You may ignore hash table resizing time when trying to achieve a specified running time--but your hash table should resize itself when necessary to keep the load factor constant.)  Below, |V| is the number of vertices in the graph, and d is the degree of the vertex in question.


O(1)   WUGraph();                construct a graph having no vertices or edges.

O(1)   int vertexCount();           return the number of vertices in the graph.

O(1)   int edgeCount();                return the number of edges in the graph.

O(|V|) Object[] getVertices();             return an array of all the vertices.

O(1)   void addVertex(Object);                       add a vertex to the graph.

O(d)   void removeVertex(Object);               remove a vertex from the graph.

O(1)   boolean isVertex(Object);          is this object a vertex of the graph?

O(1)   int degree(Object);                       return the degree of a vertex.

O(d)   Neighbors getNeighbors(Object);        return the neighbors of a vertex.

O(1)   void addEdge(Object, Object, int);      add an edge of specified weight.

O(1)   void removeEdge(Object, Object);          remove an edge from the graph.

O(1)   boolean isEdge(Object, Object);               is this edge in the graph?

O(1)   int weight(Object, Object);              return the weight of this edge.


Full project details can be found here: http://www.cs.berkeley.edu/~jrs/61bs13/hw/pj3/readme
