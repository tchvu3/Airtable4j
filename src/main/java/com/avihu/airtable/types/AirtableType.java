package com.avihu.airtable.types;

public enum AirtableType {

    TEXT,
    EMAIL,
    URL,
    TEXTUAL_TIMESTAMP,
    WHOLE_DECIMAL,
    FRACTIONAL_DECIMAL,
    TEXT_LIST,
    WHOLE_DECIMAL_LIST,
    FRACTIONAL_DECIMAL_LIST,
    BOOLEAN_LIST,
    OBJECT_LIST,
    OBJECT_PRIMITIVE_LIST,
    PRIMITIVE_LIST,
    BOOLEAN,
    ATTACHMENT,
    BARCODE,
    BUTTON,
    COLLABORATOR,
    UNKNOWN;

    public boolean isTextual() {
        return this.equals(TEXT) ||
                this.equals(EMAIL) ||
                this.equals(URL) ||
                this.equals(TEXTUAL_TIMESTAMP);
    }

    public boolean isPrimitiveList() {
        return this.equals(TEXT_LIST) ||
                this.equals(WHOLE_DECIMAL_LIST) ||
                this.equals(FRACTIONAL_DECIMAL_LIST) ||
                this.equals(BOOLEAN_LIST) ||
                this.equals(PRIMITIVE_LIST);
    }

}
