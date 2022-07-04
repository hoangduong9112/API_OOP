package test_api;

import com.google.gson.Gson;
import utils.APIPath;
import utils.ResponseDeprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestLogOutAPI extends TestBase {

    private TestLogOutAPI(LogOutParams logOutParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        logOutParams.setAccessToken();
        String result = postMethod(APIPath.getLogOut(), null, logOutParams.access_token);
        Gson g = new Gson();
        ResponseDeprecated rp = g.fromJson(result, ResponseDeprecated.class);
        System.out.println(testDescription);

        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
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
        List<TestCase<LogOutParams>> listTestCase = new ArrayList<>();

        LogOutParams params1 = new LogOutParams(true);
        TestCase<LogOutParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        LogOutParams params2 = new LogOutParams(false);
        TestCase<LogOutParams> testCase2 = new TestCase<>("1004", "まだログインではありません", "Unit test 2: Should throw 1004 without token", params2);
        listTestCase.add(testCase2);

        System.out.println(getAnsiBlue() + "Testing LogOut API" + getAnsiReset());

        for (TestCase<LogOutParams> testCase : listTestCase) {
            new TestLogOutAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class LogOutParams {
        boolean isToken;
        String access_token;


        private LogOutParams(boolean isToken) {
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

    protected static class LogOutDataType {
        protected String message;
    }
}