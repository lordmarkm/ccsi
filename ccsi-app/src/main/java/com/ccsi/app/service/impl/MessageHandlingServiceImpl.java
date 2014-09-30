package com.ccsi.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.app.service.MessageHandlingService;
import com.ccsi.app.service.TransactionRecordService;
import com.ccsi.commons.dto.DeliveryNotificationInfo;
import com.ccsi.commons.dto.IncomingMessageInfo;

@Service
public class MessageHandlingServiceImpl implements MessageHandlingService {

    private static Logger LOG = LoggerFactory.getLogger(MessageHandlingServiceImpl.class);

    @Autowired
    private TaskExecutor executor;

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private TransactionRecordService txnService;

    @Override
    public void handleIncomingMessage(IncomingMessageInfo msg) {
        IncomingMessageHandlerTask task = ctx.getBean(IncomingMessageHandlerTask.class);
        task.setMsg(msg);
        executor.execute(task);
    }

    @Override
    public void handleDeliveryNotification(DeliveryNotificationInfo notif) {
        // TODO What else do we do with these?
        LOG.debug("Delivery notification received. notif={}", notif);
        TransactionRecord txn = txnService.findByMessageId(notif.getMessage_id());
        txn.setDelivered(true);
        txnService.save(txn);
    }

}
