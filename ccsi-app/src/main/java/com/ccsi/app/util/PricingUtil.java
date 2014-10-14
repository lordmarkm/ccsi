package com.ccsi.app.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.commons.exception.NetworkNotSupportedException;
import com.ccsi.commons.reference.ErrorChargingScheme;
import com.ccsi.commons.reference.Network;
import com.ccsi.commons.reference.ReplyChargingScheme;
import com.google.common.collect.Maps;

/**
 * @author mbmartinez
 */
@Service
public class PricingUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PricingUtil.class);
    private static final String PREFIXES = "prefixes.csv";
    private static final String COLUMN_PREFIX = "Prefix";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_CHARGING_SCHEME = "Charging scheme";

    private Map<String, NetworkInfo> prefixMap = Maps.newHashMap();

    @PostConstruct
    public void init() throws IOException {
        LOG.debug("Loading data from prefixes.csv");

        Resource resource = new ClassPathResource(PREFIXES);
        for (CSVRecord record : CSVFormat.RFC4180.withHeader()
                .withDelimiter(',').parse(new InputStreamReader(resource.getInputStream()))) {
            NetworkInfo networkInfo = new NetworkInfo();
            networkInfo.setNetwork(Network.valueOf(record.get(COLUMN_CHARGING_SCHEME)));
            networkInfo.setDescription(record.get(COLUMN_DESCRIPTION));
            prefixMap.put(record.get(COLUMN_PREFIX), networkInfo);
        }
    }

    /**
     * mobile_number: 639157777777, determinant here is 915
     * @throws NetworkNotSupportedException 
     */
    public BigDecimal determineErrorCost(String mobile_number, TransactionRecord txn) throws NetworkNotSupportedException {
        String determinant = mobile_number.substring(2, 5);
        NetworkInfo networkInfo = prefixMap.get(determinant);
        if (null == networkInfo) {
            throw new NetworkNotSupportedException();
        }

        txn.setNetwork(networkInfo.getNetwork());
        txn.setNetworkDescription(networkInfo.getDescription());

        switch (networkInfo.getNetwork()) {
        case Globe:
            return ErrorChargingScheme.ERROR_COST_GLOBE;
        case Smart:
            return ErrorChargingScheme.ERROR_COST_SMART;
        case Sun:
            return ErrorChargingScheme.ERROR_COST_SUN;
        default:
            throw new NetworkNotSupportedException();
        }
    }

    public BigDecimal determineReplyCost(ReplyChargingScheme replyCharge, String mobile_number, TransactionRecord txn) throws NetworkNotSupportedException {
        String determinant = mobile_number.substring(2, 5);
        NetworkInfo networkInfo = prefixMap.get(determinant);
        if (null == networkInfo) {
            throw new NetworkNotSupportedException();
        }

        txn.setNetwork(networkInfo.getNetwork());
        txn.setNetworkDescription(networkInfo.getDescription());

        return replyCharge.getAmount(networkInfo.getNetwork());
    }

    public String asCostString(BigDecimal cost) {
        return BigDecimal.ZERO.compareTo(cost) == 0 ? "FREE" : cost.toString();
    }

}
