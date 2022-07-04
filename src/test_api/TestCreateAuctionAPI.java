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

public class TestCreateAuctionAPI extends TestBase {

    private TestCreateAuctionAPI(CreateAuctionParams createAuctionParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
            createAuctionParams.setAccessToken();
            Map<String, String> params = new HashMap<>();
            params.put("category_id", String.valueOf(createAuctionParams.category_id));
            params.put("title_ni", createAuctionParams.title_ni);
            params.put("start_date", String.valueOf(createAuctionParams.start_date));
            params.put("end_date", String.valueOf(createAuctionParams.end_date));

            String result = postMethod(APIPath.getCreateAuctionURL(), params, createAuctionParams.access_token);

            Gson g = new Gson();
            Type response = new TypeToken<Response<CreateAuctionDataType>>() {}.getType();
            Response<CreateAuctionDataType> rp = g.fromJson(result, response);
            System.out.println(testDescription);
            try {
                assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
                assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
                if (codeExpectation.equals("1000")) {
                    assert rp.getData().auction_id != null;
                    assert rp.getData().title != null;
                    assert rp.getData().category_id != null;
                    assert rp.getData().selling_user_id != null;
                    assert rp.getData().start_date != null;
                    assert rp.getData().end_date != null;
                    assert rp.getData().status != null;
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
        List<TestCase<CreateAuctionParams>> listTestCase = new ArrayList<>();

        // need to update and new title
        CreateAuctionParams params1 = new CreateAuctionParams(5, "TestTitle104", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        // need to title similar to test case 1

        CreateAuctionParams params2 = new CreateAuctionParams(5, "TestTitle104", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase2 = new TestCase<>("1001", "category: &start_date: &end_date: &title: 7005", "Unit test 2: Should throw error with code 1001 with exist title", params2);
        listTestCase.add(testCase2);

        CreateAuctionParams params3 = new CreateAuctionParams(5, "", "2022-10-05T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase3 = new TestCase<>("1001", "category: &start_date: &end_date: &title: 7000", "Unit test 3: Should throw error with code 1001 with empty title", params3);
        listTestCase.add(testCase3);

        CreateAuctionParams params4 = new CreateAuctionParams(5, "TestTitle2", "", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase4 = new TestCase<>("1001", "category: &start_date: 7000&end_date: &title: ", "Unit test 4: Should throw error with code 1001 with empty start_date", params4);
        listTestCase.add(testCase4);

        //need to update startDate is now
        CreateAuctionParams params5 = new CreateAuctionParams(5, "TestTitle2", "2022-06-19T14:48:00.000Z", "2022-10-06T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase5 = new TestCase<>("1001", "category: &start_date: 7009&end_date: &title: ", "Unit test 5: Should throw error with code 1001 with start_date not 1 day after today", params5);
        listTestCase.add(testCase5);

        CreateAuctionParams params6 = new CreateAuctionParams(5, "TestTitle2", "2022-10-05T14:48:00.000Z", "");
        TestCase<CreateAuctionParams> testCase6 = new TestCase<>("1001", "category: &start_date: &end_date: 7000&title: ", "Unit test 6: Should throw error with code 1001 with empty endDate", params6);
        listTestCase.add(testCase6);

        CreateAuctionParams params7 = new CreateAuctionParams(5, "TestTitle2", "2022-10-05T14:48:00.000Z", "2022-10-04T14:48:00.000Z");
        TestCase<CreateAuctionParams> testCase7 = new TestCase<>("1001", "category: &start_date: &end_date: 7010&title: ", "Unit test 7: Should throw error with code 1001 with endDate before startDate", params7);
        listTestCase.add(testCase7);

        System.out.println(getAnsiBlue() + "Testing Create Auction API" + getAnsiReset());
        for (TestCase<CreateAuctionParams> testCase : listTestCase) {
            new TestCreateAuctionAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class CreateAuctionParams {
        int category_id;
        String title_ni;
        String start_date;
        String end_date;

        String access_token;


        private CreateAuctionParams(int category_id, String title_ni, String start_date, String end_date) {
            this.category_id = category_id;
            this.title_ni = title_ni;
            this.start_date = start_date;
            this.end_date = end_date;
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

    protected static class CreateAuctionDataType{
        protected String auction_id;
        protected String title;
        protected String category_id;
        protected String selling_user_id;
        protected String start_date;
        protected String end_date;
        protected String status;
    }

}
