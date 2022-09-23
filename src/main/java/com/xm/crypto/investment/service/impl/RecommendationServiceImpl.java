package com.xm.crypto.investment.service.impl;

import com.xm.crypto.investment.domain.CryptoPriceCharacteristics;
import com.xm.crypto.investment.domain.CryptoPriceData;
import com.xm.crypto.investment.domain.CryptoWithNormalizedRange;
import com.xm.crypto.investment.service.ReaderService;
import com.xm.crypto.investment.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 *  Recommendation Service implementation aimed to provide crypto recommendation
 *  based on calculations. For more info on methods see
 * @see com.xm.crypto.investment.service.RecommendationService
 */
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final ReaderService readerService;

    @Override
    public Map<String, CryptoPriceCharacteristics> calculateCharacteristicsOfPrice(final List<String> symbols) {
        return cryptoPriceCharacteristicsStream(symbols)
                .flatMap(entry -> entry.entrySet().stream()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<String> getCryptoSymbolsSortedDescByNormalizedRange(final List<String> symbols) {
        Map<String, BigDecimal> normalizedValuesMap = cryptoPriceCharacteristicsStream(symbols)
                .flatMap(entry -> entry.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, entry -> normalize(entry.getValue())));

        return normalizedValuesMap.entrySet().stream()
                .sorted((entryA, entryB) -> entryB.getValue().compareTo(entryA.getValue()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public CryptoWithNormalizedRange getCryptoSymbolWithHighestNormalizedRange(final LocalDate date, final List<String> symbols) {
        Map<String, List<CryptoPriceData>> symbolPriceDataMap = readerService.readAsStreamOfCryptoPriceData(symbols)
                .filter(cryptoPriceData -> cryptoPriceData.getTimestamp().toLocalDate().equals(date))
                .collect(groupingBy(CryptoPriceData::getSymbol));

        Comparator<Map.Entry<String, List<CryptoPriceData>>> comparatorForAscSort =
                Comparator.comparing((Map.Entry<String, List<CryptoPriceData>> pricesEntry) -> normalize(cryptoPriceCharacteristics(pricesEntry.getValue())));

        return symbolPriceDataMap.entrySet().stream()
                .max(comparatorForAscSort)
                .map(priceDataEntry -> new CryptoWithNormalizedRange(priceDataEntry.getKey(), date,
                        priceDataEntry.getValue().stream().map(CryptoPriceData::getPrice).collect(Collectors.toList())))
                .orElse(new CryptoWithNormalizedRange());
    }

    /**
     * Reads csv and converts data to Stream of map grouped by crypto symbol as keys and List of
     *  @see com.xm.crypto.investment.domain.CryptoPriceData objects as values
     *
     * @param symbols list of crypto currency symbols ( e.g DOGE, BTC, ETH etc )
     * @return stream of maps grouped by symbol key and characteristics of each
     */
    private Stream<Map<String, CryptoPriceCharacteristics>> cryptoPriceCharacteristicsStream(final List<String> symbols) {
        return readerService.readAsStreamOfCryptoPriceDataMap(symbols).map(cryptoPriceCharacteristicsMapper());
    }

    /**
     * Calculates (max-min)/min
     *
     * @param cryptoPriceCharacteristics Java Object of crypto characteristics which has oldest/newest/min/max values
     * @return value of normalization
     */
    private BigDecimal normalize(CryptoPriceCharacteristics cryptoPriceCharacteristics) {
        return (cryptoPriceCharacteristics.getMax().subtract(cryptoPriceCharacteristics.getMin()))
                .divide(cryptoPriceCharacteristics.getMin(), RoundingMode.CEILING);
    }

    /**
     * Function responsible for calculation CryptoPriceCharacteristics
     * from CryptoPriceData
     *
     * @return Java Function
     */
    private Function<Map<String, List<CryptoPriceData>>, Map<String, CryptoPriceCharacteristics>> cryptoPriceCharacteristicsMapper() {
        return priceDataMap -> priceDataMap.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> cryptoPriceCharacteristics(entry.getValue())));
    }

    /**
     * Utility method to calculate crypto price data into
     * @see com.xm.crypto.investment.domain.CryptoPriceCharacteristics
     * which has oldest/newest/min/max values
     *
     * @param priceData List of Java Objects
     * @see com.xm.crypto.investment.domain.CryptoPriceData (represents row in csv file)
     * @return Java Object which has oldest/newest/min/max values
     */
    private CryptoPriceCharacteristics cryptoPriceCharacteristics(final List<CryptoPriceData> priceData) {
        List<BigDecimal> pricesSorted = priceData.stream().map(CryptoPriceData::getPrice).sorted().collect(Collectors.toList());
        BigDecimal min = pricesSorted.get(0);
        BigDecimal max = pricesSorted.get(pricesSorted.size() - 1);
        List<BigDecimal> pricesSortedByTimestamp = priceData
                .stream()
                .sorted((price1, price2) -> price1.getTimestamp().compareTo(price2.getTimestamp()))
                .map(CryptoPriceData::getPrice).collect(Collectors.toList());
        BigDecimal oldestPrice = pricesSortedByTimestamp.get(0);
        BigDecimal newestPrice = pricesSortedByTimestamp.get(pricesSortedByTimestamp.size() - 1);
        return new CryptoPriceCharacteristics(max, min, oldestPrice, newestPrice);
    }
}
