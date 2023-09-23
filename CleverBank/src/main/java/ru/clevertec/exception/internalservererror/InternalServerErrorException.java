package ru.clevertec.exception.internalservererror;

public abstract class InternalServerErrorException extends RuntimeException {

    protected InternalServerErrorException(String message) {
        super(message);
    }

}
