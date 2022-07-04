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

public class TestSignUpAPI extends TestBase {

    private TestSignUpAPI(SignUpParams signUpParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        Map<String, String> params = new HashMap<>();
        params.put(signUpParams.key1, signUpParams.value1);
        params.put(signUpParams.key2, signUpParams.value2);
        params.put(signUpParams.key3, signUpParams.value3);
        params.put(signUpParams.key4, signUpParams.value4);
        params.put(signUpParams.key5, signUpParams.value5);
        params.put(signUpParams.key6, signUpParams.value6);

        String result = postMethod(APIPath.getSignUp(), params, null);
        Gson g = new Gson();
        Type response = new TypeToken<Response<SignUpDataType>>() {}.getType();
        Response<SignUpDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);

        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().name != null;
                assert rp.getData().phone != null;
                assert rp.getData().email != null;
                assert rp.getData().avatar != null;
                assert rp.getData().role == 2;
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
        List<TestCase<SignUpParams>> listTestCase = new ArrayList<>();

        final String email = "email";
        final String password = "password";
        final String re_pass = "re_pass";
        final String address = "address";
        final String name = "name";
        final String phone = "phone";

        //Need to update new email to run test

        SignUpParams params1 = new SignUpParams(email, "duonghoang326@gmail.com", password, "123456", re_pass, "123456", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        SignUpParams params2 = new SignUpParams(email, "duonghoang123@gmail.com", password, "123456", re_pass, "123456", address, "hanoi", name, "", phone, "09123");
        TestCase<SignUpParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
        listTestCase.add(testCase2);

        SignUpParams params3 = new SignUpParams(email, "duonghoang124@gmail.com", password, "123456", re_pass, "123456", address, "hanoi", name, "duong", phone, "");
        TestCase<SignUpParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
        listTestCase.add(testCase3);

        SignUpParams params4 = new SignUpParams(email, "duonghoang327@gmail.com", password, "123456", re_pass, "123456", address, "", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with empty address", params4);
        listTestCase.add(testCase4);

        SignUpParams params5 = new SignUpParams(email, "", password, "123456", re_pass, "123456", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
        listTestCase.add(testCase5);

        SignUpParams params6 = new SignUpParams(email, "duonghoang125@gmail.com", password, "", re_pass, "123456", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty password", params6);
        listTestCase.add(testCase6);

        SignUpParams params7 = new SignUpParams(email, "123", password, "123456", re_pass, "123456", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with incorrect format email", params7);
        listTestCase.add(testCase7);

        SignUpParams params8 = new SignUpParams(email, "duonghoang100@gmail.com", password, "123456", re_pass, "123456", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase8 = new TestCase<>("1001", "", "Unit test 8: Should throw error 1001 with duplicated email", params8);
        listTestCase.add(testCase8);

        SignUpParams params9 = new SignUpParams(email, "duonghoang14@gmail.com", password, "123456", re_pass, "", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase9 = new TestCase<>("1001", "", "Unit test 9: Should throw error 1001 with empty repass", params9);
        listTestCase.add(testCase9);

        SignUpParams params10 = new SignUpParams(email, "duonghoang15@gmail.com", password, "123456", re_pass, "123", address, "hanoi", name, "duong", phone, "09123");
        TestCase<SignUpParams> testCase10 = new TestCase<>("1001", "", "Unit test 10: Should throw error 1001 with wrong repass", params10);
        listTestCase.add(testCase10);


        System.out.println(getAnsiBlue() + "Testing SignUp API" + getAnsiReset());

        for (TestCase<SignUpParams> testCase : listTestCase) {
            new TestSignUpAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class SignUpParams {
        String key1;
        String value1;
        String key2;
        String value2;
        String key3;
        String value3;
        String key4;
        String value4;
        String key5;
        String value5;
        String key6;
        String value6;

        private SignUpParams(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5, String key6, String value6) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
            this.key4 = key4;
            this.value4 = value4;
            this.key5 = key5;
            this.value5 = value5;
            this.key6 = key6;
            this.value6 = value6;
        }
    }

    protected static class SignUpDataType {
        protected String name;
        protected String email;
        protected String phone;
        protected String avatar;
        protected int role;
    }

}