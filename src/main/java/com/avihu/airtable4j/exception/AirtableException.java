package com.avihu.airtable4j.exception;

import com.avihu.airtable4j.types.servererror.AirtableServerError;

public class AirtableException extends Exception {

    private final AirtableServerError airtableServerError;

    public AirtableException(String message) {
        super(message);
        this.airtableServerError = null;
    }

    public AirtableException(Throwable cause) {
        super(cause);
        this.airtableServerError = null;
    }

    public AirtableException(String message, Throwable cause) {
        super(message, cause);
        this.airtableServerError = null;
    }

    public AirtableException(String message, AirtableServerError airtableServerError) {
        super(message);
        this.airtableServerError = airtableServerError;
    }

    public AirtableException(Throwable cause, AirtableServerError airtableServerError) {
        super(cause);
        this.airtableServerError = airtableServerError;
    }

    public AirtableException(String message, Throwable cause, AirtableServerError airtableServerError) {
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
