package test;

import impl.ArrayMap;


public class AMTest extends MapStressTest {
    

    

    @Override
    protected void reset() {
        testMap = new ArrayMap<String, String>();
    }
    @Override
    protected void resetInteger() {
        testMapInt = new ArrayMap<Integer, Integer>();
        
    }
  
}
