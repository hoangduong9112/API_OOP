package test_api;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLoginAPI extends TestBase  {
    private TestLoginAPI() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    private static void test(LoginParams loginParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.getLoginURL());
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
            System.out.println(testDescription);
            try {
                assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
                assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
                if (codeExpectation.equals("1000")) {
                    assert rp.getData().access_token != null;
                    assert rp.getData().user != null;
                    assert rp.getData().exp != null;
                    assert rp.getData().expires_in != null;
                    assert rp.getData().token_type != null;
                } else assert rp.getData() == null;
                System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
                System.out.println();
            } catch(AssertionError e) {
                System.out.println(getAnsiRed() + "Received");
                System.out.println("      code: " + rp.getCode());
                System.out.println("      message: " + rp.getMessage());
                System.out.println("      data: " + rp.getData());
                System.out.println(getAnsiGreen() + "Expect");
                System.out.println("      code: " + codeExpectation);
                if(messageExpectation.length() > 0) System.out.println("      message: " + messageExpectation);
                System.out.println("      data: " + rp.getData());
                System.out.println(getAnsiReset());
            }
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<LoginParams>> listTestCase = new ArrayList<>();

        final String emailKey = "email";
        final String passwordKey = "password";
        final String correctEmail = "duonghoang@gmail.com";
        final String correctPassword = "123456";

        LoginParams params1 = new LoginParams(emailKey, correctEmail, passwordKey, correctPassword);
        TestCase<LoginParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        LoginParams params2 = new LoginParams(emailKey, "12345@gmail.com", passwordKey, correctPassword);
        TestCase<LoginParams> testCase2 = new TestCase<>("1002", "", "Unit test 2: Should throw error 1002 with incorrect email", params2);
        listTestCase.add(testCase2);

        LoginParams params3 = new LoginParams(emailKey, correctEmail, passwordKey, "1234567");
        TestCase<LoginParams> testCase3 = new TestCase<>("1002", "", "Unit test 3: Should throw error 1002 with incorrect password", params3);
        listTestCase.add(testCase3);

        LoginParams params4 = new LoginParams(emailKey, "1234", passwordKey, correctPassword);
        TestCase<LoginParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with incorrect email", params4);
        listTestCase.add(testCase4);

        LoginParams params5 = new LoginParams(emailKey, "", passwordKey, correctPassword);
        TestCase<LoginParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
        listTestCase.add(testCase5);

        LoginParams params6 = new LoginParams(emailKey, correctEmail, passwordKey, "");
        TestCase<LoginParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty password", params6);
        listTestCase.add(testCase6);

        System.out.println(getAnsiBlue() + "Testing Login API" + getAnsiReset());

        for (TestCase<LoginParams> testCase : listTestCase) {
            TestLoginAPI.test(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class LoginParams {
        String key1;
        String value1;
        String key2;
        String value2;

        private LoginParams(String key1, String value1, String key2, String value2) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
        }
    }
}
