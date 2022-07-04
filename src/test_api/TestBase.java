package test_api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.APIPath;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestBase {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    protected TestBase() {
    }
    public static String getAnsiReset() {
        return ANSI_RESET;
    }

    public static String getAnsiGreen() {
        return ANSI_GREEN;
    }

    public static String getAnsiBlue() {
        return ANSI_BLUE;
    }

    public static String getAnsiRed() {
        return ANSI_RED;
    }
    protected static String callLogin() throws
            IOException {
        URL url = new URL(APIPath.getLoginURL());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("email", "duonghoang@gmail.com");
        params.put("password", "123456");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
        }

        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);
            writer.flush();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            Gson g = new Gson();
            Type response = new TypeToken<Response<LoginDataType>>() {}.getType();
            Response<LoginDataType> rp = g.fromJson(content.toString(), response);
            return rp.getData().access_token;
        } finally {
            connection.disconnect();
        }
    }

    protected static class LoginDataType {
        protected String access_token;
        protected Object user;
        protected String token_type;
        protected String expires_in;
        protected String exp;
    }

    protected record TestCase<T>(String codeExpectation, String messageExpectation, String testDescription, T params) { }
    protected static class Response<T> {
        protected String code;
        protected String message;
        protected T data;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
        public T getData() {
            return data;
        }

    }
}