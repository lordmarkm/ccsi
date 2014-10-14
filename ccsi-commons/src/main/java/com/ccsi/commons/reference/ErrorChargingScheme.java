package com.ccsi.commons.reference;

import java.math.BigDecimal;

/**
 * @author mbmartinez
 */
public class ErrorChargingScheme {

    public final static BigDecimal ERROR_COST_SMART = BigDecimal.ONE.setScale(2);
    public final static BigDecimal ERROR_COST_GLOBE = BigDecimal.ONE.setScale(2);
    public final static BigDecimal ERROR_COST_SUN = new BigDecimal(2).setScale(2);

}
