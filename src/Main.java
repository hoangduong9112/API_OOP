import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws MalformedURLException, ProtocolException,
            IOException {
        URL url = new URL(API.LOGIN);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("email", "thanh12345@gmail.com");
        params.put("password", "123456");
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);
            writer.flush();
            writer.close();

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
            System.out.println(content.toString());

            Gson g = new Gson();
            ResponseSignUp rp = g.fromJson(content.toString(), ResponseSignUp.class);

            System.out.println(rp.code);
            System.out.println(rp.message);

            System.out.println("Unit test 1: Should be successful with correct params");

            assert rp.code.equals("1000");
            assert rp.message.equals("OK");
            System.out.println("Pass");
        } finally {
            connection.disconnect();
        }
    }
}

