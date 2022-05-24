//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class CreateAuctionAPI {
//
//    private CreateAuctionAPI(CreateAuctionParams createAuctionParamsParams, String testDescription, String codeExpectation, String messageExpectation) throws
//            IOException {
//        URL url = new URL(APIPath.CREAT);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setRequestMethod("POST");
//        Map<String, String> params = new HashMap<>();
//        params.put(CreateAuctionParams.key1, CreateAuctionParams.value1);
//        params.put(CreateAuctionParams.key2, CreateAuctionParams.value2);
//        params.put(CreateAuctionParams.key3, CreateAuctionParams.value3);
//        params.put(CreateAuctionParams.key4, CreateAuctionParams.value4);
//        params.put(CreateAuctionParams.key5, CreateAuctionParams.value5);
//        StringBuilder postData = new StringBuilder();
//        for (Map.Entry<String, String> param : params.entrySet()) {
//            if (postData.length() != 0) {
//                postData.append('&');
//            }
//            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
//            postData.append('=');
//            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
//        }
//
//        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
//        connection.setDoOutput(true);
//        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
//            writer.write(postDataBytes);
//            writer.flush();
//            StringBuilder content;
//            try (BufferedReader in = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()))) {
//                String line;
//                content = new StringBuilder();
//                while ((line = in.readLine()) != null) {
//                    content.append(line);
//                    content.append(System.lineSeparator());
//                }
//            }
//
//            Gson g = new Gson();
//            Response rp = g.fromJson(content.toString(), Response.class);
//
//            System.out.println(testDescription);
//            assert codeExpectation.length() <= 0 || rp.code.equals(codeExpectation);
//            assert messageExpectation.length() <= 0 || rp.message.equals(messageExpectation);
//            System.out.println(ColorTerminal.ANSI_GREEN + "Pass" + ColorTerminal.ANSI_RESET);
//            System.out.println();
//        } finally {
//            connection.disconnect();
//        }
//    }
//
//    public static void main() throws
//            IOException {
//        List<TestCase<CreateAuctionParams>> listTestCase = new ArrayList<>();
//
//        //Need to update new email to run test
//
//        CreateAuctionParams params1 = new CreateAuctionParams("", "duonghoang120@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase<CreateAuctionParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
//        listTestCase.add(testCase1);
//
//        CreateAuctionParams params2 = new CreateAuctionParams("email", "duonghoang123@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "", "phone", "09123");
//        TestCase<CreateAuctionParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty name", params2);
//        listTestCase.add(testCase2);
//
//        CreateAuctionParams params3 = new CreateAuctionParams("email", "duonghoang124@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "");
//        TestCase< CreateAuctionParams > testCase3 = new TestCase<>("1001", "", "Unit test 3: Should throw error 1001 with empty phone", params3);
//        listTestCase.add(testCase3);
//
//        CreateAuctionParams  params4 = new  CreateAuctionParams ("email", "duonghoang103@gmail.com", "password", "123456", "re_pass", "123456", "address", "", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase4 = new TestCase<>("1001", "", "Unit test 4: Should throw error 1001 with empty address", params4);
//        listTestCase.add(testCase4);
//
//        CreateAuctionParams  params5 = new  CreateAuctionParams ("email", "", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase5 = new TestCase<>("1001", "", "Unit test 5: Should throw error 1001 with empty email", params5);
//        listTestCase.add(testCase5);
//
//        CreateAuctionParams  params6 = new  CreateAuctionParams ("email", "duonghoang125@gmail.com", "password", "", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase6 = new TestCase<>("1001", "", "Unit test 6: Should throw error 1001 with empty password", params6);
//        listTestCase.add(testCase6);
//
//        CreateAuctionParams  params7 = new  CreateAuctionParams ("email", "123", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase<CreateAuctionParams> testCase7 = new TestCase<>("1001", "", "Unit test 7: Should throw error 1001 with incorrect format email", params7);
//        listTestCase.add(testCase7);
//
//        CreateAuctionParams  params8 = new  CreateAuctionParams ("email", "duonghoang100@gmail.com", "password", "123456", "re_pass", "123456", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase8 = new TestCase<>("1001", "", "Unit test 8: Should throw error 1001 with duplicated email", params8);
//        listTestCase.add(testCase8);
//
//        CreateAuctionParams  params9 = new  CreateAuctionParams ("email", "duonghoang14@gmail.com", "password", "123456", "re_pass", "", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase9 = new TestCase<>("1001", "", "Unit test 9: Should throw error 1001 with empty repass", params9);
//        listTestCase.add(testCase9);
//
//        CreateAuctionParams params10 = new  CreateAuctionParams ("email", "duonghoang15@gmail.com", "password", "123456", "re_pass", "123", "address", "hanoi", "name", "duong", "phone", "09123");
//        TestCase< CreateAuctionParams > testCase10 = new TestCase<>("1001", "", "Unit test 9: Should throw error 1001 with wrong repass", params10);
//        listTestCase.add(testCase10);
//
//
//        System.out.println(ColorTerminal.ANSI_BLUE + "Testing SignUp API" + ColorTerminal.ANSI_RESET);
//
//        for (TestCase< CreateAuctionParams > testCase : listTestCase) {
//            new CreateAuctionAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
//        }
//    }
//
//    private static class CreateAuctionParams {
//        SimpleDateFormat F = new SimpleDateFormat();
//        public static String key1;
//        public static String value1;
//        public static String key2 ;
//        public static String value2;
//        public static String key3;
//        public static String value3;
//        public static String key4;
//        public static String value4;
//        public static String key5;
//        public static String value5;
//
//        private  CreateAuctionParams(String key1, String value1, int key2, int value2, Date key3, Date value3, Date key4, Date value4, String key5, String value5) {
//            this.key1 = key1;
//            this.value1 = value1;
//            this.key2 = String.valueOf(key2);
//            this.value2 = String.valueOf(value2);
//            this.key3 = key3;
//            this.value3 = value3;
//            this.key4 = key4;
//            this.value4 = value4;
//            this.key5 = key5;
//            this.value5 = value5;
//        }
//    }
//
//}