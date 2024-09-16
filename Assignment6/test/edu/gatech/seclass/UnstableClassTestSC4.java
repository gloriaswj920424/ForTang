package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class UnstableClassTestSC4 {

    @Test
    public void Test1() { UnstableClass.unstableMethod4(true, 1,1,1); }

    @Test
    public void Test2() { UnstableClass.unstableMethod4(false, 1,1,1); }

    @Test
    public void Test3() { UnstableClass.unstableMethod4(false, 0,0,0); }
}
