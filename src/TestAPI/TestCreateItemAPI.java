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

public class TestCreateItemAPI {

    private TestCreateItemAPI(ItemParams itemParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        itemParams.setAccessToken();
        APIPath.setCreateItem(itemParams.auctionID);
        URL url = new URL(APIPath.getCreateItem());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        if (itemParams.token) {
            itemParams.setAccessToken();
            connection.addRequestProperty("Authorization", "Bearer " + itemParams.accessToken);
        }

        Map<String, String> params = new HashMap<>();
        params.put("name", itemParams.name);
        params.put("starting_price", String.valueOf(itemParams.startingPrice));
        params.put("brand_id", String.valueOf(itemParams.brandID));
        params.put("description", itemParams.description);
        if (itemParams.series != null) {
            params.put("series", itemParams.series);
        }
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
            if (connection.getResponseCode() == 302) {
                url = new URL(APIPath.loginFailed);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
            }
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
        List<TestCase<ItemParams>> listTestCase = new ArrayList<>();
//            Testcase 1, 2, 4, 5, 6, 7, 8 need to update
//        ItemParams params1 = new ItemParams(8,"Rick roll", "123456", "3", "Never gonna let you down",true);
//        TestCase<ItemParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
//        listTestCase.add(testCase1);

//        ItemParams params2 = new ItemParams(3, "hello world", "654321", "24","test thui", "123456",true );
//        TestCase<ItemParams> testCase2 = new TestCase<>("1000", "", "Unit test 2: Should be successful with correct param (have series)", params2);
//        listTestCase.add(testCase2);

        ItemParams params3 = new ItemParams(5, "Test without login", "123123", "8", "Nice", "12345", false);
        TestCase<ItemParams> testCase3 = new TestCase<>("1004", "", "Unit test 3: Should throw error 1004 because user haven't login  ", params3);
        listTestCase.add(testCase3);

//        ItemParams params4 = new ItemParams(8, "Test with brandID empty", "123123", "", "Nice","12345",true);
//        TestCase<ItemParams> testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty brandID", params4);
//        listTestCase.add(testCase4);
//
//        ItemParams params5 = new ItemParams(8, "", "123123", "8", "Nice","12345", true);
//        TestCase<ItemParams> testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty name", params5);
//        listTestCase.add(testCase5);
//
//        ItemParams params6 = new ItemParams(8,"Test with empty startingPrice", "", "8", "Do uong ngon", true);
//        TestCase<ItemParams> testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty startingPrice", params6);
//        listTestCase.add(testCase6);
//
//        ItemParams params7 = new ItemParams(8,"Test with empty description", "12345", "8", "", true);
//        TestCase<ItemParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with empty description", params7);
//        listTestCase.add(testCase7);
//
//        ItemParams params8 = new ItemParams(8,"Test with long series", "12345", "8", "Helooo", "1234534413231", true);
//        TestCase<ItemParams> testCase8 = new TestCase<>("1001", "", "Unit test 8: Should throw error 1001 with series longer than 10", params8);
//        listTestCase.add(testCase8);

        ItemParams params9 = new ItemParams(5, "Test with invalid ID", "12345", "4", "Helooo", "12345", true);
        TestCase<ItemParams> testCase9 = new TestCase<>("9996", "", "Unit test 9: Should throw error 9996 with invalid auction id", params9);
        listTestCase.add(testCase9);

        ItemParams params10 = new ItemParams(5, "Test add item into room full", "12345", "4", "Helooo", "12345", true);
        TestCase<ItemParams> testCase10 = new TestCase<>("9995", "", "Unit test 10: Should throw error 9995 because room fulled", params10);
        listTestCase.add(testCase10);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Create Item API" + ColorTerminal.ANSI_RESET);
//
        for (TestCase<ItemParams> testCase : listTestCase) {
            new TestCreateItemAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class ItemParams {
        String accessToken;
        int auctionID;
        String name;
        String startingPrice;
        String brandID;
        String description;
        String series = null;
        String[] images;
        boolean token;


        private ItemParams(int auctionID, String name, String startingPrice, String brandID, String description, boolean token) {
            this.auctionID = auctionID;
            this.name = name;
            this.startingPrice = startingPrice;
            this.brandID = brandID;
            this.description = description;
            this.token = token;
        }

        private ItemParams(int auctionID, String name, String startingPrice, String brandID, String description, String series, boolean token) {
            this.auctionID = auctionID;
            this.name = name;
            this.startingPrice = startingPrice;
            this.brandID = brandID;
            this.description = description;
            this.series = series;
            this.token = token;
        }

        private ItemParams(int auctionID, String name, String startingPrice, String brandID, String description, String[] images, boolean token) {
            this.auctionID = auctionID;
            this.name = name;
            this.startingPrice = startingPrice;
            this.brandID = brandID;
            this.description = description;
            this.images = images;
            this.token = token;
        }

        private ItemParams(int auctionID, String name, String startingPrice, String brandID, String description, String series, String[] images, boolean token) {
            this.auctionID = auctionID;
            this.name = name;
            this.startingPrice = startingPrice;
            this.brandID = brandID;
            this.description = description;
            this.series = series;
            this.images = images;
            this.token = token;
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

}


