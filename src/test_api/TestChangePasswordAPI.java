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

public class TestChangePasswordAPI extends TestBase {
    public TestChangePasswordAPI(ChangePasswordParams params, String testDescription, String codeExpectation, String messageExpectation) {
    }

    private static void test(ChangePasswordParams changePasswordParams, String testDescription, String codeExpectation, String messageExpectation) throws
                IOException {
            Map<String, String> params = new HashMap<>();
            params.put(changePasswordParams.oldPass, changePasswordParams.oldPassValue);
            params.put(changePasswordParams.newPass, changePasswordParams.newPassValue);
            params.put(changePasswordParams.rePass, changePasswordParams.rePassValue);

            String result ;
            if (changePasswordParams.isToken) {
                changePasswordParams.setAccessToken();
                result = postMethod(APIPath.getChangePass(), params, changePasswordParams.accessToken);
            } else {
                result = postMethod(APIPath.getChangePass(), params, null);
            }
            Gson g = new Gson();
            Type response = new TypeToken<Response<ChangePassDataType>>() {}.getType();
            Response<ChangePassDataType> rp = g.fromJson(result, response);
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
            List<TestCase<ChangePasswordParams>> listTestCase = new ArrayList<>();

            final String oldPass = "old_pass";
            final String newPass = "new_pass";
            final String rePass = "re_pass";

            ChangePasswordParams params1 = new ChangePasswordParams(oldPass, "123456", newPass, "123456", rePass, "123456", true);
            TestCase<ChangePasswordParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
            listTestCase.add(testCase1);

            ChangePasswordParams params2 = new ChangePasswordParams(oldPass, "123456", newPass, "12345", rePass, "", true);
            TestCase<ChangePasswordParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty re_pass", params2);
            listTestCase.add(testCase2);

            System.out.println(getAnsiBlue() + "Testing ChangePassword API" + getAnsiReset());

            for (TestCase<ChangePasswordParams> testCase : listTestCase) {
                new TestChangePasswordAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
            }
        }

        private static class ChangePasswordParams {
            String oldPass;
            String oldPassValue;
            String newPass;
            String newPassValue;
            String rePass;
            String rePassValue;
            boolean isToken;

            String accessToken;

            private ChangePasswordParams(String oldPass, String oldPassValue, String newPass, String newPassValue, String rePass, String rePassValue, boolean isToken) {
                this.oldPass = oldPass;
                this.oldPassValue = oldPassValue;
                this.newPass = newPass;
                this.newPassValue = newPassValue;
                this.rePass = rePass;
                this.rePassValue = rePassValue;
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

    protected static class ChangePassDataType{
//        protected String access_token;
        protected String message;
    }
    }

