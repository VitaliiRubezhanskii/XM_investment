package com.xm.crypto.investment.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Java Object containig characteristics of crypto
 * ( e.g DOGE, BTC, ETH etc )
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CryptoPriceCharacteristics {

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal oldest;
    private BigDecimal newest;
}
