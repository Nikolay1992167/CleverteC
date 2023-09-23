package ru.clevertec.exception.badrequest;

public class UniquePhoneNumberException extends  BadRequestException {

    public UniquePhoneNumberException(String message) {
        super(message);
    }

}
