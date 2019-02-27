package alg;

import impl.BasicHashSet;
import impl.HeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * PrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
public class PrimMinSpanTree implements MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
    public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
    	// Initialize the set of MST Edges to the empty set
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        
        VertexRecord[] records = new VertexRecord[g.numVertices()];
        // Initialize all vertices with a distance of infinity...
        for (int i = 0; i < g.numVertices(); i++)
            records[i] = new VertexRecord(i, Double.POSITIVE_INFINITY);
        
        // Initialize a priority queue with all the vertices
        PriorityQueue<VertexRecord> pq = 
                new HeapPriorityQueue<VertexRecord>(records, new VertexRecord.VRComparator());
        
        // ... and with parents as -1
        int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            parents[i] = -1;
        
        while (!pq.isEmpty()) {
        	VertexRecord u = pq.extractMax();
        	if (parents[u.id] != -1)
        		mstEdges.add(new WeightedEdge(parents[u.id], u.id, g.weight(parents[u.id], u.id), false));
        	for(int v : g.adjacents(u.id)) {
        		WeightedEdge e = new WeightedEdge(v, u.id, g.weight(v, u.id), false);
        		if(pq.contains(records[v]) && e.weight < records[v].getDistance()) {
        			parents[v] = u.id;
        			records[v].setDistance(e.weight);
        			pq.increaseKey(records[v]);
        		}
        	}
        }
        return mstEdges;
    }

}



/*WeightedEdge e = new WeightedEdge(0, 0 , 0, false);
for(WeightedEdge a : g.edges()) {
	if ((a.first == u.id || a.first == v) && (a.second == u.id || a.second == v))
		e = a;
		break;
}*/
