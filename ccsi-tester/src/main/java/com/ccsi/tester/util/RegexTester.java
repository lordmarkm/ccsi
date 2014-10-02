package com.ccsi.tester.util;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTester {

    @Test
    public void test() {
        String pattern = "[a-zA-Z0-9]*";
        assertTrue(Pattern.matches(pattern, "helloworld"));
    }

}
