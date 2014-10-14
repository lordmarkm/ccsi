package com.ccsi.app.util;

import java.math.BigDecimal;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.ccsi.commons.dto.IncomingMessageInfo;

/**
 * @author mbmartinez
 */
@Service
public class MessageUtil {

    /**
     * Needs to return a 32-character string
     * TODO
     */
    public String generateId() {
        return RandomStringUtils.randomAlphabetic(32);
    }

    public String determineCost(IncomingMessageInfo msg) {
        return "FREE";
    }

    /**
     * Tenant keyword = everything that comes before the last element
     * Tracking no = always the last element
     */
    public static String[] messageBreakdown(String message) throws Exception {
        if (null == message || message.length() < 1) {
            return null;
        }
        String msg = message.trim();
        int firstspace = msg.indexOf(' ');
        String tenantCode = msg.substring(0, firstspace).trim();
        String trackingNoOrKeyword = msg.substring(tenantCode.length()).trim();

        return new String[]{tenantCode, trackingNoOrKeyword};
    }

}
