package com.ccsi.app.util;

import java.math.BigDecimal;
import static java.math.BigDecimal.*;

/**
 * @author mbmartinez
 */
public class BigDecimalUtil {

    public static BigDecimal zeroIfNull(BigDecimal d) {
        return null != d ? d : ZERO;
    }

}
