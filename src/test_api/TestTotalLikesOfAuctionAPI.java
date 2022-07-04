package test_api;

import com.google.gson.reflect.TypeToken;
import utils.APIPath;
import utils.ColorTerminalDeprecate;
import utils.TestCaseDeprecated;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestTotalLikesOfAuctionAPI extends TestBase {
    private TestTotalLikesOfAuctionAPI(TotalLikesOfAuctionParams totalLikesOfAuctionParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setTotalLikesOfAuction(totalLikesOfAuctionParams.auctionID);
        URL url = new URL(APIPath.getTotalLikesOfAuction());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (totalLikesOfAuctionParams.isToken) {
            totalLikesOfAuctionParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + totalLikesOfAuctionParams.accessToken);
        }
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 302) {
            url = new URL(APIPath.LOGIN_FAILED);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        }
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            Gson g = new Gson();
            Type response = new TypeToken<Response<TotalLikesOfAuctionType>>() {}.getType();
            Response<TotalLikesOfAuctionType> rp = g.fromJson(content.toString(), response);

            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            System.out.println(ColorTerminalDeprecate.getAnsiGreen() + "Pass" + ColorTerminalDeprecate.getAnsiReset());
            System.out.println();
        } finally {
            connection.disconnect();
        }


    }

    public static void main() throws IOException {
        List<TestCaseDeprecated<TotalLikesOfAuctionParams>> listTestCase = new ArrayList<>();

        TotalLikesOfAuctionParams params1 = new TotalLikesOfAuctionParams(true, 3);
        TestCaseDeprecated<TotalLikesOfAuctionParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        TotalLikesOfAuctionParams params2 = new TotalLikesOfAuctionParams(false, 3);
        TestCaseDeprecated<TotalLikesOfAuctionParams> testCase2 = new TestCaseDeprecated<>("1000", "", "Unit test 2: Should be successful even user haven't logined", params2);
        listTestCase.add(testCase2);

        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Total Likes Of Auction" + "API" + ColorTerminalDeprecate.getAnsiReset());
        for (TestCaseDeprecated<TotalLikesOfAuctionParams> testCase : listTestCase) {
            new TestTotalLikesOfAuctionAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class TotalLikesOfAuctionParams {
        String accessToken;
        boolean isToken;
        int auctionID;

        private TotalLikesOfAuctionParams(boolean isToken, int auctionID) {
            this.auctionID = auctionID;
            this.isToken = isToken;
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
    protected static class TotalLikesOfAuctionType {
        protected String auction_id;
        protected int total_liked;
    }
}

