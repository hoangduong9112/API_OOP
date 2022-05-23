import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpAPI {

    private SignUpAPI(SignUpParams SignUpParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.SIGNUP);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(SignUpParams.key1, SignUpParams.value1);
        params.put(SignUpParams.key2, SignUpParams.value2);
        params.put(SignUpParams.key3, SignUpParams.value3);
        params.put(SignUpParams.key4, SignUpParams.value4);
        params.put(SignUpParams.key5, SignUpParams.value5);
        params.put(SignUpParams.key6, SignUpParams.value6);
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
            Response rp = g.fromJson(content.toString(), Response.class);

            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);
            System.out.println(ColorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
            System.out.println();
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<SignUpParams>> listTestCase = new ArrayList<>();

        SignUpParams params1 = new SignUpParams("email", "duonghoang@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);


        System.out.println(ColorTerminal.ANSI_BLUE + "Testing SignUp API" + ColorTerminal.ANSI_RESET);

        for (TestCase<SignUpParams> testCase : listTestCase) {
            new SignUpAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class SignUpParams {
        String key1;
        String value1;
        String key2;
        String value2;
        String key3;
        String value3;
        String key4;
        String value4;
        String key5;
        String value5;
        String key6;
        String value6;




        private SignUpParams(String key1, String value1, String key2, String value2, String key3, String value3,String key4, String value4,String key5, String value5,String key6, String value6) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
            this.key4 = key4;
            this.value4 = value4;
            this.key5 = key5;
            this.value5 = value5;
            this.key6 = key6;
            this.value6 = value6;
        }
    }

}

