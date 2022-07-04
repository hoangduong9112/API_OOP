package test_api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.APIPath;

import java.io.IOException;
import java.lang.reflect.Type;
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
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        String result;
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("Have Token");
            result = getMethod(APIPath.SEARCH + "?" + query, null, itemParams.accessToken);
        } else {
            System.out.println("Don't have token ");
            result = getMethod(APIPath.SEARCH + "?" + query, null, null);
        }
        Gson g = new Gson();

        Type response = new TypeToken<Response<SearchDataType[]>>() {
        }.getType();
        Response<SearchDataType[]> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().length > 0;
            } else assert rp.getData() == null;
            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
        } catch (AssertionError e) {
            System.out.println(getAnsiRed() + "Received");
            System.out.println("      code: " + rp.getCode());
            System.out.println("      message: " + rp.getMessage());
            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiGreen() + "Expect");
            System.out.println("      code: " + codeExpectation);
            if (messageExpectation.length() > 0) System.out.println("      message: " + messageExpectation);
            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiReset());
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

        ItemParams params3 = new ItemParams("1","",false);
        TestCase<ItemParams> testCase3 = new TestCase<>("9998", "Khong tim thay", "Unit test 3: Should throw error 9998 with empty key", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams("","",false);
        TestCase<ItemParams> testCase4 = new TestCase<>("9998", "検索できません", "Unit test 4: Should throw error 9998 with both empty type and key", params4);
        listTestCase.add(testCase4);
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

    protected static class SearchDataType{

    }
}
