package com.ccsi.app.service;

import java.math.BigDecimal;
import java.util.Map;

import com.ccsi.app.exception.InsufficientPushCreditsException;

/**
 * @author markm
 */
public interface BroadcastHandlingService {

    BigDecimal broadcastStatus(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException;
    BigDecimal broadcastStock(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException;
    BigDecimal broadcastCustom(Long tenantId, Map<String, String> optionalParams) throws InsufficientPushCreditsException;

}
