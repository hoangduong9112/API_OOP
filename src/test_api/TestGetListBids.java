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

public class TestGetListBids {
    private TestGetListBids(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        Map<String, String> params = new HashMap<>();
        params.put("index", itemParams.index);
        params.put("count", itemParams.count);

        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListBids(itemParams.auctionID);
        URL url = new URL(APIPath.getGetListBids() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("Have token");
            connection.addRequestProperty("Authorization", "Bearer " + itemParams.accessToken);
        } else {
            System.out.println("Don't have token");
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
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();

        ItemParams params1 = new ItemParams( 1,"1", "2",true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful correct index, count and token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams(1,"","2",true);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with empty index (Have token)", params2);
        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams(1,"1", "",true);
        TestCase<ItemParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with empty count (Have token)", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams(1,"","",true);
        TestCase<ItemParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with empty count, index (Have token)", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams(1,"2","1",false);
        TestCase<ItemParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should thrown error with empty token", params5);
        listTestCase.add(testCase5);

        ItemParams params6 = new ItemParams(1,"1", "2",false);
        TestCase<ItemParams> testCase6 = new TestCase<>("1000", "OK", "Unit test 6: Should be successful with empty token", params6);
        listTestCase.add(testCase6);
//
        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get List Bids API" + ColorTerminal.ANSI_RESET);
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestGetListBids(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }
    private static class ItemParams {
        String index;

        String count;
        String accessToken;
        boolean token;

        int auctionID;
        private ItemParams(int auctionID, String index, String count, boolean token) {
            this.auctionID = auctionID;
            this.index = index;
            this.count = count;
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
