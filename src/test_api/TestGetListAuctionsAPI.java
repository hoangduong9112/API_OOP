package test_api;

import utils.api.LoginAPI;
import utils.APIPath;
import utils.ColorTerminalDeprecate;
import utils.ResponseDeprecated;
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

public class TestGetListAuctionsAPI {
    private TestGetListAuctionsAPI(GetListAuctionsParams getListAuctionsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(getListAuctionsParams.index, getListAuctionsParams.indexValue);
        params.put(getListAuctionsParams.count, getListAuctionsParams.countValue);
        if (!getListAuctionsParams.optinal.equals("")) {
            params.put(getListAuctionsParams.optinal, getListAuctionsParams.optionalValue);
        }

        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListAuctions(getListAuctionsParams.statusID);
        URL url = new URL(APIPath.getGetListAuctions() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        getListAuctionsParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + getListAuctionsParams.accessToken);

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
            ResponseDeprecated rp = g.fromJson(content.toString(), ResponseDeprecated.class);

            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);
            System.out.println(ColorTerminalDeprecate.getAnsiGreen() + "Pass" + ColorTerminalDeprecate.getAnsiReset());
            System.out.println();
        } finally {
            connection.disconnect();
        }


    }

    public static void main() throws IOException {
        List<TestCaseDeprecated<GetListAuctionsParams>> listTestCase = new ArrayList<>();

        GetListAuctionsParams params1 = new GetListAuctionsParams(1, "index", "1", "count", "1");
        TestCaseDeprecated<GetListAuctionsParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        GetListAuctionsParams params2 = new GetListAuctionsParams(1, "index", "1", "count", "1", "user_id", "3");
        TestCaseDeprecated<GetListAuctionsParams> testCase2 = new TestCaseDeprecated<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        GetListAuctionsParams params3 = new GetListAuctionsParams(1, "index", "1", "count", "1", "category_id", "1");
        TestCaseDeprecated<GetListAuctionsParams> testCase3 = new TestCaseDeprecated<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        GetListAuctionsParams params4 = new GetListAuctionsParams(1, "index", "1", "count", "1", "type", "1");
        TestCaseDeprecated<GetListAuctionsParams> testCase4 = new TestCaseDeprecated<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        GetListAuctionsParams params5 = new GetListAuctionsParams(1, "index", "", "count", "");
        TestCaseDeprecated<GetListAuctionsParams> testCase5 = new TestCaseDeprecated<>("1000", "OK", "Unit test 5: Should be successful with empty count", params5);
        listTestCase.add(testCase5);

        GetListAuctionsParams params7 = new GetListAuctionsParams(1, "index", "", "count", "", "user_id", "");
        TestCaseDeprecated<GetListAuctionsParams> testCase7 = new TestCaseDeprecated<>("1000", "", "Unit test 7: Should be successful with empty param", params7);
        listTestCase.add(testCase7);

        GetListAuctionsParams params8 = new GetListAuctionsParams(1, "index", "", "count", "");
        TestCaseDeprecated<GetListAuctionsParams> testCase8 = new TestCaseDeprecated<>("1000", "", "Unit test 8: Should be successful with correct param", params8);
        listTestCase.add(testCase8);

        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Get List Auction API" + ColorTerminalDeprecate.getAnsiReset());
        for (TestCaseDeprecated<GetListAuctionsParams> testCase : listTestCase) {
            new TestGetListAuctionsAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class GetListAuctionsParams {
        int statusID;
        String accessToken;
        String index;
        String indexValue;
        String count;
        String countValue;
        String optinal = "";
        String optionalValue;

        private GetListAuctionsParams(int statusID, String index, String indexValue, String count, String countValue) {
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
        }

        private GetListAuctionsParams(int statusID, String index, String indexValue, String count, String countValue, String optinal, String optionalValue) {
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
            this.optinal = optinal;
            this.optionalValue = optionalValue;
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

