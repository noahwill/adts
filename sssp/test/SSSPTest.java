package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import impl.WeightedGraphFactory;

import org.junit.Test;

import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;
import alg.SSSP;

public abstract class SSSPTest {

    protected SSSP ssspAlg;
    
    protected abstract void reset();
    
    public void runSSSP(String graph, int start, double correctWeight) {
        reset();

        WeightedGraph g = WeightedGraphFactory.weightedDirectedALGraphFromFile(graph);
        if (g == null) {
            fail("File \""+graph+ "\" not found\n");
        }
        double totalWeight = 0.0;
        
        for (WeightedEdge e : ssspAlg.sssp(g, start)) {
            totalWeight += e.weight;
        }
        
        assertEquals(correctWeight, totalWeight, 0.01);

    }
    
    @Test
    public void minimal() {
        runSSSP("minimal",0, 1);
        runSSSP("minimal",1, 0);
    }
    
    @Test
    public void simple() {
        runSSSP("simple",0, 6);
        runSSSP("simple",1, 9);
        runSSSP("simple",2, 8);
        runSSSP("simple",3, 7);
    }
    
    @Test
    public void simpleFullyConnected() {
        runSSSP("simple-fully-connected",0, 6);
        runSSSP("simple-fully-connected",1, 9);
        runSSSP("simple-fully-connected",2, 8);
        runSSSP("simple-fully-connected",3, 7);
    }
    @Test
    public void longPathShortPath() {
        runSSSP("long-path-short-path",0, 51);
        runSSSP("long-path-short-path",1, 641);
        runSSSP("long-path-short-path",2, 641);
        runSSSP("long-path-short-path",3, 641);
        runSSSP("long-path-short-path",4, 641);
        runSSSP("long-path-short-path",5, 641);
        runSSSP("long-path-short-path",6, 1140);
    }
    @Test
    public void big() {
        runSSSP("big",0, 17045);
    }
    

}
