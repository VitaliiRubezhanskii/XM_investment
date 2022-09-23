package com.xm.crypto.investment.service;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Recommendation Service aimed to provide crypto recommendation
 * based on calculations
 */
public interface RecommendationService {

    /**
     * Method to calculate characteristics of crypto currency e.g oldest/newest/min/max
     *
     * @param symbols list of crypto currency symbols we want to calculate characteristics for ( e.g DOGE, BTC, ETH etc )
     * @return HashMap of symbol as key and
     * @see com.xm.crypto.investment.domain.CryptoPriceCharacteristics as value
     */
    Map<String, CryptoPriceCharacteristics> calculateCharacteristicsOfPrice(final List<String> symbols);

    /**
     * Method to calculate descending sorted list of cryptos
     * comparing the normalized range (i.e. (max-min)/min) indicated by
     * @param symbols
     *
     * @param symbols list of crypto currency symbols we want ( e.g DOGE, BTC, ETH etc )
     * @return descending sorted list of cryptos comparing the normalized range (i.e. (max-min)/min)
     */
    List<String> getCryptoSymbolsSortedDescByNormalizedRange(final List<String> symbols);

    /**
     * Method to calculate the crypto with the highest normalized range for a
     * specific day
     *
     * @param date day for which we want to calculate highest normalized range
     * @param symbols list of crypto symbols for which we want to calculate highest normalized range
     * @return Java Object containing crypto symbol, date and list of prices which are highest normalized
     * @see com.xm.crypto.investment.domain.CryptoWithNormalizedRange for reference
     */
    CryptoWithNormalizedRange getCryptoSymbolWithHighestNormalizedRange(final LocalDate date, final List<String> symbols);
}
