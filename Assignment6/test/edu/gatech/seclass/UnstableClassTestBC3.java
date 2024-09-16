package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class UnstableClassTestBC3 {
    @Test
    public void Test1() { UnstableClass.unstableMethod3(3, 3); }

    @Test
    public void Test2() { UnstableClass.unstableMethod3(0, 0); }
}
