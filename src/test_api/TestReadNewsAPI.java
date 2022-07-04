package test_api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.APIPath;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestReadNewsAPI extends TestBase {
    private TestReadNewsAPI(ReadNewsParams readNewsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        APIPath.setReadNews(readNewsParams.newID);
        String result;
        if (readNewsParams.isToken) {
            readNewsParams.setAccessToken();
            System.out.println("Have Token");
            result = getMethod(APIPath.getReadNews(), null, readNewsParams.accessToken);
        } else {
            System.out.println("Don't have token ");
            result = getMethod(APIPath.getReadNews(), null, null);
        }
        Gson g = new Gson();

        Type response = new TypeToken<Response<ReadNewsDataType>>() {
        }.getType();
        Response<ReadNewsDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().new_id != null;
                assert rp.getData().content != null;
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

    public static void main() throws IOException {
        List<TestCase<ReadNewsParams>> listTestCase = new ArrayList<>();

        ReadNewsParams params1 = new ReadNewsParams(true, 1);
        TestCase<ReadNewsParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        ReadNewsParams params2 = new ReadNewsParams(false, 1);
        TestCase<ReadNewsParams> testCase2 = new TestCase<>("1004", "Chưa đăng nhập", "Unit test 2: Should throw 1004 because do not have token", params2);
        listTestCase.add(testCase2);

        System.out.println(getAnsiBlue() + "Testing Read News" + "API" + getAnsiReset());
        for (TestCase<ReadNewsParams> testCase : listTestCase) {
            new TestReadNewsAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class ReadNewsParams {
        String accessToken;
        boolean isToken;
        int newID;

        private ReadNewsParams(boolean isToken, int newID) {
            this.newID = newID;
            this.isToken = isToken;
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

    protected static class ReadNewsDataType {
        protected String new_id;
        protected String content;
    }


}

