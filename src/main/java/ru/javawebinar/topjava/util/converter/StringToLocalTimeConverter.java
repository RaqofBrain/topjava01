package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    private final String TIME_PATTERN = "HH:mm";

    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(TIME_PATTERN));
    }
}
