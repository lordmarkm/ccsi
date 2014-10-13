package com.ccsi.commons.reference;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;

/**
 * @author mbmartinez
 * Note: Sun will always be charged 2.
 */
public enum ReplyChargingScheme {
    FREE(0, 0, 2),
    PISO(1, 1, 2),
    TWO_FIFTY(2.5, 2.5, 2),
    FIVE(5, 5, 2),
    TEN(10, 10, 2),
    FIFTEEN(15, 15, 2);

    private BigDecimal smart;
    private BigDecimal globe;
    private BigDecimal sun;

    private ReplyChargingScheme(double smart, double globe, double sun) {
        this.smart = new BigDecimal(smart).setScale(2);
        this.globe = new BigDecimal(globe).setScale(2);
        this.sun = new BigDecimal(sun).setScale(2);
    }

    public String getAmount(Network network) {
        switch (network) {
        case Globe:
            return ZERO.compareTo(globe) == 0 ? "FREE" : globe.toString();
        case Smart:
            return ZERO.compareTo(smart) == 0 ? "FREE" : smart.toString();
        case Sun:
            return ZERO.compareTo(sun) == 0 ? "FREE" : sun.toString();
        default:
            throw new IllegalArgumentException("Unrecognized network: " + network);
        }
    }

    public static void main(String[] args) {
        System.out.println(FREE.getAmount(Network.Smart));
        System.out.println(TWO_FIFTY.getAmount(Network.Globe));
        System.out.println(TEN.getAmount(Network.Sun));
    }
}
