package com.apiguardian.bean;

public class ResponseWrapper {

    private Response response;

    public ResponseWrapper(final Response response) {
        this.response = response;
    }

    public ResponseWrapper() {
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(final Response response) {
        this.response = response;
    }
}
