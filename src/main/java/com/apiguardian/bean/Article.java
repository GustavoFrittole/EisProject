package com.apiguardian.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Article {
    String id;
    String type;
    String sectionId;
    String sectionName;
    String webPublicationDate;
    String webTitle;
    String webUrl;
    String apiUrl;
    boolean isHosted;
    String pillarId;
    String pillarName;
    String bodyText;

    public Article(
            final String id,
            final String type,
            final String sectionId,
            final String sectionName,
            final String webPublicationDate,
            final String webTitle,
            final String webUrl,
            final String apiUrl,
            final boolean isHosted,
            final String pillarId,
            final String pillarName
    ) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.isHosted = isHosted;
        this.pillarId = pillarId;
        this.pillarName = pillarName;
    }

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(final String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(final String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(final String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(final String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(final String webUrl) {
        this.webUrl = webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public boolean getIsHosted() {
        return isHosted;
    }

    public void setIsHosted(final boolean isHosted) {
        this.isHosted = isHosted;
    }

    public String getPillarId() {
        return pillarId;
    }

    public void setPillarId(final String pillarId) {
        this.pillarId = pillarId;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(final String pillarName) {
        this.pillarName = pillarName;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(final String bodyText) {
        this.bodyText = bodyText;
    }

    @JsonProperty("fields")
    private void unpackNested(Map<String, Object> fields) {
        this.bodyText = (String) fields.get("bodyText");
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", webPublicationDate='" + webPublicationDate + '\'' +
                ", webTitle='" + webTitle + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", isHosted='" + isHosted + '\'' +
                ", pillarId='" + pillarId + '\'' +
                ", pillarName='" + pillarName + '\'' +
                ", bodyText='" + bodyText + '\'' +
                '}';
    }
}
