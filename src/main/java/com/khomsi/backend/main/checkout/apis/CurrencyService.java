package com.khomsi.backend.main.checkout.apis;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal convertToUSD(BigDecimal amountInUAH);
}
