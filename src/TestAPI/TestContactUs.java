package TestAPI;
import Utils.API.LoginAPI;
import Utils.APIPath;
import Utils.ColorTerminal;
import Utils.Response;
import Utils.TestCase;
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

public class TestContactUs {
    private TestContactUs(contactUsParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.contactUs);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (apiParams.token) {
            apiParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + apiParams.accessToken);
        }

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("name", apiParams.name);
        params.put("phone", apiParams.phone);
        params.put("email", apiParams.email);
        params.put("content", apiParams.content);
        params.put("reportType", apiParams.reportType);
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
        List<TestCase<contactUsParams>> listTestCase = new ArrayList<>();

        contactUsParams params1 = new contactUsParams("test", "123456789", "test@gmail.com", "Test noi dung", "他に", true);
        TestCase<contactUsParams> testCase1 = new TestCase<>("1001", "", "Unit test 1: Should throw error 1001 because report type is invalid", params1);
        listTestCase.add(testCase1);

        contactUsParams params2 = new contactUsParams("", "123456789", "test@gmail.com", "Test noi dung", "他に", true);
        TestCase<contactUsParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
        listTestCase.add(testCase2);

        contactUsParams params3 = new contactUsParams("test", "", "test@gmail.com", "Test noi dung", "123", true);
        TestCase<contactUsParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
        listTestCase.add(testCase3);

        contactUsParams params4 = new contactUsParams("test", "123456789", "", "Test noi dung", "他に", true);
        TestCase<contactUsParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty email", params4);
        listTestCase.add(testCase4);

        contactUsParams params5 = new contactUsParams("test", "123456789", "test@gmail.com", "", "他に", true);
        TestCase<contactUsParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty content", params5);
        listTestCase.add(testCase5);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Contact Us API" + ColorTerminal.ANSI_RESET);

        for (TestCase<contactUsParams> testCase : listTestCase) {
            new TestContactUs(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }


    private static class contactUsParams {
        String accessToken;
        String name;
        String phone;
        String email;
        String content;
        String reportType;
        boolean token;

        private contactUsParams(String name, String phone, String email, String content, String reportType, boolean token) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.content = content;
            this.reportType = reportType;
            this.token = token;

        }

        public void setAccessToken() {
            String accessToken;
            try {
                accessToken = LoginAPI.call();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.accessToken = accessToken;
        }


    }

}
