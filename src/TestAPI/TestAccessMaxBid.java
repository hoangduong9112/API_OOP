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

public class TestAccessMaxBid {
    private TestAccessMaxBid(apiParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setAccessMaxBid(apiParams.auctionID);
        URL url = new URL(APIPath.getAccessMaxBid());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (apiParams.token) {
            apiParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + apiParams.accessToken);
        }

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("selling_info", apiParams.sellingInfo);
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
            if (connection.getResponseCode() == 302) {
                url = new URL(APIPath.loginFailed);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
            }
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
        List<TestCase<apiParams>> listTestCase = new ArrayList<>();


        apiParams params1 = new apiParams(8, "iphone", true);
        TestCase<apiParams> testCase1 = new TestCase<>("1006", "", "Unit test 1: Should throw error 1006 because of no permission", params1);
        listTestCase.add(testCase1);

        apiParams params2 = new apiParams(8, "iphone", false);
        TestCase<apiParams> testCase2 = new TestCase<>("1004", "", "Unit test 2: Should throw error 1004 because user haven't login", params2);
        listTestCase.add(testCase2);

        apiParams params3 = new apiParams(8, "", true);
        TestCase<apiParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty selling info", params3);
        listTestCase.add(testCase3);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Access Max Bid API" + ColorTerminal.ANSI_RESET);

        for (TestCase<apiParams> testCase : listTestCase) {
            new TestAccessMaxBid(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class apiParams {
        String accessToken;
        int auctionID;
        String sellingInfo;

        boolean token;

        private apiParams(int auctionID, String sellingInfo, boolean token) {
            this.auctionID = auctionID;
            this.sellingInfo = sellingInfo;
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
