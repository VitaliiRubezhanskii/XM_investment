package com.xm.crypto.investment.converters;

import com.opencsv.bean.AbstractBeanField;
import com.xm.crypto.investment.domain.CryptoPriceData;

import java.time.Instant;
import java.time.ZoneId;

/**
 * DateTimeConverter to convert timestamp from 1 column in csv file to
 * @see java.time.LocalDateTime object
 *
 */
public class LocalDateTimeConverter extends AbstractBeanField<CryptoPriceData, Integer> {
    @Override
    protected Object convert(String value)  {
        return Instant.ofEpochMilli(Long.valueOf(value)).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
