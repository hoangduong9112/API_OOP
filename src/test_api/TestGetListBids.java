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

public class TestGetListBids extends TestBase {
    private TestGetListBids(GetListBidsParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        Map<String, String> params = new HashMap<>();
        params.put("index", itemParams.index);
        params.put("count", itemParams.count);

        APIPath.setGetListBids(itemParams.auctionID);
        String result;
        if (itemParams.token) {
            itemParams.setAccessToken();
            result = getMethod(APIPath.getGetListBids(), params, itemParams.access_token);
        } else {
            result = getMethod(APIPath.getGetListBids(), params, null);
        }
        Gson g = new Gson();
        Type response = new TypeToken<Response<GetListBidsDataType>>() {}.getType();
        Response<GetListBidsDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().bids.length > 0;
                assert rp.getData().total > 0;
            } else assert rp.getData() == null;
            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
        } catch(AssertionError e) {
            System.out.println(getAnsiRed() + "Received");
            System.out.println("      code: " + rp.getCode());
            System.out.println("      message: " + rp.getMessage());
            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiGreen() + "Expect");
            System.out.println("      code: " + codeExpectation);
            if(messageExpectation.length() > 0) System.out.println("      message: " + messageExpectation);
            System.out.println("      data: " + rp.getData());
            System.out.println(getAnsiReset());
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<GetListBidsParams>> listTestCase = new ArrayList<>();

        GetListBidsParams params1 = new GetListBidsParams(1, "1", "2", true);
        TestCase<GetListBidsParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful correct index, count and token", params1);
        listTestCase.add(testCase1);

        GetListBidsParams params2 = new GetListBidsParams(1, "", "2", true);
        TestCase<GetListBidsParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with empty index (Have token)", params2);
        listTestCase.add(testCase2);

        GetListBidsParams params3 = new GetListBidsParams(1, "1", "", true);
        TestCase<GetListBidsParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with empty count (Have token)", params3);
        listTestCase.add(testCase3);

        GetListBidsParams params4 = new GetListBidsParams(1, "", "", true);
        TestCase<GetListBidsParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with empty count, index (Have token)", params4);
        listTestCase.add(testCase4);

        GetListBidsParams params5 = new GetListBidsParams(1, "2", "1", false);
        TestCase<GetListBidsParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should thrown error with empty token", params5);
        listTestCase.add(testCase5);

        GetListBidsParams params6 = new GetListBidsParams(1, "1", "2", false);
        TestCase<GetListBidsParams> testCase6 = new TestCase<>("1000", "OK", "Unit test 6: Should be successful with empty token", params6);
        listTestCase.add(testCase6);

        System.out.println(getAnsiBlue() + "Testing Get List Bids API" + getAnsiReset());

        for (TestCase<GetListBidsParams> testCase : listTestCase) {
            new TestGetListBids(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class GetListBidsParams {
        String index;

        String count;
        String access_token;
        boolean token;

        int auctionID;

        private GetListBidsParams(int auctionID, String index, String count, boolean token) {
            this.auctionID = auctionID;
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
            this.access_token = accessToken;
        }
    }
    protected static class GetListBidsDataType{
        protected Object[] bids;
        protected int total;
    }
}
