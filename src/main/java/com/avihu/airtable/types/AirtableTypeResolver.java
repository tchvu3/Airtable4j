package com.avihu.airtable.types;

import com.avihu.airtable.utils.AirtableGenericUtils;
import com.avihu.airtable.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.time.Instant;
import java.util.List;

public abstract class AirtableTypeResolver {

    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final static String URL_REGEX = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

    public static AirtableType inferType(Object toInfer) {
        if (AirtableTypeResolver.isAirtableTextualTimestamp(toInfer))
            return AirtableType.TEXTUAL_TIMESTAMP;
        else if (AirtableTypeResolver.isAirtableEmail(toInfer))
            return AirtableType.EMAIL;
        else if (AirtableTypeResolver.isAirtableUrl(toInfer))
            return AirtableType.URL;
        else if (AirtableTypingUtils.isString(toInfer))
            return AirtableType.TEXT;
        else if (AirtableTypeResolver.isAirtableWholeDecimal(toInfer))
            return AirtableType.WHOLE_DECIMAL;
        else if (AirtableTypingUtils.isFloatOrDouble(toInfer))
            return AirtableType.FRACTIONAL_DECIMAL;
        else if (AirtableTypeResolver.isAirtableBoolean(toInfer))
            return AirtableType.BOOLEAN;
        else if (AirtableTypeResolver.isAirtableAttachment(toInfer))
            return AirtableType.ATTACHMENT;
        else if (AirtableTypeResolver.isAirtableBarcode(toInfer))
            return AirtableType.BARCODE;
        else if (AirtableTypeResolver.isAirtableButton(toInfer))
            return AirtableType.BUTTON;
        else if (AirtableTypeResolver.isAirtableCollaborator(toInfer))
            return AirtableType.COLLABORATOR;
        else if (AirtableTypingUtils.isList(toInfer))
            return AirtableTypeResolver.calculateListType((List<?>) toInfer);
        else
            return AirtableType.UNKNOWN;
    }

    private static boolean isAirtableTextualTimestamp(Object toInfer) {
        try {
            return AirtableTypingUtils.isString(toInfer) && Instant.parse(toInfer.toString()) != null;
        } catch (Exception ignore) {
            return false;
        }
    }

    private static boolean isAirtableEmail(Object toInfer) {
        return AirtableTypingUtils.isString(toInfer) && AirtableGenericUtils.validateRegex(toInfer.toString(), AirtableTypeResolver.EMAIL_REGEX);
    }

    private static boolean isAirtableUrl(Object toInfer) {
        return AirtableTypingUtils.isString(toInfer) && AirtableGenericUtils.validateRegex(toInfer.toString(), AirtableTypeResolver.URL_REGEX);
    }

    private static boolean isAirtableWholeDecimal(Object toInfer) {
        boolean isIntegerOrLong = AirtableTypingUtils.isIntOrLong(toInfer);
        if (isIntegerOrLong) {
            return true;
        }
        if (AirtableTypingUtils.isFloatOrDouble(toInfer)) {
            double converted = (double) toInfer;
            return converted == Math.rint(converted);
        }
        return false;
    }

    private static boolean isAirtableBoolean(Object toInfer) {
        return AirtableTypingUtils.isBoolean(toInfer);
    }

    private static boolean isAirtableAttachment(Object toInfer) {
        if (!AirtableTypingUtils.isList(toInfer)) {
            return false;
        }
        List<?> list = (List<?>) toInfer;
        if (!AirtableTypingUtils.isLinkedTreeMap(list.get(0))) {
            return false;
        }
        LinkedTreeMap<?, ?> treeMap = (LinkedTreeMap<?, ?>) list.get(0);
        return treeMap.containsKey("id") && treeMap.containsKey("url") && treeMap.containsKey("filename") && treeMap.containsKey("size") && treeMap.containsKey("type") && treeMap.containsKey("thumbnails");
    }

    private static boolean isAirtableBarcode(Object toInfer) {
        if (!AirtableTypingUtils.isLinkedTreeMap(toInfer)) {
            return false;
        }
        LinkedTreeMap<?, ?> treeMap = (LinkedTreeMap<?, ?>) toInfer;
        return treeMap.containsKey("text") && treeMap.size() <= 2;
    }

    private static boolean isAirtableButton(Object toInfer) {
        if (!AirtableTypingUtils.isLinkedTreeMap(toInfer)) {
            return false;
        }
        LinkedTreeMap<?, ?> treeMap = (LinkedTreeMap<?, ?>) toInfer;
        return treeMap.containsKey("label") && treeMap.size() <= 2;
    }

    private static boolean isAirtableCollaborator(Object toInfer) {
        if (!AirtableTypingUtils.isLinkedTreeMap(toInfer)) {
            return false;
        }
        LinkedTreeMap<?, ?> treeMap = (LinkedTreeMap<?, ?>) toInfer;
        return treeMap.containsKey("id") && treeMap.containsKey("email") && treeMap.containsKey("name") && treeMap.size() <= 3;
    }

    private static AirtableType calculateListType(List<?> toInfer) {
        int textCounter = 0;
        int wholeDecimalCounter = 0;
        int fractionalDecimalCounter = 0;
        int booleanCounter = 0;
        int objectCounter = 0;
        for (Object item : toInfer) {
            if (item == null) {
                objectCounter++;
            } else if (AirtableTypingUtils.isString(item)) {
                textCounter++;
            } else if (AirtableTypeResolver.isAirtableWholeDecimal(item)) {
                wholeDecimalCounter++;
            } else if (AirtableTypingUtils.isFloatOrDouble(item)) {
                fractionalDecimalCounter++;
            } else if (AirtableTypeResolver.isAirtableBoolean(item)) {
                booleanCounter++;
            } else {
                objectCounter++;
            }
        }

        if (textCounter == toInfer.size()) {
            return AirtableType.TEXT_LIST;
        } else if (wholeDecimalCounter == toInfer.size()) {
            return AirtableType.WHOLE_DECIMAL_LIST;
        } else if (fractionalDecimalCounter == toInfer.size()) {
            return AirtableType.FRACTIONAL_DECIMAL_LIST;
        } else if (booleanCounter == toInfer.size()) {
            return AirtableType.BOOLEAN_LIST;
        } else if (objectCounter == toInfer.size()) {
            return AirtableType.OBJECT_LIST;
        } else if (textCounter + wholeDecimalCounter + fractionalDecimalCounter + booleanCounter == toInfer.size()) {
            return AirtableType.PRIMITIVE_LIST;
        } else {
            return AirtableType.OBJECT_PRIMITIVE_LIST;
        }
    }

}
