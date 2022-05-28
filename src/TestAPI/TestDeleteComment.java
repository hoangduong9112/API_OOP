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

public class TestDeleteComment {
    private TestDeleteComment(APIParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setDeleteComments(apiParams.auctionID);
        URL url = new URL(APIPath.getDeleteComment());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("AuctionID: " + apiParams.auctionID);
        if (apiParams.token) {
            apiParams.setAccessToken();
            System.out.println("Have Token");
        } else {
            apiParams.loginOtherAccount();
            System.out.println("Login Other Account ");
        }
        connection.addRequestProperty("Authorization", "Bearer " + apiParams.accessToken);

        connection.setRequestMethod("POST");

        StringBuilder postData = new StringBuilder();
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
        List<TestCase<APIParams>> listTestCase = new ArrayList<>();

        //Need to update auctionID in testcase 1, 2

//        APIParams params1 = new APIParams(132, true);
//        TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
//        listTestCase.add(testCase1);

//        APIParams params2 = new APIParams(131, false);
//        TestCase<APIParams> testCase2 = new TestCase<>("1006", "", "Unit test 2: No permisson", params2);
//        listTestCase.add(testCase2);


        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Delete Comment API" + ColorTerminal.ANSI_RESET);

        for (TestCase<APIParams> testCase : listTestCase) {
            new TestDeleteComment(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class APIParams {

        String accessToken = "";
        int auctionID;
        boolean token;

        private APIParams(int auctionID, boolean token) {
            this.auctionID = auctionID;
            this.token = token;
        }
        public void loginOtherAccount() throws
                IOException {
            URL url = new URL(APIPath.LOGIN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            Map<String, String> params = new HashMap<>();
            params.put("email", "minhtuyenpa@gmail.com");
            params.put("password", "sinhvienbka2407");

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
                String accessToken = rp.data.access_token;
                this.accessToken = accessToken;
            } finally {
                connection.disconnect();
            }
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
