package com.xm.crypto.investment.controller;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller API providing api for
 * 1) calculation characteristics of crypto currency e.g oldest/newest/min/max
 * 2) calculation descending sorted list of cryptos
 *    comparing the normalized range (i.e. (max-min)/min) indicated
 * 3) calculation of crypto with the highest normalized range for a specific day
 */
public interface RecommendationApi {

    /**
     * Endpoint to calculate characteristics of crypto currency e.g oldest/newest/min/max for each symbol
     *
     * @param symbols list of crypto currency symbols we want to calculate characteristics for ( e.g DOGE, BTC, ETH etc )
     * @return HashMap of symbol as key and
     * @see com.xm.crypto.investment.domain.CryptoPriceCharacteristics as value
     */
    @Operation(summary = "calculate characteristics of price",
            description = "calculate characteristics of crypto currency e.g oldest/newest/min/max for each symbol")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}")})
    ResponseEntity<Map<String, CryptoPriceCharacteristics>> calculateCharacteristicsOfPrice(@RequestParam(value = "symbols", required = false) List<String> symbols);

    /**
     * Endpoint to calculate descending sorted list of cryptos
     * comparing the normalized range (i.e. (max-min)/min) indicated by symbols
     *
     * @param symbols list of crypto currency symbols we want ( e.g DOGE, BTC, ETH etc )
     * @return descending sorted list of cryptos comparing the normalized range (i.e. (max-min)/min)
     */
    @Operation(summary = "calculate descending sorted list of cryptos ",
            description = " Endpoint to calculate descending sorted list of cryptos comparing the normalized range")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}")})
     ResponseEntity<List<String>> getCryptoSymbolsSortedDescByNormalizedRange(@RequestParam(value = "symbols", required = false) List<String> symbols);

    /**
     * Endpoint to calculate the crypto with the highest normalized range for a specific day
     *
     * @param date day for which we want to calculate highest normalized range
     * @param symbols list of crypto symbols for which we want to calculate highest normalized range
     * @return Java Object containing crypto symbol, date and list of prices which are highest normalized
     * @see com.xm.crypto.investment.domain.CryptoWithNormalizedRange for reference
     */
    @Operation(summary = "calculate the crypto with the highest normalized range",
            description = "Endpoint to calculate the crypto with the highest normalized range for a specific day")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}")})
     ResponseEntity<CryptoWithNormalizedRange> getCryptoSymbolWithHighestNormalizedRange(@RequestParam("date") LocalDate date,
                                                                                         @RequestParam(value = "symbols", required = false) List<String> symbols);
}
