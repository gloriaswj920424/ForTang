package edu.gatech.seclass;

import org.junit.jupiter.api.Test;


public class UnstableClassTestBC2 {

    @Test
    public void Test1() { UnstableClass.unstableMethod2(2, 0); }


    @Test
    public void Test2() { UnstableClass.unstableMethod2(-2, 0); }
}
