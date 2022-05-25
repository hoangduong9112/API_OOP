import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDetailAuctionAPI {
    private GetDetailAuctionAPI(APIParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setGetDetailAuction(apiParams.auctionID);
        URL url = new URL(APIPath.getGetDetailAuction());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("AuctionID: "+apiParams.auctionID);
        if (!AccessToken.accessTokenValue.equals("")) {
            connection.addRequestProperty("Authorization", "Bearer " + AccessToken.accessTokenValue);
            System.out.println("Token: True");
        }else{
            System.out.println("Token: Empty");
        }
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
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
        if (AccessToken.accessTokenValue.equals("")) {
            APIParams params1 = new APIParams(1);
            TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
            listTestCase.add(testCase1);

            APIParams params2 = new APIParams(2);
            TestCase<APIParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
            listTestCase.add(testCase2);

            APIParams params3 = new APIParams(4);
            TestCase<APIParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
            listTestCase.add(testCase3);

            APIParams params4 = new APIParams(5);
            TestCase<APIParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params4);
            listTestCase.add(testCase4);
        }else{
            APIParams params1 = new APIParams(5);
            TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
            listTestCase.add(testCase1);

            APIParams params2 = new APIParams(4);
            TestCase<APIParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
            listTestCase.add(testCase2);

            APIParams params3 = new APIParams(1);
            TestCase<APIParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
            listTestCase.add(testCase3);

            APIParams params4 = new APIParams(2);
            TestCase<APIParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params4);
            listTestCase.add(testCase4);
        }



        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get Detail Auction API" + ColorTerminal.ANSI_RESET);

        for (TestCase<APIParams> testCase : listTestCase) {
            new GetDetailAuctionAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class APIParams {
        String accessToken = "";
        int auctionID;

        private APIParams(int auctionID) {
            this.auctionID = auctionID;
        }

        //this func can use when user login success
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
