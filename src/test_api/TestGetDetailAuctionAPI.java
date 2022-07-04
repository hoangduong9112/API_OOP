package test_api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.APIPath;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TestGetDetailAuctionAPI extends TestBase {
    private TestGetDetailAuctionAPI(APIParams apiParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setGetDetailAuction(apiParams.auctionID);
        System.out.println("AuctionID: " + apiParams.auctionID);
        String result;
        if (apiParams.token) {
            apiParams.setAccessToken();
            System.out.println("Have Token");
            result = getMethod(APIPath.getGetDetailAuction(), null, apiParams.accessToken);
        } else {
            System.out.println("Don't have token ");
            result = getMethod(APIPath.getGetDetailAuction(), null, null);
        }
        Gson g = new Gson();

        Type response = new TypeToken<Response<GetDetailAuctionDataType>>() {
        }.getType();
        Response<GetDetailAuctionDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().auctions != null;
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
        List<TestCase<APIParams>> listTestCase = new ArrayList<>();
        APIParams params1 = new APIParams(1, false);
        TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        APIParams params2 = new APIParams(2, false);
        TestCase<APIParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        APIParams params3 = new APIParams(3, true);
        TestCase<APIParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        APIParams params4 = new APIParams(5, true);
        TestCase<APIParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        System.out.println(getAnsiBlue() + "Testing Get Detail Auction API" + getAnsiReset());

        for (TestCase<APIParams> testCase : listTestCase) {
            new TestGetDetailAuctionAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class APIParams {
        String accessToken;
        int auctionID;
        boolean token;

        private APIParams(int auctionID, boolean token) {
            this.auctionID = auctionID;
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

    protected static class GetDetailAuctionDataType {
        protected String code;
        protected Object auctions;
    }

}
