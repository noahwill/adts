package test;

import impl.ListStack;


public class LSTest extends StackTest {

    protected void reset() {
        testStack = new ListStack<String>();
    }
    protected void resetInt() {
        testStackInt = new ListStack<Integer>();
    }


}
