package TestAPI;

import Utils.API.LoginAPI;
import Utils.APIPath;
import Utils.ColorTerminal;
import Utils.Response;
import Utils.TestCase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTotalLikesOfAuctionAPI {
    private TestTotalLikesOfAuctionAPI(TotalLikesOfAuctionParams totalLikesOfAuctionParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setTotalLikesOfAuction(totalLikesOfAuctionParams.auctionID);
        URL url = new URL(APIPath.getTotalLikesOfAuction());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(totalLikesOfAuctionParams.isToken){
            totalLikesOfAuctionParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + totalLikesOfAuctionParams.accessToken);
        }
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 302) {
            url = new URL(APIPath.loginFailed);
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

    public static void main() throws IOException {
        List<TestCase<TotalLikesOfAuctionParams>> listTestCase = new ArrayList<>();

        TotalLikesOfAuctionParams params1 = new TotalLikesOfAuctionParams(true, 3);
        TestCase<TotalLikesOfAuctionParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        TotalLikesOfAuctionParams params2 = new TotalLikesOfAuctionParams(false, 3);
        TestCase<TotalLikesOfAuctionParams> testCase2 = new TestCase<>("1004", "", "Unit test 2: Should throw error 1004 because user haven't logined", params2);
        listTestCase.add(testCase2);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Total Likes Of Auction" + "API" + ColorTerminal.ANSI_RESET);
        for (TestCase<TotalLikesOfAuctionParams> testCase : listTestCase) {
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
                accessToken = LoginAPI.call();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.accessToken = accessToken;
        }
    }

}

