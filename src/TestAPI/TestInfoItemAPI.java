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
import java.util.List;


public class TestInfoItemAPI {
    private TestInfoItemAPI(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setInfoItem(itemParams.itemID);
        URL url = new URL(APIPath.getInfoItem());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("ItemID: " + itemParams.itemID + ", Have token");
            connection.addRequestProperty("Authorization", "Bearer " + itemParams.accessToken);
        } else {
            System.out.println("ItemID: " + itemParams.itemID + ", Don't have token");
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
            System.out.println(connection.getResponseCode());
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();

        ItemParams params1 = new ItemParams(1, true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams(2, true);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct token", params2);
        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams(3, true);
        TestCase<ItemParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct token", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams(4, false);
        TestCase<ItemParams> testCase4 = new TestCase<>("1004", "まだログインではありません", "Unit test 4: Should throw error 1004 because user haven't login", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams(5, false);
        TestCase<ItemParams> testCase5 = new TestCase<>("1004", "まだログインではありません", "Unit test 5: Should throw error 1004 because user haven't login", params5);
        listTestCase.add(testCase5);

        ItemParams params6 = new ItemParams(6, false);
        TestCase<ItemParams> testCase6 = new TestCase<>("1004", "まだログインではありません", "Unit test 6: Should throw error 1004 because user haven't login", params6);
        listTestCase.add(testCase6);
//
        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Info Item API" + ColorTerminal.ANSI_RESET);
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestInfoItemAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class ItemParams {
        String accessToken;
        int itemID;
        boolean token;

        private ItemParams(int itemID, boolean token) {
            this.itemID = itemID;
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