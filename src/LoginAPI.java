import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAPI {
    public LoginAPI(LoginParams loginParams, String testDescription, String codeExpectation, String messageExpectation) throws MalformedURLException, ProtocolException,
            IOException {

        URL url = new URL(APIPath.LOGIN);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(loginParams.key1, loginParams.value1);
        params.put(loginParams.key2, loginParams.value2);
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
            Response rp = g.fromJson(content.toString(), Response.class);

            System.out.println(testDescription);

            assert rp.code.equals(codeExpectation);
            assert rp.message.equals(messageExpectation);
            System.out.println("Pass");
            System.out.println();
            System.out.println();
        } finally {
            connection.disconnect();
        }
    }

    public static class TestCase {
        LoginParams params;
        String testDescription;
        String codeExpectation;
        String messageExpectation;

        public TestCase(String codeExpectation, String messageExpectation, String testDescription, LoginParams params) {
            this.codeExpectation = codeExpectation;
            this.messageExpectation = messageExpectation;
            this.testDescription = testDescription;
            this.params = params;

        }
    }

    public static class LoginParams {
        String key1;
        String value1;
        String key2;
        String value2;

        public LoginParams(String key1, String value1, String key2, String value2) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;

        }
    }

    public static void main() throws MalformedURLException, ProtocolException,
            IOException{
        List<TestCase> listTestCase = new ArrayList<TestCase>();

        LoginParams params1 = new LoginParams("email", "thanh12345@gmail.com", "password", "123456");
        TestCase testCase1 = new TestCase("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        LoginParams params2 = new LoginParams("email", "12345@gmail.com", "password", "123456");
        TestCase testCase2 = new TestCase("1002", "メールとパスワードは違いました", "Unit test 2: Should throw error 1002 with incorrect params", params2);
        listTestCase.add(testCase2);

        for (TestCase testCase : listTestCase){
            new LoginAPI(testCase.params, testCase.testDescription, testCase.codeExpectation, testCase.messageExpectation );
        };
    }




}

