package com.ccsi.web.resource;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baldy.commons.web.controller.GenericController;
import com.ccsi.app.service.BroadcastHandlingService;
import com.google.common.collect.Maps;

/**
 * @author pilabaldeh
 */
@RestController
@RequestMapping("/broadcast/{tenantId}")
public class BroadcastResource extends GenericController {

    private static Logger LOG = LoggerFactory.getLogger(BroadcastResource.class);

    @Autowired
    private BroadcastHandlingService broadcaster;

    @RequestMapping(method = POST)
    public ResponseEntity<Object> page(Principal principal,
            @PathVariable Long tenantId,
            @RequestParam String broadcastType,

            //Optional broadcast params, but may be required based on broadcastType
            @RequestParam(required = false) String stockBroadcast,
            @RequestParam(required = false) String customBroadcast,

            //Optional filtering params
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String trackingNo,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String transactionType) {

        LOG.debug("Broadcast request query. Principal={}, tenant={}", name(principal), tenantId);

        Map<String, String> optionalParams = Maps.newHashMap();
        optionalParams.put("status", StringUtils.trimToNull(status));
        optionalParams.put("trackingNo", StringUtils.trimToNull(trackingNo));
        optionalParams.put("customerName", StringUtils.trimToNull(customerName));
        optionalParams.put("transactionType", StringUtils.trimToNull(transactionType));
        optionalParams.put("requireBroadcastNo", "true");

        try {
            switch (broadcastType) {
            case "status":
                broadcaster.broadcastStatus(tenantId, optionalParams);
                break;
            case "stock":
                if (null == stockBroadcast) {
                    return error("Keyword template required for keyword template broadcast");
                }
                optionalParams.put("keyword", stockBroadcast);
                broadcaster.broadcastStock(tenantId, optionalParams);
                break;
            case "custom":
                if (null == customBroadcast) {
                    return error("Custom message required for this broadcast type.");
                }
                optionalParams.put("customBroadcast", customBroadcast);
                broadcaster.broadcastCustom(tenantId, optionalParams);
                break;
            default:
                return error("Unrecognized broadcast type=" + broadcastType);
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }

        return new ResponseEntity<Object>("Ok", OK);
    }

    private ResponseEntity<Object> error(String message) {
        return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
    }
}
