package com.avihu.airtable4j.model.get.external;

import com.avihu.airtable4j.types.AirtableType;
import com.avihu.airtable4j.types.specific.AirtableAttachment;
import com.avihu.airtable4j.types.specific.AirtableBarcode;
import com.avihu.airtable4j.types.specific.AirtableButton;
import com.avihu.airtable4j.types.specific.AirtableCollaborator;
import com.avihu.airtable4j.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AirtableRecordGetFieldResponseFactory {

    public static AirtableRecordGetFieldResponse<?> generate(AirtableType type, Object value) {
        AirtableRecordGetFieldResponse<Object> toReturn = new AirtableRecordGetFieldResponse<>(type);
        if (type.isTextual()) {
            toReturn.setValue(value);
        } else if (type == AirtableType.BOOLEAN) {
            toReturn.setValue(AirtableTypingUtils.toBoolean(value));
        } else if (type == AirtableType.ATTACHMENT) {
            toReturn.setValue(AirtableRecordGetFieldResponseFactory.generateListOfAttachment((List<Object>) value));
        } else if (type == AirtableType.BARCODE) {
            toReturn.setValue(new AirtableBarcode((LinkedTreeMap<String, String>) value));
        } else if (type == AirtableType.BUTTON) {
            toReturn.setValue(new AirtableButton((LinkedTreeMap<String, String>) value));
        } else if (type == AirtableType.COLLABORATOR) {
            toReturn.setValue(new AirtableCollaborator((LinkedTreeMap<String, String>) value));
        } else if (type == AirtableType.WHOLE_DECIMAL) {
            toReturn.setValue(AirtableTypingUtils.toLong(() -> value));
        } else if (type == AirtableType.FRACTIONAL_DECIMAL) {
            toReturn.setValue(AirtableTypingUtils.toDouble(() -> value));
        } else if (type.isPrimitiveList()) {
            toReturn.setValue(AirtableRecordGetFieldResponseFactory.generatePrimitiveList((List<Object>) value));
        } else if (type == AirtableType.OBJECT_PRIMITIVE_LIST) {
            toReturn.setValue(AirtableRecordGetFieldResponseFactory.generateObjectPrimitiveList((List<Object>) value));
        } else if (type == AirtableType.OBJECT_LIST) {
            toReturn.setValue(value);
        }
        return toReturn;
    }

    private static List<AirtableAttachment> generateListOfAttachment(List<Object> attachments) {
        List<AirtableAttachment> toSet = new ArrayList<>();
        attachments.forEach(attachment -> toSet.add(new AirtableAttachment((LinkedTreeMap<String, Object>) attachment)));
        return toSet;
    }

    private static List<Object> generatePrimitiveList(List<Object> values) {
        return values.stream().map(val -> {
            if (AirtableTypingUtils.isBoolean(val)) {
                return AirtableTypingUtils.toBoolean(val);
            } else if (AirtableTypingUtils.isFloatOrDouble(val)) {
                return AirtableTypingUtils.toDouble(val);
            } else if (AirtableTypingUtils.isIntOrLong(val)) {
                return AirtableTypingUtils.toLong(val);
            } else if (AirtableTypingUtils.isString(val)) {
                return AirtableTypingUtils.toString(val);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<Object> generateObjectPrimitiveList(List<Object> values) {
        return values.stream().map(val -> {
            if (AirtableTypingUtils.isBoolean(val)) {
                return AirtableTypingUtils.toBoolean(val);
            } else if (AirtableTypingUtils.isFloatOrDouble(val)) {
                return AirtableTypingUtils.toDouble(val);
            } else if (AirtableTypingUtils.isIntOrLong(val)) {
                return AirtableTypingUtils.toLong(val);
            } else if (AirtableTypingUtils.isString(val)) {
                return AirtableTypingUtils.toString(val);
            } else {
                return val;
            }
        }).collect(Collectors.toList());
    }

}
