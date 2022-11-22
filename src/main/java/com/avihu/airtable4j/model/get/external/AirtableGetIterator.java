package com.avihu.airtable4j.model.get.external;

import com.avihu.airtable4j.Airtable;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.methods.operation.AirtableGetBuilder;
import com.avihu.airtable4j.types.servererror.AirtablePossibleServerErrors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AirtableGetIterator<T> implements Iterable<T>, Iterator<T> {

    private final Airtable.InternalApi apiRef;
    private final AirtableGetBuilder airtableGetBuilder;

    private List<T> currentResponse;
    private String nextOffset;
    private int index;

    public AirtableGetIterator(Airtable.InternalApi apiRef, AirtableGetBuilder airtableGetBuilder) {
        this.apiRef = apiRef;
        this.airtableGetBuilder = airtableGetBuilder;
    }

    @Override
    public boolean hasNext() {
        return this.nextOffset != null || this.currentResponse == null || this.index < this.currentResponse.size() - 1;
    }

    @Override
    public T next() {
        if (this.currentResponse != null && this.index < this.currentResponse.size() - 1) {
            return this.currentResponse.get(++this.index);
        }
        try {
            this.airtableGetBuilder.getPublicApi().setOffset(this.nextOffset);
            AirtableGetResponse executeResult = this.apiRef.execute(this.airtableGetBuilder);
            this.currentResponse = this.airtableGetBuilder.transformToRequiredClass(executeResult);
            this.nextOffset = executeResult.getOffset();
            this.index = 0;
            return this.currentResponse.get(0);
        } catch (AirtableException exception) {
            if (AirtablePossibleServerErrors.ITERATOR_NOT_AVAILABLE.equals(exception.getAirtableServerErrorType())) {
                throw new RuntimeException("The iteration has been timed-out by the server.", exception);
            }
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    public List<T> getAll() {
        List<T> toReturn = new ArrayList<>();
        this.forEach(toReturn::add);
        return toReturn;
    }
}
