package com.ccsi.app.util;

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

}
