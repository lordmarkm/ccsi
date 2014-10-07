package com.ccsi.app.service;

import java.util.Map;

/**
 * @author markm
 */
public interface BroadcastHandlingService {

    void broadcastStatus(Long tenantId, Map<String, String> optionalParams);
    void broadcastStock(Long tenantId, Map<String, String> optionalParams);
    void broadcastCustom(Long tenantId, Map<String, String> optionalParams);

}
