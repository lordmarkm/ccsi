package com.ccsi.tester.misc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MiscTester {

    @Test
    public void testCapitalization() {
        assertEquals("A1B2C", "a1B2c".toUpperCase());
    }

}
