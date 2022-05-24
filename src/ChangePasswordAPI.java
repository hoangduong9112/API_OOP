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

public class ChangePasswordAPI {

    private ChangePasswordAPI(ChangePasswordParams changePasswordParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.CHANGEPASS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(changePasswordParams.key1, changePasswordParams.value1);
        params.put(changePasswordParams.key2, changePasswordParams.value2);
        params.put(changePasswordParams.key3, changePasswordParams.value3);

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
        List<TestCase<ChangePasswordParams>> listTestCase = new ArrayList<>();

        final String oldPasswordKey = "123456789";

        ChangePasswordParams params1 = new ChangePasswordParams("old_pass", oldPasswordKey, "new_pass", "0987654321", "re_pass", "0987654321");
        TestCase<ChangePasswordParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        ChangePasswordParams params2 = new ChangePasswordParams("old_pass", oldPasswordKey, "new_pass", "0987654321", "re_pass", "");
        TestCase<ChangePasswordParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty re_pass", params2);
        listTestCase.add(testCase2);

        ChangePasswordParams params3 = new ChangePasswordParams("old_pass", "1234", "new_pass", "123456", "re_pass", "123456");
        TestCase<ChangePasswordParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should be successful with correct old_pass", params3);
        listTestCase.add(testCase3);

        ChangePasswordParams params4 = new ChangePasswordParams("old_pass", oldPasswordKey, "new_pass", "", "re_pass", "0987654321");
        TestCase<ChangePasswordParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty new_pass", params4);
        listTestCase.add(testCase4);

        ChangePasswordParams params5 = new ChangePasswordParams("old_pass", oldPasswordKey, "new_pass", "0987654321", "re_pass", "abcd");
        TestCase<ChangePasswordParams> testCase5 = new TestCase<>("1000", "", "Unit test 5: Should throw error 1001 with re_pass is not similar to new_pass", params5);
        listTestCase.add(testCase5);

        ChangePasswordParams params6 = new ChangePasswordParams("old_pass", "", "new_pass", "0987654321", "re_pass", "0987654321");
        TestCase<ChangePasswordParams> testCase6 = new TestCase<>("1000", "", "Unit test 6: Should throw error 1001 with empty old_pass", params6);
        listTestCase.add(testCase6);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing SignUp API" + ColorTerminal.ANSI_RESET);

        for (TestCase<ChangePasswordParams>testCase : listTestCase) {
            new ChangePasswordAPI (testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class ChangePasswordParams{
        String key1;
        String value1;
        String key2;
        String value2;
        String key3;
        String value3;

        private ChangePasswordParams(String key1, String value1, String key2, String value2, String key3, String value3) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
        }
    }

}