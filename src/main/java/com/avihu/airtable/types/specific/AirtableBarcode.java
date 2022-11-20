package com.avihu.airtable.types.specific;

import com.avihu.airtable.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

public class AirtableBarcode {

    private final String text;
    private final String type;

    public AirtableBarcode(LinkedTreeMap<String, String> source) {
        this.text = AirtableTypingUtils.toString(() -> source.get("text"));
        this.type = AirtableTypingUtils.toString(() -> source.get("type"));
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

}
