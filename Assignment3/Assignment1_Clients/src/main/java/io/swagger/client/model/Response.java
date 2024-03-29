package io.swagger.client.model;

import java.util.List;
import java.util.Map;

public class Response<T> {
    private T result;
    private int statusCode;
    private Map<String, List<String>> responseHeaders;

    public Response(T result, int statusCode, Map<String, List<String>> responseHeaders) {
        this.result = result;
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
    }

    public Response(int statusCode, Map<String, List<String>> responseHeaders) {
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", statusCode=" + statusCode +
                ", responseHeaders=" + responseHeaders +
                '}';
    }
}
