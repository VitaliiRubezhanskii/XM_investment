package com.xm.crypto.investment.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Java Object wich represents crypto containing
 * prices with highest normalized range
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CryptoWithNormalizedRange {

    private String symbol;
    private LocalDate date;
    private List<BigDecimal> prices;
}
