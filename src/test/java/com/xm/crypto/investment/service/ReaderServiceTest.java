package com.xm.crypto.investment.service;

import com.xm.crypto.investment.domain.CryptoPriceData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.xm.crypto.investment.utils.TestUtils.expectedCsvContent;
import static com.xm.crypto.investment.utils.TestUtils.expectedCsvContentAsStreamMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReaderServiceTest {

    private final ReaderService readerService;

    @Autowired
    public ReaderServiceTest(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Test
    public void calculateCharacteristicsOfPriceTest() {
        Stream<CryptoPriceData> actualCsvContent = readerService.readAsStreamOfCryptoPriceData(List.of("BTC", "DOGE"));
        assertEquals(expectedCsvContent(), actualCsvContent.collect(Collectors.toList()));
    }

    @Test
    public void readAsStreamOfCryptoPriceDataMapTest() {
        Stream<Map<String, List<CryptoPriceData>>> actualCsvContentAsStreamMap = readerService.readAsStreamOfCryptoPriceDataMap(List.of("BTC", "DOGE"));
        assertEquals(expectedCsvContentAsStreamMap(), actualCsvContentAsStreamMap.collect(Collectors.toList()));
    }
}
