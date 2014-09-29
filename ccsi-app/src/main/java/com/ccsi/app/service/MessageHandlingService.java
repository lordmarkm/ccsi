package com.ccsi.app.service;

import com.ccsi.commons.dto.DeliveryNotificationInfo;
import com.ccsi.commons.dto.IncomingMessageInfo;

public interface MessageHandlingService {

    void handleIncomingMessage(IncomingMessageInfo msg);
    void handleDeliveryNotification(DeliveryNotificationInfo notif);

}
