package com.xm.crypto.investment.service;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.xm.crypto.investment.utils.TestUtils.expectedCharacteristics;
import static com.xm.crypto.investment.utils.TestUtils.expectedCryptoWithNormalizedRange;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecommendationServiceTest {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationServiceTest(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Test
    public void calculateCharacteristicsOfPriceTest() {
        Map<String, CryptoPriceCharacteristics> actualCharacteristics = recommendationService.calculateCharacteristicsOfPrice(List.of("BTC", "DOGE"));
        assertEquals(expectedCharacteristics(), actualCharacteristics);
    }

    @Test
    public void getCryptoSymbolsSortedDescByNormalizedRange() {
       List<String> actualSymbols = recommendationService.getCryptoSymbolsSortedDescByNormalizedRange(List.of("BTC", "DOGE"));
       assertEquals(List.of("DOGE", "BTC"), actualSymbols);
    }

    @Test
    public void getCryptoSymbolWithHighestNormalizedRange() {
      CryptoWithNormalizedRange cryptoWithNormalizedRange = recommendationService.getCryptoSymbolWithHighestNormalizedRange(
              LocalDate.of(2022, 01, 1), List.of("BTC", "DOGE"));
      assertEquals(expectedCryptoWithNormalizedRange(), cryptoWithNormalizedRange);
    }
}
