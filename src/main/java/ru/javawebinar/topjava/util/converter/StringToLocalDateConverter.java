package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String dateString) {
        String datePattern = "yyyy-MM-dd";
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(datePattern));
    }
}
