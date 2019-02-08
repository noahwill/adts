package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;
import alg.MinSpanTree;
import impl.WeightedGraphFactory;

public abstract class MinSpanTreeTest {

    protected MinSpanTree mstAlg;
    
    protected abstract void reset();
    private void graphFileTest(String file, double target) {
        reset();
        WeightedGraph g = WeightedGraphFactory.weightedUndirectedALGraphFromFile(file);
        double totalWeight = 0.0;
        for (WeightedEdge e : mstAlg.minSpanTree(g)) {
            totalWeight += e.weight;
        }
        assertEquals(target, totalWeight, 0.01);
    }
    @Test
    public void big() {
        graphFileTest("big",4628.0);
    }
    
    @Test
    public void simple() {
        graphFileTest("simple",6.0);
    }
    
    @Test
    public void simpleFullyConnected() {
        graphFileTest("simple-fully-connected",6.0);
    }
    
    @Test
    public void longPathShortPath() {
        graphFileTest("long-path-short-path",51.0);
    }
    
    @Test
    public void minimal() {
        graphFileTest("minimal",1.0);
    }
    
    
    
}
