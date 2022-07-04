package test_api;


import com.google.gson.reflect.TypeToken;
import utils.APIPath;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCreateBidAPI extends TestBase {

    private TestCreateBidAPI(CreateBidParams createBidParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setCreateBid(createBidParams.auctionID);
        Map<String, String> params = new HashMap<>();
        params.put(createBidParams.price, createBidParams.priceValue);
        params.put(createBidParams.bidLastID, createBidParams.bidLastIDValue);
        String result;
        if (createBidParams.isToken) {
            createBidParams.setAccessToken();
            result = postMethod(APIPath.getCreateBid(), params, createBidParams.access_token);
        } else {
            result = postMethod(APIPath.getCreateBid(), params, null);
        }
        Gson g = new Gson();
        Type response = new TypeToken<Response<CreateBidDataType>>() {}.getType();
        Response<CreateBidDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().message != null;
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
        List<TestCase<CreateBidParams>> listTestCase = new ArrayList<>();

        final String price = "price";
        final String bidLastID = "bidLastID";

        // Testcase 1 and 3 need to update price higher than old price

        CreateBidParams params1 = new CreateBidParams(5, price, "800000000107", bidLastID, "39", true);
        TestCase<CreateBidParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        CreateBidParams params2 = new CreateBidParams(5, price, "", bidLastID, "39", true);
        TestCase<CreateBidParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty price", params2);
        listTestCase.add(testCase2);

        CreateBidParams params3 = new CreateBidParams(5, price, "800000000108", bidLastID, "", true);
        TestCase<CreateBidParams> testCase3 = new TestCase<>("1000", "", "Unit test 3: Should be successful with empty bidLastID", params3);
        listTestCase.add(testCase3);

        CreateBidParams params4 = new CreateBidParams(5, price, "80", bidLastID, "", true);
        TestCase<CreateBidParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with lower price", params4);
        listTestCase.add(testCase4);

        System.out.println(getAnsiBlue() + "Testing Create Bid API" + getAnsiReset());

        for (TestCase<CreateBidParams> testCase : listTestCase) {
            new TestCreateBidAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class CreateBidParams {
        String price;
        String bidLastID;
        String priceValue;
        String bidLastIDValue;
        int auctionID;
        boolean isToken;


        String access_token;


        private CreateBidParams(int auctionID, String price, String priceValue, String bidLastID, String bidLastIDValue, boolean isToken) {
            this.auctionID = auctionID;
            this.price = price;
            this.priceValue = priceValue;
            this.bidLastID = bidLastID;
            this.bidLastIDValue = bidLastIDValue;
            this.isToken = isToken;

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
    protected static class CreateBidDataType{
        protected String message;
    }

}
