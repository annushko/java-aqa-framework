package com.github.annushko.core.http.apache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public final class UrlParams {

    private UrlParams() {
    }

    public static List<NameValuePair> from(String... keyValues) {
        if ((keyValues.length % 2) != 0) {
            throw new IllegalArgumentException("Number of passed parameters should be even! Was " + keyValues.length);
        }
        var parameters = new ArrayList<NameValuePair>();
        for (int i = 0, keyValuesLength = keyValues.length; i < keyValuesLength; i++) {
            parameters.add(new BasicNameValuePair(keyValues[i], keyValues[i + 1]));
            i++;
        }
        return parameters;
    }

}
