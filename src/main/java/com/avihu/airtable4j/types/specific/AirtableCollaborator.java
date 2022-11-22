package com.avihu.airtable4j.types.specific;

import com.avihu.airtable4j.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

public class AirtableCollaborator {

    private final String id;
    private final String email;
    private final String name;

    public AirtableCollaborator(LinkedTreeMap<String, String> source) {
        this.id = AirtableTypingUtils.toString(() -> source.get("id"));
        this.email = AirtableTypingUtils.toString(() -> source.get("email"));
        this.name = AirtableTypingUtils.toString(() -> source.get("name"));
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
