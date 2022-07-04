package test_api;

import utils.api.LoginAPI;
import utils.APIPath;
import utils.ColorTerminalDeprecate;
import utils.Response;
import utils.TestCaseDeprecated;
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

public class TestGetListAuctionsByTypeAPI {

    private TestGetListAuctionsByTypeAPI(APIParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put(apiParams.index, apiParams.indexValue);
        params.put(apiParams.count, apiParams.countValue);
        System.out.println("TypeID: " + apiParams.typeID + ", StatusID: " + apiParams.statusID + ", Index: " + apiParams.indexValue + ", Count: " + apiParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListAuctionsByType(apiParams.typeID, apiParams.statusID);
        URL url = new URL(APIPath.getGetListAuctionsByType() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (apiParams.token == true) {
            apiParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + apiParams.accessToken);
            System.out.println("Have Token");
        } else {
            System.out.println("Don't have token ");
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
            System.out.println(ColorTerminalDeprecate.getAnsiGreen() + "Pass" + ColorTerminalDeprecate.getAnsiReset());
            System.out.println();
        } finally {
            connection.disconnect();
        }

    }

    public static void main() throws
            IOException {
        final String index = "index";
        final String count = "count";
        List<TestCaseDeprecated<APIParams>> listTestCase = new ArrayList<>();

        APIParams params1 = new APIParams(2, 0, index, "1", count, "2", false);
        TestCaseDeprecated<APIParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        APIParams params2 = new APIParams(1, 0, index, "", count, "2", false);
        TestCaseDeprecated<APIParams> testCase2 = new TestCaseDeprecated<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        APIParams params3 = new APIParams(2, 0, index, "1", count, "", false);
        TestCaseDeprecated<APIParams> testCase3 = new TestCaseDeprecated<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        APIParams params4 = new APIParams(4, 0, index, "", count, "", false);
        TestCaseDeprecated<APIParams> testCase4 = new TestCaseDeprecated<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        APIParams params5 = new APIParams(2, 0, index, "1", count, "2", true);
        TestCaseDeprecated<APIParams> testCase5 = new TestCaseDeprecated<>("1000", "OK", "Unit test 5: Should be successful with correct param", params5);
        listTestCase.add(testCase5);

        APIParams params6 = new APIParams(1, 0, index, "", count, "2", true);
        TestCaseDeprecated<APIParams> testCase6 = new TestCaseDeprecated<>("1000", "OK", "Unit test 6: Should be successful with correct param", params6);
        listTestCase.add(testCase6);

        APIParams params7 = new APIParams(2, 0, index, "1", count, "", true);
        TestCaseDeprecated<APIParams> testCase7 = new TestCaseDeprecated<>("1000", "OK", "Unit test 7: Should be successful with correct param", params7);
        listTestCase.add(testCase7);

        APIParams params8 = new APIParams(4, 0, index, "", count, "", true);
        TestCaseDeprecated<APIParams> testCase8 = new TestCaseDeprecated<>("1000", "OK", "Unit test 8: Should be successful with correct param", params8);
        listTestCase.add(testCase8);

        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Get List Auctions By Type API" + ColorTerminalDeprecate.getAnsiReset());

        for (TestCaseDeprecated<APIParams> testCase : listTestCase) {
            new TestGetListAuctionsByTypeAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class APIParams {
        int typeID;
        int statusID;
        String accessToken;
        String index;
        String indexValue;
        String count;
        String countValue;

        boolean token;

        private APIParams(int typeID, int statusID, String index, String indexValue, String count, String countValue, boolean token) {
            this.typeID = typeID;
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
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
