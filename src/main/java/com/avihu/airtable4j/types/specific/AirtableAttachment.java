package com.avihu.airtable4j.types.specific;

import com.avihu.airtable4j.utils.AirtableTypingUtils;
import com.google.gson.internal.LinkedTreeMap;

/*
Note that the height and width will only be available if the attachment is of type image
 */
public class AirtableAttachment {

    private final String id;
    private final String url;
    private final String filename;
    private final long size;
    private final String type;
    private final long width;
    private final long height;
    private final AirtableAttachmentThumbnails thumbnails;

    public AirtableAttachment(LinkedTreeMap<String, Object> source) {
        this.id = AirtableTypingUtils.toString(() -> source.get("id"));
        this.url = AirtableTypingUtils.toString(() -> source.get("url"));
        this.filename = AirtableTypingUtils.toString(() -> source.get("filename"));
        this.size = AirtableTypingUtils.toLong(() -> source.get("size"));
        this.type = AirtableTypingUtils.toString(() -> source.get("type"));
        this.width = AirtableTypingUtils.toLong(() -> source.get("width"));
        this.height = AirtableTypingUtils.toLong(() -> source.get("height"));
        this.thumbnails = new AirtableAttachmentThumbnails((LinkedTreeMap<String, Object>) source.get("thumbnails"));
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }

    public AirtableAttachmentThumbnails getThumbnails() {
        return thumbnails;
    }

    public static class AirtableAttachmentThumbnails {
        private final AirtableAttachmentThumbnail small;
        private final AirtableAttachmentThumbnail large;
        private final AirtableAttachmentThumbnail full;

        public AirtableAttachmentThumbnails(LinkedTreeMap<String, Object> source) {
            this.small = new AirtableAttachmentThumbnail((LinkedTreeMap<String, Object>) source.get("small"));
            this.large = new AirtableAttachmentThumbnail((LinkedTreeMap<String, Object>) source.get("large"));
            this.full = new AirtableAttachmentThumbnail((LinkedTreeMap<String, Object>) source.get("full"));
        }

        public AirtableAttachmentThumbnail getSmall() {
            return small;
        }

        public AirtableAttachmentThumbnail getLarge() {
            return large;
        }

        public AirtableAttachmentThumbnail getFull() {
            return full;
        }

    }

    public static class AirtableAttachmentThumbnail {
        private final String url;
        private final long width;
        private final long height;

        public AirtableAttachmentThumbnail(LinkedTreeMap<String, Object> source) {
            this.url = AirtableTypingUtils.toString(() -> source.get("url"));
            this.width = AirtableTypingUtils.toLong(() -> source.get("width"));
            this.height = AirtableTypingUtils.toLong(() -> source.get("height"));
        }

        public String getUrl() {
            return url;
        }

        public long getWidth() {
            return width;
        }

        public long getHeight() {
            return height;
        }

    }

}
