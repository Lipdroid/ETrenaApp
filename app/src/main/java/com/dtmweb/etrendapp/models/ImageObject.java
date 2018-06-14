package com.dtmweb.etrendapp.models;

/**
 * Created by lipuhossain on 6/12/18.
 */

public class ImageObject {
    private String id = null;
    private String url = null;
    private Boolean selected = false;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
