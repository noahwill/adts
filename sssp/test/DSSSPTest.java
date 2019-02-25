package test;

import org.junit.Test;

import alg.DijkstraSSSP;

public class DSSSPTest extends SSSPTest {

    protected void reset() {

        ssspAlg = new DijkstraSSSP();
    }
    /**
     * This test is too large for bellman ford or a broken implementation of
     * Dijkstra's algorithm to run in 20 seconds (what machine?)
     */
    // as of Feb 2019, this is just too big...
    /*
    @Test(timeout=60000)
    public void tooBigForBF() {
        runSSSP("tooBigForBF",0, 46918.84);
    }
    */
}
