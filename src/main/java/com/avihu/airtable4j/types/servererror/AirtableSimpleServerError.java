package com.avihu.airtable4j.types.servererror;

public class AirtableSimpleServerError implements AirtableServerError {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String getServerErrorType() {
        return this.error;
    }

    @Override
    public String getServerErrorMessage() {
        return null;
    }
}
