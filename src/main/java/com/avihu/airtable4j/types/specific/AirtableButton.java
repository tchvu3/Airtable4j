package com.avihu.airtable4j.types.specific;

import com.avihu.airtable4j.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

public class AirtableButton {

    private final String label;
    private final String url;

    public AirtableButton(LinkedTreeMap<String, String> source) {
        this.label = AirtableTypingUtils.toString(() -> source.get("label"));
        this.url = AirtableTypingUtils.toString(() -> source.get("url"));
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

}
