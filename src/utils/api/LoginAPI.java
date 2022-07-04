package utils.api;

import com.google.gson.reflect.TypeToken;
import utils.APIPath;
import utils.data_type.DataType;
import utils.Response;
import com.google.gson.Gson;
import utils.data_type.LoginDataType;

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

public final class LoginAPI {
    private LoginAPI() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String call() throws
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
            Response<DataType> rp = g.fromJson(content.toString(), response);
            return rp.getData().accessToken;
        } finally {
            connection.disconnect();
        }
    }
}
