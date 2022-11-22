package com.avihu.airtable4j.methods.base.urlbuild;

import com.avihu.airtable4j.utils.AirtableGenericUtils;
import com.avihu.airtable4j.utils.AirtableTypingUtils;
import com.google.common.net.UrlEscapers;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

public class UrlStringBuilder {

    private final StringBuilder builder;
    private final UrlParts urlParts;
    private final String encodedLeftBrace = this.encodeUrl("[");
    private final String encodedRightBrace = this.encodeUrl("]");

    public UrlStringBuilder(String initial, UrlParts urlParts) {
        this.builder = new StringBuilder(initial);
        this.urlParts = urlParts;
    }

    public void appendPathParam(CustomField pathParam) {
        this.builder.append("/").append(this.encodeUrl(pathParam.getValueAsString()));
    }

    public void appendQueryParamsSeparatorIfNeeded() {
        if (this.urlParts.havingQueryParams()) {
            this.builder.append("?");
        }
    }

    public void appendQueryParam(CustomField queryParam) {
        this.builder.append(this.encodeUrl(queryParam.getFieldName())).append("=").append(queryParam.getValueAsString()).append("&");
    }

    public void appendQueryListParam(CustomField queryListParam) {
        Collection<?> paramsList = (Collection<?>) queryListParam.getValue();
        if (AirtableTypingUtils.isPrimitiveCollection(paramsList)) {
            String value = queryListParam.getFieldName().endsWith("[]") ? queryListParam.getFieldName() : queryListParam.getFieldName() + "[]";
            paramsList.forEach(item -> builder.append(this.encodeUrl(value)).append("=").append(String.valueOf(item).trim()).append("&"));
        } else {
            Iterator<?> iterator = paramsList.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                String mainFieldName = this.encodeUrl(queryListParam.getFieldName());
                Object obj = iterator.next();
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    builder.append(mainFieldName)
                            .append(this.encodedLeftBrace).append(i).append(this.encodedRightBrace)
                            .append(this.encodedLeftBrace).append(field.getName()).append(this.encodedRightBrace)
                            .append("=")
                            .append(AirtableGenericUtils.getFieldValue(field, obj))
                            .append("&");
                }
            }
        }
    }

    public String toUrlString() {
        while (this.builder.charAt(this.builder.length() - 1) == '?' || this.builder.charAt(this.builder.length() - 1) == '&') {
            this.builder.deleteCharAt(this.builder.length() - 1);
        }
        return this.builder.toString();
    }

    private String encodeUrl(String content) {
        return UrlEscapers.urlFragmentEscaper().escape(content);
    }

}
