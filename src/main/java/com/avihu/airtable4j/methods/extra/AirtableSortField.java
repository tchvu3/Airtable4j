package com.avihu.airtable4j.methods.extra;

import java.util.Objects;

public class AirtableSortField {

    private final String field;
    private final String direction;

    private AirtableSortField(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public String getDirection() {
        return direction;
    }

    public static AirtableSortField ascending(String field) {
        return new AirtableSortField(field, "asc");
    }

    public static AirtableSortField descending(String field) {
        return new AirtableSortField(field, "desc");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirtableSortField that = (AirtableSortField) o;
        return field.equals(that.field) && direction.equals(that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, direction);
    }
}
