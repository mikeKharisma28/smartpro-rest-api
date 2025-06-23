package com.juaracoding.smartpro_rest_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * Author: Michael, 2025-05-17
 * Last updated date: 2025-06-17
 */

public class MenuLoginDTO {
    private String name;

    private String path;

    private String featherIconTag;

    @JsonIgnore
    private String parentMenuName;

    @JsonIgnore
    private String parentFeatherIconTags;

    private Boolean hasChild;

    private Boolean isParent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFeatherIconTag() {
        return featherIconTag;
    }

    public void setFeatherIconTag(String featherIconTag) {
        this.featherIconTag = featherIconTag;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public String getParentFeatherIconTags() {
        return parentFeatherIconTags;
    }

    public void setParentFeatherIconTags(String parentFeatherIconTags) {
        this.parentFeatherIconTags = parentFeatherIconTags;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }
}
