package ru.clevertec.exception.internalservererror;

public class JDBCConnectionException extends InternalServerErrorException {

    private static final String MESSAGE = "Sorry! We got Server database connection problems";

    public JDBCConnectionException() {
        super(MESSAGE);
    }

}
