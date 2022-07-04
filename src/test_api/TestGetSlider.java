package test_api;


import com.google.gson.Gson;
import utils.APIPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestGetSlider extends TestBase {
    private TestGetSlider(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        URL url = new URL(APIPath.GET_SLIDER);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (itemParams.token) {
            itemParams.setAccessToken();
            System.out.println("Have token");
            connection.addRequestProperty("Authorization", "Bearer " + itemParams.accessToken);
        } else {
            System.out.println("Don't have token");
        }

        connection.setRequestMethod("GET");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            System.out.println(content);
            Gson g = new Gson();
            Response rp = g.fromJson(content.toString(), Response.class);
            System.out.println(testDescription);
            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);

            System.out.println(getAnsiGreen() + "Pass" + getAnsiReset());
            System.out.println();
            System.out.println(connection.getResponseCode());
        } finally {
            connection.disconnect();
        }
    }

    public static void main() throws
            IOException {
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();

        ItemParams params1 = new ItemParams( true);
        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with token", params1);
        listTestCase.add(testCase1);

        ItemParams params2 = new ItemParams(true);
        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with token", params2);
        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams(true);
        TestCase<ItemParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with token", params3);
        listTestCase.add(testCase3);

        ItemParams params4 = new ItemParams(false);
        TestCase<ItemParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with token", params4);
        listTestCase.add(testCase4);

        ItemParams params5 = new ItemParams(false);
        TestCase<ItemParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should be successful with token", params5);
        listTestCase.add(testCase5);

        ItemParams params6 = new ItemParams(false);
        TestCase<ItemParams> testCase6 = new TestCase<>("1000", "OK", "Unit test 6: Should be successful with token", params6);
        listTestCase.add(testCase6);
//
        System.out.println(getAnsiBlue() + "Testing Get Slider API" + getAnsiReset());
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestGetSlider(testCase.params(), testCase.testDescription(), testCase.codeExpectation(), testCase.messageExpectation());
        }
    }
    private static class ItemParams {
        String accessToken;
        boolean token;

        private ItemParams(boolean token) {
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
}
