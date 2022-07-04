package test_api;

import utils.api.LoginAPI;
import utils.APIPath;
import utils.ColorTerminalDeprecate;
import utils.ResponseDeprecated;
import utils.TestCaseDeprecated;
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

public class TestCreateBidAPI {

    private TestCreateBidAPI(CreateBidParams createBidParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setCreateBid(createBidParams.auctionID);
        URL url = new URL(APIPath.getCreateBid());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        createBidParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + createBidParams.accessToken);

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(createBidParams.price, createBidParams.priceValue);
        params.put(createBidParams.bidLastID, createBidParams.bidLastIDValue);
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
            ResponseDeprecated rp = g.fromJson(content.toString(), ResponseDeprecated.class);

            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            System.out.println(ColorTerminalDeprecate.getAnsiGreen() + "Pass" + ColorTerminalDeprecate.getAnsiReset());
            System.out.println();
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCaseDeprecated<CreateBidParams>> listTestCase = new ArrayList<>();

        final String price = "price";
        final String bidLastID = "bidLastID";

        // Testcase 1 and 3 need to update price higher than old price

        CreateBidParams params1 = new CreateBidParams(5, price, "80000000009", bidLastID, "39");
        TestCaseDeprecated<CreateBidParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        CreateBidParams params2 = new CreateBidParams(5, price, "", bidLastID, "39");
        TestCaseDeprecated<CreateBidParams> testCase2 = new TestCaseDeprecated<>("1001", "", "Unit test 2: Should throw error 1001 with empty price", params2);
        listTestCase.add(testCase2);

        CreateBidParams params3 = new CreateBidParams(5, price, "80000000010", bidLastID, "");
        TestCaseDeprecated<CreateBidParams> testCase3 = new TestCaseDeprecated<>("1000", "", "Unit test 3: Should be successful with empty bidLastID", params3);
        listTestCase.add(testCase3);

        CreateBidParams params4 = new CreateBidParams(5, price, "80", bidLastID, "");
        TestCaseDeprecated<CreateBidParams> testCase4 = new TestCaseDeprecated<>("1001", "", "Unit test 4: Should throw error 1001 with lower price", params4);
        listTestCase.add(testCase4);
        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Create Bid API" + ColorTerminalDeprecate.getAnsiReset());

        for (TestCaseDeprecated<CreateBidParams> testCase : listTestCase) {
            new TestCreateBidAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class CreateBidParams {
        String price;
        String bidLastID;
        String priceValue;
        String bidLastIDValue;
        int auctionID;


        String accessToken;


        private CreateBidParams(int auctionID, String price, String priceValue, String bidLastID, String bidLastIDValue) {
            this.auctionID = auctionID;
            this.price = price;
            this.priceValue = priceValue;
            this.bidLastID = bidLastID;
            this.bidLastIDValue = bidLastIDValue;
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
