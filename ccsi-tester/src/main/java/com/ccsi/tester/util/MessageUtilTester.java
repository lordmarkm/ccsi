package com.ccsi.tester.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ccsi.app.util.MessageUtil;

public class MessageUtilTester {

    @Test
    public void testMessageBreakdown() throws Exception {
        String msg = "titan help fnm";
        String[] breakdown = MessageUtil.messageBreakdown(msg);
        assertEquals("titan", breakdown[0]);
        assertEquals("help fnm", breakdown[1]);

        msg = "titan fnm";
        breakdown = MessageUtil.messageBreakdown(msg);
        assertEquals("titan", breakdown[0]);
        assertEquals("fnm", breakdown[1]);
    }
}
