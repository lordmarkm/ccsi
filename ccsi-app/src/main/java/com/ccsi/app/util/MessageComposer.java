package com.ccsi.app.util;

import org.springframework.stereotype.Service;

import com.ccsi.app.entity.Template;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.commons.dto.tenant.TemplateInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;

@Service
public class MessageComposer {

    private static final String TRACKING_NO_PH = "<trackingNo>";
    private static final String CUSTOMER_NAME_PH = "<customerName>";
    private static final String TRANSACTION_TYPE_PH = "<transactionType>";

    public String composeMessage(TenantRecord record) {
        Template template = record.getStatus();
        String templateStr = template.getTemplateString();

        templateStr = templateStr
                .replaceAll(TRACKING_NO_PH, record.getTrackingNo())
                .replaceAll(CUSTOMER_NAME_PH, record.getCustomerName())
                .replaceAll(TRANSACTION_TYPE_PH, record.getTransactionType());

        return templateStr;
    }

    public String composeMessage(TenantRecordInfo record, TemplateInfo template) {
        return template.getTemplateString()
                .replaceAll(TRACKING_NO_PH, record.getTrackingNo())
                .replaceAll(CUSTOMER_NAME_PH, record.getCustomerName())
                .replaceAll(TRANSACTION_TYPE_PH, record.getTransactionType());
    }

}
