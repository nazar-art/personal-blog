package net.lelyak.edu.utils.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class LocalDateConverter implements Converter<String, LocalDate> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private final DateTimeFormatter formatter;


    public LocalDateConverter(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat.trim());
    }

    @Override
    public LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        return LocalDate.parse(source, formatter);
    }
}