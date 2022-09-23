package com.xm.crypto.investment.utils;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoPriceData;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TestUtils {

    public static List<CryptoPriceData> expectedCsvContent() {
        return List.of(
                new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 6, 00), "BTC", new BigDecimal("46813.21")),
                new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 9, 00), "BTC", new BigDecimal("46979.61")),
                new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 7, 00), "DOGE", new BigDecimal("0.1702")),
                new CryptoPriceData(LocalDateTime.of(2022, 01, 2, 00, 00), "DOGE", new BigDecimal("0.1722"))
        );
    }

    public static List<Map<String, List<CryptoPriceData>>> expectedCsvContentAsStreamMap() {
        return List.of(
                Map.of("BTC", List.of(
                        new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 6, 00), "BTC", new BigDecimal("46813.21")),
                        new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 9, 00), "BTC", new BigDecimal("46979.61"))
                )),
                Map.of("DOGE", List.of(
                        new CryptoPriceData(LocalDateTime.of(2022, 01, 1, 7, 00), "DOGE", new BigDecimal("0.1702")),
                        new CryptoPriceData(LocalDateTime.of(2022, 01, 2, 00, 00), "DOGE", new BigDecimal("0.1722"))
                ))
        );
    }

    public static Map<String, CryptoPriceCharacteristics> expectedCharacteristics() {
        return Map.of(
                "BTC", new CryptoPriceCharacteristics(new BigDecimal("46979.61"), new BigDecimal("46813.21"), new BigDecimal("46813.21"), new BigDecimal("46979.61")),
                "DOGE", new CryptoPriceCharacteristics(new BigDecimal("0.1722"), new BigDecimal("0.1702"), new BigDecimal("0.1702"), new BigDecimal("0.1722")));
    }

    public static CryptoWithNormalizedRange expectedCryptoWithNormalizedRange() {
        return new CryptoWithNormalizedRange( "BTC", LocalDate.of(2022, 01, 01), List.of(new BigDecimal("46813.21"), new BigDecimal("46979.61")));
    }
}
