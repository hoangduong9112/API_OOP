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
            assert (codeExpectation.length() <= 0 || rp.code.equals(codeExpectation));
            assert (messageExpectation.length() <= 0 || rp.message.equals(messageExpectation));
            System.out.println(ColorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
            System.out.println();
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<SignUpParams>> listTestCase = new ArrayList<>();

        //Need to update new email to run test

        SignUpParams params1 = new SignUpParams("email", "duonghoang120@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        SignUpParams params2 = new SignUpParams("email", "duonghoang123@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "", "phone", "09123");
        TestCase<SignUpParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
        listTestCase.add(testCase2);

        SignUpParams params3 = new SignUpParams("email", "duonghoang124@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "");
        TestCase<SignUpParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
        listTestCase.add(testCase3);

        SignUpParams params4 = new SignUpParams("email", "duonghoang103@gmail.com", "password", "123456", "re_pass", "123456", "address", "", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty address", params4);
        listTestCase.add(testCase4);

        SignUpParams params5 = new SignUpParams("email", "", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
        listTestCase.add(testCase5);

        SignUpParams params6 = new SignUpParams("email", "duonghoang125@gmail.com", "password", "", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty password", params6);
        listTestCase.add(testCase6);

        SignUpParams params7 = new SignUpParams("email", "123", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with incorrect format email", params7);
        listTestCase.add(testCase7);

        SignUpParams params8 = new SignUpParams("email", "duonghoang100@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase8 = new TestCase<>("1001", "", "Unit test 8: Should throw error 1001 with duplicated email", params8);
        listTestCase.add(testCase8);

        SignUpParams params9 = new SignUpParams("email", "duonghoang14@gmail.com", "password", "123456", "re_pass", "", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase9 = new TestCase<>("1001", "", "Unit test 9: Should throw error 1001 with empty repass", params9);
        listTestCase.add(testCase9);

        SignUpParams params10 = new SignUpParams("email", "duonghoang15@gmail.com", "password", "123456", "re_pass", "123", "address", "hanoi", "name", "duong", "phone", "09123");
        TestCase<SignUpParams> testCase10 = new TestCase<>("1001", "", "Unit test 9: Should throw error 1001 with wrong repass", params10);
        listTestCase.add(testCase10);


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

        private SignUpParams(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5, String key6, String value6) {
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