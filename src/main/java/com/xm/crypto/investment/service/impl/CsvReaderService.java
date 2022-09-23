package com.xm.crypto.investment.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.crypto.investment.InvestmentApplication;
import com.xm.crypto.investment.domain.CryptoPriceData;
import com.xm.crypto.investment.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Implementation of
 * @see com.xm.crypto.investment.service.ReaderService containing
 * methods for reading csv files to Java Objects
 * @see com.xm.crypto.investment.service.ReaderService for further doc
 */
@Service
@RequiredArgsConstructor
public class CsvReaderService implements ReaderService {

    @Override
    public Stream<Map<String, List<CryptoPriceData>>> readAsStreamOfCryptoPriceDataMap(final List<String> symbols) {
        var listOfFiles = getListOfFiles(symbols, "price");
        return listOfFiles.stream()
                .map(this::csvToBean)
                .map(CsvToBean::stream)
                .map(priceData -> priceData.collect(groupingBy(CryptoPriceData::getSymbol)));
    }

    @Override
    public Stream<CryptoPriceData> readAsStreamOfCryptoPriceData(final List<String> symbols) {
        var listOfFiles = getListOfFiles(symbols,"price");
        return listOfFiles.stream()
                .map(this::csvToBean)
                .flatMap(CsvToBean::stream);
    }

    /**
     * Utility method to read csv file into wrapper object with Java Object
     * containig data read from csv files
     *
     * @param dir directory where csv files located
     * @return    wrapper object with Java Object containig data read from csv files
     */
    @SneakyThrows
    private CsvToBean<CryptoPriceData> csvToBean(final File dir) {
        return new CsvToBeanBuilder<CryptoPriceData>(new BufferedReader(new FileReader(dir)))
                .withType(CryptoPriceData.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
    }

    /**
     * Utility method to check directory for csv files with name %symbol%_values.csv
     * and return list of that files
     *
     * @param symbols list of crypto currency symbols we want to read from files ( e.g DOGE, BTC, ETH etc )
     * @param dir     directory where csv files located
     * @return        list of files
     */
    @SneakyThrows
    private List<File> getListOfFiles(final List<String> symbols, final String dir) {
        URI uri = Objects.requireNonNull(InvestmentApplication.class.getClassLoader().getResource(dir)).toURI();
        List<File> files = Files.list(Paths.get(uri)).map(Path::toFile).collect(Collectors.toList());
        files.removeIf(file -> Objects.nonNull(symbols) && !symbols.contains(file.getName().substring(0, file.getName().indexOf("_"))));
        return files;
    }
}

