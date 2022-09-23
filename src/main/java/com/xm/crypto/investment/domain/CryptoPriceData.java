package com.xm.crypto.investment.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.xm.crypto.investment.converters.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Java Object as Entity serializable from csv file
 * Each field is mapped to csv file column
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CryptoPriceData {

    @CsvCustomBindByName(column = "timestamp", converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

    @CsvBindByName(column = "symbol")
    private String symbol;

    @CsvBindByName(column = "price")
    private BigDecimal price;

}
