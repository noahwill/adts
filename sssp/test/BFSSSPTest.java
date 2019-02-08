package test;

import org.junit.Test;

import alg.BellmanFordSSSP;

public class BFSSSPTest extends SSSPTest {

    protected void reset() {
        ssspAlg = new BellmanFordSSSP();
    }
    
    @Test
    public void negatives() {
        runSSSP("negatives",0, -1);
    }
    

}
