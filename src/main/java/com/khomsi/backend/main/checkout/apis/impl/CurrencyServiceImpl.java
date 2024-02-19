package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.main.checkout.apis.CurrencyService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${app.payment.currencyApi}")
    private String currencyApiUrl;

    @Override
    public BigDecimal convertToUSD(BigDecimal amountInUAH) {
        return amountInUAH.divide(getUSDRate(), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getUSDRate() {
        RestTemplate restTemplate = new RestTemplate();
        CurrencyRate[] rates = restTemplate.getForObject(currencyApiUrl, CurrencyRate[].class);
        if (rates != null && rates.length > 0) {
            return BigDecimal.valueOf(rates[0].rate());
        } else {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Unable to fetch USD rate from API");
        }
    }
}

record CurrencyRate(int r030, String txt, double rate, String cc, String exchangedate) {
}
