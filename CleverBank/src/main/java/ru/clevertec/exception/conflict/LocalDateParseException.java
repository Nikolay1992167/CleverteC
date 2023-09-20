package ru.clevertec.exception.conflict;

import javax.xml.bind.ValidationException;

public class LocalDateParseException extends ValidationException {
    public LocalDateParseException(String message) {
        super(message);
    }
}
