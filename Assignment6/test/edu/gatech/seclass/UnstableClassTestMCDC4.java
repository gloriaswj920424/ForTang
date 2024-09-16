package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class UnstableClassTestMCDC4 {
    @Test
    public void Test1() { UnstableClass.unstableMethod4(true, 0,1,1); }

    @Test
    public void Test2() { UnstableClass.unstableMethod4(false, 0,1,1); }

    @Test
    public void Test3() { UnstableClass.unstableMethod4(false, 1,1,1); }

    @Test
    public void Test4() { UnstableClass.unstableMethod4(false, 1,1,0); }

    @Test
    public void Test5() { UnstableClass.unstableMethod4(false, 1,-1,1); }


}
