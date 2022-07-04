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

public class TestCreateCommentAPI extends TestBase {

    private TestCreateCommentAPI(CreateCommentParams createCommentParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setCreateComment(createCommentParams.auctionID);
        Map<String, String> params = new HashMap<>();
        params.put(createCommentParams.content, createCommentParams.contentValue);
        params.put(createCommentParams.commentLastID, createCommentParams.commentLastIDValue);

        String result;
        if (createCommentParams.isToken) {
            createCommentParams.setAccessToken();
            result = postMethod(APIPath.getCreateComment(), params, createCommentParams.access_token);
        } else result = postMethod(APIPath.getCreateComment(), params, null);


        Gson g = new Gson();
        Type response = new TypeToken<Response<CreateCommentDataType>>() {
        }.getType();
        Response<CreateCommentDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                if (rp.getData().comments != null) assert rp.getData().comments.length >0;
                else assert rp.getData().code.equals("1008");
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
        List<TestCase<CreateCommentParams>> listTestCase = new ArrayList<>();
        final String content = "content";
        final String comment_last_id = "comment_last_id";


        CreateCommentParams params1 = new CreateCommentParams(1, content, "And love", comment_last_id, "39", true);
        TestCase<CreateCommentParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct params", params1);
        listTestCase.add(testCase1);

        CreateCommentParams params2 = new CreateCommentParams(1, content, "", comment_last_id, "", true);
        TestCase<CreateCommentParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty content", params2);
        listTestCase.add(testCase2);

        CreateCommentParams params3 = new CreateCommentParams(2, content, "1234", comment_last_id, "6", true);
        TestCase<CreateCommentParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with status =4", params3);
        listTestCase.add(testCase3);

        System.out.println(getAnsiBlue() + "Testing Create Comment API" + getAnsiReset());

        for (TestCase<CreateCommentParams> testCase : listTestCase) {
            new TestCreateCommentAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class CreateCommentParams {
        int auctionID;
        String content;
        String contentValue;
        String commentLastID;
        String commentLastIDValue;
        String access_token;
        boolean isToken;

        private CreateCommentParams(int auctionID, String content, String contentValue, String commentLastID, String commentLastIDValue, boolean isToken) {
            this.auctionID = auctionID;
            this.content = content;
            this.contentValue = contentValue;
            this.commentLastID = commentLastID;
            this.commentLastIDValue = commentLastIDValue;
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
    protected static class CreateCommentDataType{
        protected String code;
        protected Object[] comments;
    }

}