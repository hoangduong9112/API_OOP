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

public class TestGetNewsAPI {
    private TestGetNewsAPI(GetNewsParams getNewsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(getNewsParams.index, getNewsParams.indexValue);
        params.put(getNewsParams.count, getNewsParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        URL url = new URL(APIPath.GET_NEWS + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (getNewsParams.isToken) {
            getNewsParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + getNewsParams.accessToken);
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

    public static void main() throws IOException {
        List<TestCaseDeprecated<GetNewsParams>> listTestCase = new ArrayList<>();
        final String index = "index";
        final String count = "count";
        GetNewsParams params1 = new GetNewsParams(true, index, "1", count, "1");
        TestCaseDeprecated<GetNewsParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        GetNewsParams params2 = new GetNewsParams(false, index, "1", count, "1");
        TestCaseDeprecated<GetNewsParams> testCase2 = new TestCaseDeprecated<>("1004", "", "Unit test 2: Should throw error 1004 because user haven't logined", params2);
        listTestCase.add(testCase2);
        GetNewsParams params3 = new GetNewsParams(true, index, "", count, "1");
        TestCaseDeprecated<GetNewsParams> testCase3 = new TestCaseDeprecated<>("1000", "OK", "Unit test 3: Should be successful with empty index", params3);
        listTestCase.add(testCase3);
        GetNewsParams params4 = new GetNewsParams(true, index, "1", count, "");
        TestCaseDeprecated<GetNewsParams> testCase4 = new TestCaseDeprecated<>("1000", "OK", "Unit test 4: Should be successful with empty count", params4);
        listTestCase.add(testCase4);

        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Get News " + "API" + ColorTerminalDeprecate.getAnsiReset());
        for (TestCaseDeprecated<GetNewsParams> testCase : listTestCase) {
            new TestGetNewsAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class GetNewsParams {
        String accessToken;
        String index;
        String indexValue;
        String count;
        String countValue;
        boolean isToken;

        private GetNewsParams(boolean isToken, String index, String indexValue, String count, String countValue) {
            this.isToken = isToken;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
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

