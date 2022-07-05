package test_api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.APIPath;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestTotalLikesOfAuction extends TestBase {

    private TestTotalLikesOfAuction(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setTotalLikesOfAuction(itemParams.auctionID);
        String result;
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("Have Token");
            result = getMethod(APIPath.getTotalLikesOfAuction(), null, itemParams.accessToken);
        } else {
            System.out.println("Don't have token ");
            result = getMethod(APIPath.getTotalLikesOfAuction(), null, null);
        }
        Gson g = new Gson();

        Type response = new TypeToken<Response<TotalLikesOfAuctionDataType>>() {
        }.getType();
        Response<TotalLikesOfAuctionDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().auction_id != null;
                assert rp.getData().total_liked != null;
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

        ItemParams params1 = new ItemParams( 1,true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams(2,true);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with token", params2);
        listTestCase.add(testCase2);

        ItemParams params4 = new ItemParams(1,false);
        TestCase<ItemParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful without token", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams(2,false);
        TestCase<ItemParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful without token", params5);
        listTestCase.add(testCase5);

//
        System.out.println(getAnsiBlue() + "Testing Get Total Likes Of Auction API" + getAnsiReset());
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestTotalLikesOfAuction(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }
    private static class ItemParams {

        int auctionID;
        String accessToken;
        boolean token;

        private ItemParams(int auctionID, boolean token) {
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

    protected static class TotalLikesOfAuctionDataType{
        protected String auction_id;
        protected String total_liked;
    }
}
