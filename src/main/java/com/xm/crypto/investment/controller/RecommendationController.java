package com.xm.crypto.investment.controller;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;
import com.xm.crypto.investment.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller API providing api
 * for more info
 * @see com.xm.crypto.investment.controller.RecommendationApi
 */
@RestController
@RequiredArgsConstructor
public class RecommendationController implements RecommendationApi  {

    private final RecommendationService recommendationService;

    @GetMapping("/price/characteristics/symbol")
    public ResponseEntity<Map<String, CryptoPriceCharacteristics>> calculateCharacteristicsOfPrice(@RequestParam(value = "symbols", required = false) List<String> symbols) {
        return new ResponseEntity<>(recommendationService.calculateCharacteristicsOfPrice(symbols), HttpStatus.OK);
    }

    @GetMapping("/normalized-range/sorted-desc/symbol")
    public ResponseEntity<List<String>> getCryptoSymbolsSortedDescByNormalizedRange(@RequestParam(value = "symbols", required = false) List<String> symbols) {
        return new ResponseEntity<>(recommendationService.getCryptoSymbolsSortedDescByNormalizedRange(symbols), HttpStatus.OK);
    }

    @GetMapping("/normalized-range/highest/symbol")
    public ResponseEntity<CryptoWithNormalizedRange> getCryptoSymbolWithHighestNormalizedRange(@RequestParam("date") LocalDate date,
                                                                                               @RequestParam(value = "symbols", required = false) List<String> symbols) {
        return new ResponseEntity<>(recommendationService.getCryptoSymbolWithHighestNormalizedRange(date, symbols), HttpStatus.OK);
    }
}
