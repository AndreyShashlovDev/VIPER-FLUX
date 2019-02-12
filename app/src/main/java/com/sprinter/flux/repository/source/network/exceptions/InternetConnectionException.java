package com.sprinter.flux.repository.source.network.exceptions;

public class InternetConnectionException extends ApiException {

    private static final int ERROR_CODE = -1;

    public InternetConnectionException() {
        super(ERROR_CODE, "");
    }

}
