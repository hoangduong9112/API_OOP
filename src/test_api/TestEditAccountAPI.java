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

public class TestEditAccountAPI extends TestBase {

    private TestEditAccountAPI(EditAccountParams editAccountParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        Map<String, String> params = new HashMap<>();
        params.put(editAccountParams.name, editAccountParams.nameValue);
        params.put(editAccountParams.email, editAccountParams.emailValue);
        params.put(editAccountParams.address, editAccountParams.addressValue);
        params.put(editAccountParams.phone, editAccountParams.phoneValue);
        String result;
        if (editAccountParams.isToken) {
            editAccountParams.setAccessToken();
            result = postMethod(APIPath.getEditAccount(), params, editAccountParams.accessToken);
        } else {
            result = postMethod(APIPath.getEditAccount(), params, null);
        }
        Gson g = new Gson();
        Type response = new TypeToken<Response<EditAccountDataType>>() {}.getType();
        Response<EditAccountDataType> rp = g.fromJson(result, response);
        System.out.println(testDescription);
        try {
            assert codeExpectation.length() <= 0 || rp.getCode().equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.getMessage().equals(messageExpectation);
            if (codeExpectation.equals("1000")) {
                assert rp.getData().name != null;
                assert rp.getData().address != null;
                assert rp.getData().phone != null;
                assert rp.getData().email != null;
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
        List<TestCase<EditAccountParams>> listTestCase = new ArrayList<>();

        final String email = "email";
        final String address = "address";
        final String name = "name";
        final String phone = "phone";

        //Test case 1 and 4 should be run independently with 1 other email

        EditAccountParams params1 = new EditAccountParams(email, "duonghoang130@gmail.com", address, "hanoi1", name, "duong1", phone, "091231", true);
        TestCase<EditAccountParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        EditAccountParams params2 = new EditAccountParams(email, "duonghoang123@gmail.com", address, "hanoi", name, "", phone, "09123", true);
        TestCase<EditAccountParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
        listTestCase.add(testCase2);

        EditAccountParams params3 = new EditAccountParams(email, "duonghoang124@gmail.com", address, "hanoi", name, "duong", phone, "", true);
        TestCase<EditAccountParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
        listTestCase.add(testCase3);

        EditAccountParams params4 = new EditAccountParams(email, "duonghoang131@gmail.com", address, "", name, "duong", phone, "09123", true);
        TestCase<EditAccountParams> testCase4 = new TestCase<>("1000", "", "Unit test 4: Should be successful with empty address", params4);
        listTestCase.add(testCase4);

        EditAccountParams params5 = new EditAccountParams(email, "", address, "hanoi", name, "duong", phone, "09123", true);
        TestCase<EditAccountParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
        listTestCase.add(testCase5);

        EditAccountParams params6 = new EditAccountParams(email, "123", address, "hanoi", name, "duong", phone, "09123", true);
        TestCase<EditAccountParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with incorrect format email", params6);
        listTestCase.add(testCase6);

        EditAccountParams params7 = new EditAccountParams(email, "duonghoang100@gmail.com", address, "hanoi", name, "duong", phone, "09123", true);
        TestCase<EditAccountParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with duplicated email", params7);
        listTestCase.add(testCase7);

        EditAccountParams params8 = new EditAccountParams(email, "duonghoang100@gmail.com", address, "hanoi", name, "duong", phone, "09123", false);
        TestCase<EditAccountParams> testCase8 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with duplicated email", params8);
        listTestCase.add(testCase8);

        System.out.println(getAnsiBlue() + "Testing Edit Account API" + getAnsiReset());

        for (TestCase<EditAccountParams> testCase : listTestCase) {
            new TestEditAccountAPI(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }

    private static class EditAccountParams {
        String email;
        String emailValue;
        String name;
        String nameValue;
        String phone;
        String phoneValue;
        String address;
        String addressValue;
        boolean isToken;

        String accessToken;

        private EditAccountParams(String email, String emailValue, String address, String addressValue, String name, String nameValue, String phone, String phoneValue, boolean isToken) {
            this.email = email;
            this.emailValue = emailValue;
            this.address = address;
            this.addressValue = addressValue;
            this.phone = phone;
            this.phoneValue = phoneValue;
            this.name = name;
            this.nameValue = nameValue;

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

    protected static class EditAccountDataType{
        protected String name;
        protected String email;
        protected String phone;
        protected String address;
        protected String role;
    }
}