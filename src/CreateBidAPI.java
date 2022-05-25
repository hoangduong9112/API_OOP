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

public class CreateBidAPI {

    private CreateBidAPI(CreateBidParams createBidParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setCreateBid(createBidParams.auctionID);
        URL url = new URL(APIPath.getCreateBid());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (!createBidParams.accessToken.equals("")) {
            connection.addRequestProperty("Authorization", "Bearer " + createBidParams.accessToken);
        }

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
        List<TestCase<CreateBidParams>> listTestCase = new ArrayList<>();

        final String price = "price";
        final String bidLastID = "bidLastID";

        // NEED TO INCREASE THE PRICE AFTER EACH TEST
        CreateBidParams params1 = new CreateBidParams(5, price, "80000000005", bidLastID, "39");
        TestCase<CreateBidParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        CreateBidParams params2 = new CreateBidParams(5, price, "", bidLastID, "39");
        TestCase<CreateBidParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty price", params2);
        listTestCase.add(testCase2);

        CreateBidParams params3 = new CreateBidParams(5, price, "80000000005", bidLastID, "");
        TestCase<CreateBidParams> testCase3 = new TestCase<>("1000", "", "Unit test 3: Should be successful with empty bidLastID", params3);
        listTestCase.add(testCase3);

        CreateBidParams params4 = new CreateBidParams(5, price, "", bidLastID, "");
        TestCase<CreateBidParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty price and bidLastID", params4);
        listTestCase.add(testCase4);
        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Edit Account API" + ColorTerminal.ANSI_RESET);

        for (TestCase<CreateBidParams> testCase : listTestCase) {
            new CreateBidAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class CreateBidParams {
        String price;
        String bidLastID;
        String priceValue;
        String bidLastIDValue;
        int auctionID;


        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hdWN0aW9ucy1hcHAtMi5oZXJva3VhcHAuY29tXC9hcGlcL2xvZ2luIiwiaWF0IjoxNjUzNDQwMjkxLCJleHAiOjE2NTM4MDAyOTEsIm5iZiI6MTY1MzQ0MDI5MSwianRpIjoia2lQWVZQVTZ6SmFubGJnYyIsInN1YiI6MzksInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.VCE9L_EsFQBOCz24nEa1yRlDLbby0SFXEOKMUcr8rPI";


        private CreateBidParams(int auctionID, String price, String priceValue, String bidLastID, String bidLastIDValue) {
            this.auctionID = auctionID;
            this.price = price;
            this.priceValue = priceValue;
            this.bidLastID = bidLastID;
            this.bidLastIDValue = bidLastIDValue;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
