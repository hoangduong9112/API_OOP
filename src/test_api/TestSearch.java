package test_api;


import com.google.gson.Gson;
import utils.APIPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSearch extends TestBase {
    private TestSearch(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        Map<String, String> params = new HashMap<>();
        params.put("type", itemParams.type);
        params.put("key", itemParams.key);
//        System.out.println("AuctionID: "+itemParams.auctionID+", Index: "+itemParams.indexValue +", Count: "+itemParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        URL url = new URL(APIPath.SEARCH + "?" + query);
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
            System.out.println(content.getClass());
            Gson g = new Gson();
            Response rp = g.fromJson(content.toString(), Response.class);
            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);

            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
            System.out.println(connection.getResponseCode());
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();

        ItemParams params1 = new ItemParams("1","1",true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct type, key and token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams("1","1",false);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct type, key and without token", params2);
        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams("10","12", false);
        TestCase<ItemParams> testCase3 = new TestCase<>("9998", "検索できません", "Unit test 3: Should throw error 9998 with invalid type", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams("1","",false);
        TestCase<ItemParams> testCase4 = new TestCase<>("9998", "検索できません", "Unit test 4: Should throw error 9998 with empty key", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams("","3",false);
        TestCase<ItemParams> testCase5 = new TestCase<>("9998", "検索できません", "Unit test 5: Should throw error 9998 with empty type", params5);
        listTestCase.add(testCase5);

        ItemParams params6 = new ItemParams("","3",false);
        TestCase<ItemParams> testCase6 = new TestCase<>("9998", "検索できません", "Unit test 6: Should throw error 9998 with both empty type and key", params6);
        listTestCase.add(testCase6);
//
        System.out.println(getAnsiBlue() + "Testing Search API" + getAnsiReset());
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestSearch(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }
    private static class ItemParams {
        String accessToken;
        String type;
        String key;
        boolean token;

        private ItemParams(String type, String key, boolean token) {
            this.token = token;
            this.type = type;
            this.key = key;
        }

        public void setAccessToken() {
            String accessToken;
            try {
                accessToken =callLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.accessToken = accessToken;
        }
    }
}
