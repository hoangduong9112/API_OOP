package test_api;


import utils.APIPath;

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

public class TestCreateAuctionAPI extends TestBase {

    private TestCreateAuctionAPI(CreateAuctionParams createAuctionParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.CREATE_AUCTION);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        createAuctionParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + createAuctionParams.accessToken);

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("category_id", String.valueOf(createAuctionParams.category_id));
        params.put("title_ni", createAuctionParams.title_ni);
        params.put("start_date", String.valueOf(createAuctionParams.start_date));
        params.put("end_date", String.valueOf(createAuctionParams.end_date));

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
            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<CreateAuctionParams>> listTestCase = new ArrayList<>();

        // need to update and new title
        CreateAuctionParams params1 = new CreateAuctionParams(5, "TestTitle4", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        // need to title similar to test case 1

        CreateAuctionParams params2 = new CreateAuctionParams(6, "TestTitle4", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error with code 1001 with exist title", params2);
        listTestCase.add(testCase2);

        CreateAuctionParams params3 = new CreateAuctionParams(5, "", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error with code 1001 with empty title", params3);
        listTestCase.add(testCase3);

        CreateAuctionParams params4 = new CreateAuctionParams(5, "TestTitle2", "", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error with code 1001 with empty start_date", params4);
        listTestCase.add(testCase4);

        //need to update startDate is now
        CreateAuctionParams params5 = new CreateAuctionParams(5, "TestTitle2", "2022-06-19T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error with code 1001 with start_date not 1 day after today", params5);
        listTestCase.add(testCase5);

        CreateAuctionParams params6 = new CreateAuctionParams(5, "TestTitle2", "2022-10-05T14:48:00.000Z", "");
        TestCase<CreateAuctionParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error with code 1001 with empty endDate", params6);
        listTestCase.add(testCase6);

        CreateAuctionParams params7 = new CreateAuctionParams(5, "TestTitle2", "2022-10-05T14:48:00.000Z", "2022-10-04T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error with code 1001 with endDate before startDate", params7);
        listTestCase.add(testCase7);

        System.out.println(getAnsiBlue() + "Testing Create Auction API" + getAnsiReset());
        for (TestCase<CreateAuctionParams> testCase : listTestCase) {
            new TestCreateAuctionAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class CreateAuctionParams {
        int category_id;
        String title_ni;
        String start_date;
        String end_date;

        String accessToken;


        private CreateAuctionParams(int category_id, String title_ni, String start_date, String end_date) {
            this.category_id = category_id;
            this.title_ni = title_ni;
            this.start_date = start_date;
            this.end_date = end_date;
        }

        public void setAccessToken() {
            String accessToken;
            try {
                accessToken = callLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.accessToken = accessToken;
        }
    }

}
