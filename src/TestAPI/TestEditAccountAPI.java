package TestAPI;

import Utils.API.LoginAPI;
import Utils.APIPath;
import Utils.ColorTerminal;
import Utils.Response;
import Utils.TestCase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEditAccountAPI {

    private TestEditAccountAPI(EditAccountParams editAccountParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.EDIT_ACCOUNT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        editAccountParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + editAccountParams.accessToken);

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(editAccountParams.key1, editAccountParams.value1);
        params.put(editAccountParams.key2, editAccountParams.value2);
        params.put(editAccountParams.key3, editAccountParams.value3);
        params.put(editAccountParams.key4, editAccountParams.value4);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
        }

        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);
            writer.flush();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            Gson g = new Gson();
            Response rp = g.fromJson(content.toString(), Response.class);

            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);
            System.out.println(ColorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
            System.out.println();
        } finally {
            connection.disconnect();
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

//        EditAccountParams params1 = new EditAccountParams(email, "duonghoang120@gmail.com", address, "hanoi1", name, "duong1", phone, "091231");
//        TestCase<EditAccountParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
//        listTestCase.add(testCase1);

        EditAccountParams params2 = new EditAccountParams(email, "duonghoang123@gmail.com", address, "hanoi", name, "", phone, "09123");
        TestCase<EditAccountParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
        listTestCase.add(testCase2);

        EditAccountParams params3 = new EditAccountParams(email, "duonghoang124@gmail.com", address, "hanoi", name, "duong", phone, "");
        TestCase<EditAccountParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
        listTestCase.add(testCase3);

//        EditAccountParams params4 = new EditAccountParams(email, "duonghoang123@gmail.com", address, "", name, "duong", phone, "09123");
//        TestCase<EditAccountParams> testCase4 = new TestCase<>("1000", "", "Unit test 4: Should be successful with empty address", params4);
//        listTestCase.add(testCase4);

        EditAccountParams params5 = new EditAccountParams(email, "", address, "hanoi", name, "duong", phone, "09123");
        TestCase<EditAccountParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
        listTestCase.add(testCase5);

        EditAccountParams params6 = new EditAccountParams(email, "123", address, "hanoi", name, "duong", phone, "09123");
        TestCase<EditAccountParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with incorrect format email", params6);
        listTestCase.add(testCase6);

        EditAccountParams params7 = new EditAccountParams(email, "duonghoang100@gmail.com", address, "hanoi", name, "duong", phone, "09123");
        TestCase<EditAccountParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with duplicated email", params7);
        listTestCase.add(testCase7);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Edit Account API" + ColorTerminal.ANSI_RESET);

        for (TestCase<EditAccountParams> testCase : listTestCase) {
            new TestEditAccountAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class EditAccountParams {
        String key1;
        String value1;
        String key2;
        String value2;
        String key3;
        String value3;
        String key4;
        String value4;
        String accessToken;

        private EditAccountParams(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4) {
            this.key1 = key1;
            this.value1 = value1;
            this.key2 = key2;
            this.value2 = value2;
            this.key3 = key3;
            this.value3 = value3;
            this.key4 = key4;
            this.value4 = value4;

        }

        public void setAccessToken() {
            String accessToken;
            try {
                accessToken = LoginAPI.call();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.accessToken = accessToken;
        }


    }

    public static class TestChangePasswordAPI {

        private TestChangePasswordAPI(ChangePasswordParams changePasswordParams, String testDescription, String codeExpectation, String messageExpectation) throws
                IOException {
            URL url = new URL(APIPath.CHANGE_PASS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (!changePasswordParams.accessToken.equals("")) {
                connection.addRequestProperty("Authorization", "Bearer " + changePasswordParams.accessToken);
            }

            connection.setRequestMethod("POST");
            Map<String, String> params = new HashMap<>();
            params.put(changePasswordParams.key1, changePasswordParams.value1);
            params.put(changePasswordParams.key2, changePasswordParams.value2);
            params.put(changePasswordParams.key3, changePasswordParams.value3);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
            }

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
            connection.setDoOutput(true);
            try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                writer.write(postDataBytes);
                writer.flush();
                StringBuilder content;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    content = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        content.append(line);
                        content.append(System.lineSeparator());
                    }
                }

                Gson g = new Gson();
                Response rp = g.fromJson(content.toString(), Response.class);

                System.out.println(testDescription);
                assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
                assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);
                System.out.println(ColorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
                System.out.println();
            } finally {
                connection.disconnect();
            }
        }

        public static void main() throws
                IOException {
            List<TestCase<ChangePasswordParams>> listTestCase = new ArrayList<>();

            final String oldPass = "old_pass";
            final String newPass = "new_pass";
            final String rePass = "re_pass";

            ChangePasswordParams params1 = new ChangePasswordParams(oldPass, "1234", newPass, "1234", rePass, "1234");
            TestCase<ChangePasswordParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
            listTestCase.add(testCase1);

            ChangePasswordParams params2 = new ChangePasswordParams(oldPass, "1234", newPass, "12345", rePass, "");
            TestCase<ChangePasswordParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty re_pass", params2);
            listTestCase.add(testCase2);

            ChangePasswordParams params3 = new ChangePasswordParams(oldPass, "123456", newPass, "123456", rePass, "123456");
            TestCase<ChangePasswordParams> testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with correct oldPass", params3);
            listTestCase.add(testCase3);

            ChangePasswordParams params4 = new ChangePasswordParams(oldPass, "1234", newPass, "", rePass, "0987654321");
            TestCase<ChangePasswordParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty new_pass", params4);
            listTestCase.add(testCase4);

            ChangePasswordParams params5 = new ChangePasswordParams(oldPass, "1234", newPass, "0987654321", rePass, "abcd");
            TestCase<ChangePasswordParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with re_pass is not similar to new_pass", params5);
            listTestCase.add(testCase5);

            ChangePasswordParams params6 = new ChangePasswordParams(oldPass, "", newPass, "0987654321", rePass, "0987654321");
            TestCase<ChangePasswordParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty old_pass", params6);
            listTestCase.add(testCase6);

            System.out.println(ColorTerminal.ANSI_BLUE + "Testing Change Password API" + ColorTerminal.ANSI_RESET);

            for (TestCase<ChangePasswordParams>testCase : listTestCase) {
                new TestChangePasswordAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
            }
        }

        private static class ChangePasswordParams{
            String key1;
            String value1;
            String key2;
            String value2;
            String key3;
            String value3;

            String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hdWN0aW9ucy1hcHAtMi5oZXJva3VhcHAuY29tXC9hcGlcL2xvZ2luIiwiaWF0IjoxNjUzNDA1MjIyLCJleHAiOjE2NTM3NjUyMjIsIm5iZiI6MTY1MzQwNTIyMiwianRpIjoiT05XZ3pNZ2VmS0MyQXU2OSIsInN1YiI6MjYsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.CAYRjUHSDxnCH1_rgrmR2-DqysA4h9G2wbBF6-4gnLc";

            private ChangePasswordParams(String key1, String value1, String key2, String value2, String key3, String value3) {
                this.key1 = key1;
                this.value1 = value1;
                this.key2 = key2;
                this.value2 = value2;
                this.key3 = key3;
                this.value3 = value3;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }
        }

    }
}