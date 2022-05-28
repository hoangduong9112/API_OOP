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
public class TestLikeAuction {

    private TestLikeAuction(apiParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setLikeAuction(apiParams.auctionID);
        URL url = new URL(APIPath.getLikeAuction());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (apiParams.token) {
            apiParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + apiParams.accessToken);
        }

        connection.setRequestMethod("POST");
        StringBuilder postData = new StringBuilder();

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


        apiParams params1 = new apiParams(1, true);
        TestCase<apiParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct params", params1);
        listTestCase.add(testCase1);

        apiParams params2 = new apiParams(2,  true);
        TestCase<apiParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct params", params2);
        listTestCase.add(testCase2);

        apiParams params3 = new apiParams(2, false);
        TestCase<apiParams> testCase3 = new TestCase<>("1004", "", "Unit test 3: Should throw error 1004 because user haven't login", params3);
        listTestCase.add(testCase3);

        apiParams params4 = new apiParams(1, false);
        TestCase<apiParams> testCase4 = new TestCase<>("1004", "", "Unit test 3: Should throw error 1004 because user haven't login", params4);
        listTestCase.add(testCase4);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Update Like API" + ColorTerminal.ANSI_RESET);

        for (TestCase<apiParams> testCase : listTestCase) {
            new TestLikeAuction(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class apiParams {
        String accessToken;
        int auctionID;
        boolean token;

        private apiParams(int auctionID , boolean token) {
            this.auctionID = auctionID;
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
