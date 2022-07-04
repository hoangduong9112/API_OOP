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
import java.util.List;

public class TestReadNewsAPI {
    private TestReadNewsAPI(ReadNewsParams readNewsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setReadNews(readNewsParams.newID);
        URL url = new URL(APIPath.getReadNews());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (readNewsParams.isToken == true) {
            readNewsParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + readNewsParams.accessToken);
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
        List<TestCaseDeprecated<ReadNewsParams>> listTestCase = new ArrayList<>();

        ReadNewsParams params1 = new ReadNewsParams(true, 3);
        TestCaseDeprecated<ReadNewsParams> testCase1 = new TestCaseDeprecated<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        ReadNewsParams params2 = new ReadNewsParams(false, 3);
        TestCaseDeprecated<ReadNewsParams> testCase2 = new TestCaseDeprecated<>("1004", "", "Unit test 2: Should throw error 1004 because user haven't logined", params2);
        listTestCase.add(testCase2);

        System.out.println(ColorTerminalDeprecate.getAnsiBlue() + "Testing Read News" + "API" + ColorTerminalDeprecate.getAnsiReset());
        for (TestCaseDeprecated<ReadNewsParams> testCase : listTestCase) {
            new TestReadNewsAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class ReadNewsParams {
        String accessToken;
        boolean isToken;
        int newID;

        private ReadNewsParams(boolean isToken, int newID) {
            this.newID = newID;
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

