package com.ccsi.web.endpoint;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccsi.commons.dto.DeliveryNotificationInfo;
import com.ccsi.commons.dto.IncomingMessageInfo;

/**
 * @author mbmartinez
 */
@Controller
public class ChikkaEndpoint {

    @RequestMapping(value = "/message", method = POST)
    public ResponseEntity<String> message(@RequestBody IncomingMessageInfo msg) {
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/notification", method = POST)
    public ResponseEntity<String> notification(@RequestBody DeliveryNotificationInfo msg) {
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
