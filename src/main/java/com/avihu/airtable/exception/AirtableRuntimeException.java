package com.avihu.airtable.exception;

import com.avihu.airtable.types.servererror.AirtableServerError;

public class AirtableRuntimeException extends RuntimeException {

    private final AirtableServerError airtableServerError;

    public AirtableRuntimeException(String message) {
        super(message);
        this.airtableServerError = null;
    }

    public AirtableRuntimeException(Throwable cause) {
        super(cause);
        this.airtableServerError = null;
    }

    public AirtableRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.airtableServerError = null;
    }

    public AirtableRuntimeException(String message, AirtableServerError airtableServerError) {
        super(message);
        this.airtableServerError = airtableServerError;
    }

    public AirtableRuntimeException(Throwable cause, AirtableServerError airtableServerError) {
        super(cause);
        this.airtableServerError = airtableServerError;
    }

    public AirtableRuntimeException(String message, Throwable cause, AirtableServerError airtableServerError) {
        super(message, cause);
        this.airtableServerError = airtableServerError;
    }

    public AirtableServerError getAirtableServerError() {
        return this.airtableServerError;
    }

    public String getAirtableServerErrorType() {
        if (this.airtableServerError != null) {
            return this.airtableServerError.getServerErrorType();
        } else {
            return null;
        }
    }

    public String getAirtableServerErrorMessage() {
        if (this.airtableServerError != null) {
            return this.airtableServerError.getServerErrorMessage();
        } else {
            return null;
        }
    }

}
