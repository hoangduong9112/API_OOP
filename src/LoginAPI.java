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
        ColorTerminal colorTerminal = new ColorTerminal();
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
            if (codeExpectation.length() > 0) {
                assert rp.code.equals(codeExpectation);
            }
           if (messageExpectation.length() > 0) {
               assert rp.message.equals(messageExpectation);
           }
            System.out.println(colorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
            System.out.println();
            System.out.println();
        } finally {
            connection.disconnect();
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
        TestCase testCase1 = new TestCase<LoginParams>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        LoginParams params2 = new LoginParams("email", "12345@gmail.com", "password", "123456");
        TestCase testCase2 = new TestCase<LoginParams>("1002", "", "Unit test 2: Should throw error 1002 with incorrect params", params2);
        listTestCase.add(testCase2);

        LoginParams params3 = new LoginParams("email", "", "password", "123456");
        TestCase testCase3 = new TestCase<LoginParams>("1001", "", "Unit test 3: Should throw error 1001 with empty params", params3);
        listTestCase.add(testCase3);

        for (TestCase testCase : listTestCase){
            new LoginAPI((LoginParams) testCase.params, testCase.testDescription, testCase.codeExpectation, testCase.messageExpectation );
        };
    }
}

