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

public class TestGetListComments extends TestBase {
    private TestGetListComments(APIParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(apiParams.index, apiParams.indexValue);
        params.put(apiParams.count, apiParams.countValue);

        APIPath.setGetListComments(apiParams.auctionID);
        String result;
        if (apiParams.token) {
            apiParams.setAccessToken();
            result = getMethod(APIPath.getGetListComments(), params, apiParams.access_token);
        } else {
            result = getMethod(APIPath.getGetListComments(), params, null);
        }
        Gson g = new Gson();
        Type response = new TypeToken<Response<GetListCommentsDataType>>() {
        }.getType();
        Response<GetListCommentsDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().comments.length > 0;
                assert rp.getData().total > 0;
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
        final String index = "index";
        final String count = "count";
        List<TestCase<APIParams>> listTestCase = new ArrayList<>();

        APIParams params1 = new APIParams(1, index, "1", count, "5", false);
        TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        APIParams params2 = new APIParams(1, index, "", count, "2", false);
        TestCase<APIParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        APIParams params3 = new APIParams(1, index, "", count, "", false);
        TestCase<APIParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        APIParams params4 = new APIParams(1, index, "1", count, "", false);
        TestCase<APIParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        APIParams params5 = new APIParams(1, index, "1", count, "5", true);
        TestCase<APIParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should be successful with correct param", params5);
        listTestCase.add(testCase5);

        APIParams params6 = new APIParams(1, index, "", count, "2", true);
        TestCase<APIParams> testCase6 = new TestCase<>("1000", "OK", "Unit test 6: Should be successful with correct param", params6);
        listTestCase.add(testCase6);

        APIParams params7 = new APIParams(1, index, "", count, "", true);
        TestCase<APIParams> testCase7 = new TestCase<>("1000", "OK", "Unit test 7: Should be successful with correct param", params7);
        listTestCase.add(testCase7);

        APIParams params8 = new APIParams(1, index, "1", count, "", true);
        TestCase<APIParams> testCase8 = new TestCase<>("1000", "OK", "Unit test 8: Should be successful with correct param", params8);
        listTestCase.add(testCase8);

        System.out.println(getAnsiBlue() + "Testing Get List List Comments API" + getAnsiReset());

        for (TestCase<APIParams> testCase : listTestCase) {
            new TestGetListComments(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class APIParams {
        String access_token;
        String index;
        String indexValue;
        String count;
        String countValue;
        int auctionID;

        boolean token;

        private APIParams(int auctionID, String index, String indexValue, String count, String countValue, boolean token) {
            this.auctionID = auctionID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
            this.token = token;
        }

        public void setAccessToken() {
            String accessToken;
            try {
                accessToken = callLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.access_token = accessToken;
        }
    }

    protected static class GetListCommentsDataType {
        protected Object[] comments;
        protected int total;
    }

}
