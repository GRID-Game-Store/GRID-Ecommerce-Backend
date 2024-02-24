package com.khomsi.backend.main.checkout.apis.impl;

import com.khomsi.backend.main.checkout.apis.CurrencyService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        CurrencyRate[] rates = restTemplate.getForObject(currencyApiUrl + "&date=" + formattedDate + "&json", CurrencyRate[].class);
        if (rates != null && rates.length > 0) {
            return BigDecimal.valueOf(rates[0].rate());
        } else {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Unable to fetch USD rate from API");
        }
    }
}

record CurrencyRate(int r030, String txt, double rate, String cc, String exchangedate) {
}
