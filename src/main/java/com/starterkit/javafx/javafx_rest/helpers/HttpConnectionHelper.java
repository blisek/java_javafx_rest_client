package com.starterkit.javafx.javafx_rest.helpers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.StringJoiner;

public class HttpConnectionHelper {
	public static final String AGENT_PROPERTY = "User-Agent";
	public static final String CONTENT_TYPE_PROPERTY = "Content-Type";
	public static String AGENT = "JavaFX/8";
	public static String CONTENT_TYPE = "application/json";

	public static enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	public static HttpURLConnection openConnection(URL url, RequestMethod method, String params) throws IOException {
		HttpURLConnection connection = null;
		switch (method) {
		case GET:
		case DELETE:
			url = new URL(url.toString() + '?' + params);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setRequestProperty(AGENT_PROPERTY, AGENT);
			break;
		case POST:
		case PUT:
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setRequestProperty(AGENT_PROPERTY, AGENT);
			connection.setRequestProperty(CONTENT_TYPE_PROPERTY, CONTENT_TYPE);
			connection.setDoOutput(true);
			try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
				dataOutputStream.writeBytes(params);
				dataOutputStream.flush();
				/*
				 * REV: dataOutputStream nie jest zamykany
				 */
			}
			break;
		}

		return connection;
	}

	public static String joinParamValue(String param, String value) {
		return String.format("%s=%s", param, value);
	}

	public static String joinParams(String... params) {
		return String.join("&", params);
	}

	public static String joinParams(Properties properties) {
		StringJoiner joiner = new StringJoiner("&");
		properties.forEach((key, val) -> joiner.add(joinParamValue((String) key, (String) val)));
		return joiner.toString();
	}
}
