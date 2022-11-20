package com.avihu.airtable.types.servererror;

public class AirtableComplexServerError implements AirtableServerError {

    private AirtableComplexServerErrorType error;

    public AirtableComplexServerErrorType getError() {
        return error;
    }

    public void setError(AirtableComplexServerErrorType error) {
        this.error = error;
    }

    @Override
    public String getServerErrorType() {
        if (this.error == null) {
            return null;
        }
        return this.error.getType();
    }

    @Override
    public String getServerErrorMessage() {
        if (this.error == null) {
            return null;
        }
        return this.error.getMessage();
    }

    public static class AirtableComplexServerErrorType {

        private String type;
        private String message;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
