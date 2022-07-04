package test_api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class TestGetNews extends TestBase {

    private TestGetNews(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
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

        String result;
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("Have Token");
            result = getMethod(APIPath.GET_NEWS + "?" + query, null, itemParams.accessToken);
        } else {
            System.out.println("Don't have token ");
            result = getMethod(APIPath.GET_NEWS + "?" + query, null, null);
        }
        Gson g = new Gson();

        Type response = new TypeToken<Response<GetNewsDataType>>() {
        }.getType();
        Response<GetNewsDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().news.length >= 0;
                assert rp.getData().total != null;
            } else assert rp.getData() == null;
            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
        } catch (AssertionError e) {
            System.out.println(getAnsiRed() + "Received");
            System.out.println("      code: " + rp.getCode());
            System.out.println("      message: " + rp.getMessage());
//            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiGreen() + "Expect");
            System.out.println("      code: " + codeExpectation);
            if (messageExpectation.length() > 0) System.out.println("      message: " + messageExpectation);
//            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiReset());
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();

        ItemParams params1 = new ItemParams( "1", "2",true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful correct index, count and token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams("","2",true);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with empty index (Have token)", params2);
        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams("1", "",true);
        TestCase<ItemParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with empty count (Have token)", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams("","",true);
        TestCase<ItemParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with empty count, index (Have token)", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams("2","1",false);
        TestCase<ItemParams> testCase5 = new TestCase<>("1004", "Chưa đăng nhập", "Unit test 5: Should thrown error because user haven't login", params5);
        listTestCase.add(testCase5);

        ItemParams params6 = new ItemParams("1", "2",false);
        TestCase<ItemParams> testCase6 = new TestCase<>("1004", "Chưa đăng nhập", "Unit test 6: Should thrown error because user haven't login", params6);
        listTestCase.add(testCase6);
//
        System.out.println(getAnsiBlue() + "Testing Get News API" + getAnsiReset());
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestGetNews(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }
    private static class ItemParams {
        String index;

        String count;
        String accessToken;
        boolean token;

        private ItemParams(String index, String count, boolean token) {
            this.index = index;
            this.count = count;
            this.token = token;
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

    protected static class GetNewsDataType{
        protected Object[] news;
        protected String total;
    }
}
