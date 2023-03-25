package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        String timePattern = "HH:mm";
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(timePattern));
    }
}
