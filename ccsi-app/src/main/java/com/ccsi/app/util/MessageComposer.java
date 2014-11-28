package com.ccsi.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.Variable;
import com.ccsi.app.service.VariableService;
import com.ccsi.commons.dto.tenant.TemplateInfo;

@Service
public class MessageComposer {

    private static Logger LOG = LoggerFactory.getLogger(MessageComposer.class);

    private static final String TRACKING_NO_PH = "<trackingNo>";
    private static final String CUSTOMER_NAME_PH = "<customerName>";
    private static final String TRANSACTION_TYPE_PH = "<transactionType>";

    @Autowired
    private VariableService variableService;

    public String composeMessage(TenantRecord record, String templateStr) {
        templateStr = templateStr
                .replaceAll(TRACKING_NO_PH, record.getTrackingNo())
                .replaceAll(CUSTOMER_NAME_PH, record.getCustomerName())
                .replaceAll(TRANSACTION_TYPE_PH, record.getTransactionType());

        //First replace variables that have record-specific values
        for (Variable var : variableService.findByTenant_idAndRecord_id(record.getTenant().getId(), record.getId(), null)) {
            templateStr = templateStr.replaceAll(replaceableString(var.getKey()), var.getValue());
        }
        //Then replace the rest with defaults
        for (Variable var : variableService.findByTenant_idAndRecord_id(record.getTenant().getId(), null, null)) {
            templateStr = templateStr.replaceAll(replaceableString(var.getKey()), var.getValue());
        }

        return templateStr;
    }

    public String composeMessage(TenantRecord record) {
        return composeMessage(record, record.getStatus().getTemplateString());
    }

    //For previews
    public String composeMessage(TenantRecord record, TemplateInfo template) {
        return composeMessage(record, template.getTemplateString());
    }

    private String replaceableString(String key) {
        return new StringBuilder("<").append(key).append(">").toString();
    }
}
