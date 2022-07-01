package com.github.annushko.core.http.apache;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

public class HttpExecutor {

    public static final List<NameValuePair> EMPTY_PARAMETERS = Collections.emptyList();
    public static final List<Header> EMPTY_HEADERS = Collections.emptyList();

    private final CloseableHttpClient internal;

    public HttpExecutor() {
        this.internal = HttpClientBuilder.create()
                                         .useSystemProperties()
                                         .evictExpiredConnections()
                                         .setDefaultRequestConfig(RequestConfig.custom()
                                                                               .setConnectTimeout(3000)
                                                                               .setConnectionRequestTimeout(3000)
                                                                               .setSocketTimeout(3000)
                                                                               .build())
                                         .build();
    }

    public CloseableHttpResponse get(String url) {
        return this.get(url, EMPTY_PARAMETERS, EMPTY_HEADERS);
    }

    public CloseableHttpResponse get(String url, List<NameValuePair> parameters) {
        return this.get(url, parameters, EMPTY_HEADERS);
    }

    public CloseableHttpResponse get(String url, List<NameValuePair> parameters, List<Header> headers) {
        return execute(request(HttpGet.METHOD_NAME, url, parameters, headers, null));
    }

    public CloseableHttpResponse post(String url, JsonBody body) {
        return this.post(url, EMPTY_PARAMETERS, EMPTY_HEADERS, body);
    }

    public CloseableHttpResponse post(String url, List<NameValuePair> parameters, List<Header> headers, JsonBody body) {
        return execute(request(HttpPost.METHOD_NAME, url, parameters, headers, body));
    }

    public CloseableHttpResponse put(String url, JsonBody body) {
        return this.put(url, EMPTY_PARAMETERS, EMPTY_HEADERS, body);
    }

    public CloseableHttpResponse put(String url, List<NameValuePair> parameters, List<Header> headers, JsonBody body) {
        return execute(request(HttpPut.METHOD_NAME, url, parameters, headers, body));
    }

    public CloseableHttpResponse patch(String url, JsonBody body) {
        return this.patch(url, EMPTY_PARAMETERS, EMPTY_HEADERS, body);
    }

    public CloseableHttpResponse patch(String url, List<NameValuePair> parameters, List<Header> headers, JsonBody body) {
        return execute(request(HttpPatch.METHOD_NAME, url, parameters, headers, body));
    }

    public CloseableHttpResponse delete(String url) {
        return this.delete(url, EMPTY_PARAMETERS, EMPTY_HEADERS, null);
    }

    public CloseableHttpResponse delete(String url, List<NameValuePair> parameters, List<Header> headers, JsonBody body) {
        return execute(request(HttpDelete.METHOD_NAME, url, parameters, headers, body));
    }

    private CloseableHttpResponse execute(HttpUriRequest request) {
        try {
            return internal.execute(request);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpUriRequest request(String method, String url, List<NameValuePair> parameters, List<Header> headers, JsonBody body) {
        var req = RequestBuilder.create(method)
                                .setUri(url);
        parameters.forEach(req::addParameter);
        headers.forEach(req::addHeader);
        req.setEntity(prepareEntity(body));
        return req.build();
    }

    private HttpEntity prepareEntity(JsonBody body) {
        if (body != null) {
            return new ByteArrayEntity(body.asBytes(), ContentType.APPLICATION_JSON);
        } else {
            return null;
        }
    }

}
