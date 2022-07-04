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

public class TestGetListAuctionsAPI extends TestBase {
    private TestGetListAuctionsAPI(GetListAuctionsParams getListAuctionsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(getListAuctionsParams.index, getListAuctionsParams.indexValue);
        params.put(getListAuctionsParams.count, getListAuctionsParams.countValue);

        APIPath.setGetListAuctions(getListAuctionsParams.statusID);
        String result;
        if (getListAuctionsParams.token) {
            getListAuctionsParams.setAccessToken();
            result = getMethod(APIPath.getGetListAuctions(), params, getListAuctionsParams.access_token);
        } else {
            result = getMethod(APIPath.getGetListAuctions(), params, null);
        }
        Gson g = new Gson();
        Type response = new TypeToken<Response<GetListAuctionsDataType>>() {
        }.getType();
        Response<GetListAuctionsDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().auctions.length > 0;
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

    public static void main() throws IOException {
        List<TestCase<GetListAuctionsParams>> listTestCase = new ArrayList<>();

        GetListAuctionsParams params1 = new GetListAuctionsParams(1, "index", "1", "count", "1", "", "", true);
        TestCase<GetListAuctionsParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        GetListAuctionsParams params2 = new GetListAuctionsParams(1, "index", "1", "count", "1", "user_id", "3", true);
        TestCase<GetListAuctionsParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        GetListAuctionsParams params3 = new GetListAuctionsParams(1, "index", "1", "count", "1", "category_id", "1", true);
        TestCase<GetListAuctionsParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        GetListAuctionsParams params4 = new GetListAuctionsParams(1, "index", "1", "count", "1", "type", "1", true);
        TestCase<GetListAuctionsParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        GetListAuctionsParams params5 = new GetListAuctionsParams(1, "index", "", "count", "", "", "", false);
        TestCase<GetListAuctionsParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should be successful with empty count", params5);
        listTestCase.add(testCase5);

        GetListAuctionsParams params7 = new GetListAuctionsParams(1, "index", "", "count", "", "user_id", "", false);
        TestCase<GetListAuctionsParams> testCase7 = new TestCase<>("1000", "", "Unit test 7: Should be successful with empty param", params7);
        listTestCase.add(testCase7);

        GetListAuctionsParams params8 = new GetListAuctionsParams(1, "index", "", "count", "", "", "", false);
        TestCase<GetListAuctionsParams> testCase8 = new TestCase<>("1000", "", "Unit test 8: Should be successful with correct param", params8);
        listTestCase.add(testCase8);

        System.out.println(getAnsiBlue() + "Testing Get List Auction API" + getAnsiReset());
        for (TestCase<GetListAuctionsParams> testCase : listTestCase) {
            new TestGetListAuctionsAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class GetListAuctionsParams {
        int statusID;
        String access_token;
        String index;
        String indexValue;
        String count;
        String countValue;
        String optional;
        String optionalValue;
        boolean token;

        private GetListAuctionsParams(int statusID, String index, String indexValue, String count, String countValue, String optional, String optionalValue, boolean token) {
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
            this.optional = optional;
            this.optionalValue = optionalValue;
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
    protected static class GetListAuctionsDataType{
        protected Object[] auctions;
        protected int total;
    }

}

