package com.xm.crypto.investment.controller;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.xm.crypto.investment.utils.TestUtils.expectedCharacteristics;
import static com.xm.crypto.investment.utils.TestUtils.expectedCryptoWithNormalizedRange;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    public void calculateCharacteristicsOfPriceTest() throws Exception {
        Map<String, CryptoPriceCharacteristics> expectedCharacteristics = expectedCharacteristics();
        when(recommendationService.calculateCharacteristicsOfPrice(List.of("BTC", "DOGE"))).thenReturn(expectedCharacteristics);

        mockMvc.perform(get("/price/characteristics/symbol")
                .contentType("application/json")
                .param("symbols", "BTC", "DOGE"))
                .andExpect(status().isOk())

                .andExpect(content().json("""
                        {'BTC': {'max': 46979.61, 'min': 46813.21, 'oldest': 46813.21, 'newest': 46979.61 },
                        'DOGE': {'max': 0.1722, 'min': 0.1702, 'oldest': 0.1702, 'newest': 0.1722} }"""
                ))
                .andReturn();
    }

    @Test
    public void getCryptoSymbolsSortedDescByNormalizedRangeTest() throws Exception {
        when(recommendationService.getCryptoSymbolsSortedDescByNormalizedRange(List.of("BTC", "DOGE"))).thenReturn(List.of("DOGE", "BTC"));
        mockMvc.perform(get("/normalized-range/sorted-desc/symbol")
                .contentType("application/json")
                .param("symbols", "BTC", "DOGE"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        ["DOGE", "BTC"]
                        """))
                .andReturn();
    }

    @Test
    public void getCryptoSymbolWithHighestNormalizedRangeTest() throws Exception {
        when(recommendationService.getCryptoSymbolWithHighestNormalizedRange(LocalDate.of(2022, 01, 1),
                List.of("BTC", "DOGE"))).thenReturn(expectedCryptoWithNormalizedRange());

        mockMvc.perform(get("/normalized-range/highest/symbol")
                .contentType("application/json")
                .param("date", "2022-01-01")
                .param("symbols", "BTC", "DOGE"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { 'symbol':'BTC', 'date':2022-01-01, 'prices':[46813.21, 46979.61] }
                        """))
                .andReturn();
    }
}
