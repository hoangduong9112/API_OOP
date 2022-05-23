import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDetailAuctionAPI {
    private GetDetailAuctionAPI(GetDetailAuctionAPI.APIParams APIParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setGetDetailAuction(APIParams.AuctionID);
        URL url = new URL(APIPath.getGetDetailAuction());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (!APIParams.access_token_value.equals("")) {
            connection.addRequestProperty("Authorization", "Bearer " + APIParams.access_token_value);
        }
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                System.out.println(line);
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

        APIParams params1 = new APIParams(1);
        TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        APIParams params2 = new APIParams(2);
        TestCase<APIParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get Detail Auction API" + ColorTerminal.ANSI_RESET);

        for (TestCase<APIParams> testCase : listTestCase) {
            new GetDetailAuctionAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class APIParams {
        String access_token_value = "";
        int AuctionID;

        private APIParams(int AuctionID) {
            this.AuctionID = AuctionID;
        }

        //this func can use when user login success
        public void setAccess_token_value(String access_token_value) {
            this.access_token_value = access_token_value;
        }
    }

}
