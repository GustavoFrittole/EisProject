package com.apiguardian;

import com.apiguardian.bean.Response;
import com.apiguardian.bean.ResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuardianContentApi {

    private final static String TARGET_URL = "http://content.guardianapis.com/search";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
// Only one time
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
//          System.out.println(value+" - "+valueType.getName());
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private final String apiKey;
    private String section;
    private String tag;
    private Date toDate;
    private Date fromDate;
    private String order;

    //aggiunta variabile pagina
    private int pageSize;

    public GuardianContentApi(final String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setFromDate(Date date) {
        this.fromDate = date;
    }

    public void setToDate(Date date) {
        this.toDate = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Response getContent(String query) {
        return getContent(query, 1);
    }

    public Response getContent() {
        return getContent(null, 10);
    }

    public Response getContent(String query, int page) {

        HttpRequest request = Unirest.get(TARGET_URL)
                .queryString("api-key", apiKey)
                .header("accept", "application/json");

        if (pageSize > 1 && pageSize < 201) {
            request.queryString("page-size", pageSize);
        }
        if (page > 0) {
            request.queryString("page", page);
        }

        if (query != null && !query.isEmpty()) {
            request.queryString("q", query);
        }

        if (order != null && !order.isEmpty()) {
            request.queryString("order-by", order);
        }

        if (section != null && !section.isEmpty()) {
            request.queryString("section", section);
        }

        if (tag != null && !tag.isEmpty()) {
            request.queryString("tag", tag);
        }

        if (fromDate != null) {
            request.queryString("from-date", dateFormat.format(fromDate));
        }
        if (toDate != null) {
            request.queryString("to-date", dateFormat.format(toDate));
        }

        request.queryString("show-fields", "all");

        HttpResponse<ResponseWrapper> response = null;
        try {
            response = request.asObject(ResponseWrapper.class);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return response.getBody().getResponse();

    }
}
