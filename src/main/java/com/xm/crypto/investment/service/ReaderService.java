package com.xm.crypto.investment.service;

import com.xm.crypto.investment.domain.CryptoPriceData;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Service responsible for reading content of file to Java Objects
 */
public interface ReaderService {

    /**
     * Method reads files that contains appropriate symbol in its name
     * and converts data to Stream of map grouped by crypto symbol as keys and List of
     * @see com.xm.crypto.investment.domain.CryptoPriceData objects as values
     *
     * @param symbols list of crypto currency symbols we want to read from files ( e.g DOGE, BTC, ETH etc )
     * @return stream of maps containig crypto symbol as key and list of csv file content serialized to
     * @see CryptoPriceData
     */
    Stream<Map<String, List<CryptoPriceData>>> readAsStreamOfCryptoPriceDataMap(final List<String> symbols);

    /**
     * Method reads files that contains appropriate symbol in its name
     * and converts data to Stream of
     * @see com.xm.crypto.investment.domain.CryptoPriceData objects
     *
     * @param symbols list of crypto currency symbols we want to read from files ( e.g DOGE, BTC, ETH etc )
     * @return stream of csv file content serialized to
     * @see CryptoPriceData
     */
    Stream<CryptoPriceData> readAsStreamOfCryptoPriceData(final List<String> symbols);
}
