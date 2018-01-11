package net.lelyak.edu.utils.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeConverter {
	private static final String DATE_FORMAT = "MM/dd/yyyy h:mm a";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public static LocalDateTime convert(String source) {
		if (source == null || source.isEmpty()) {
			return null;
		}

		return LocalDateTime.parse(source.trim(), formatter);
	}

}