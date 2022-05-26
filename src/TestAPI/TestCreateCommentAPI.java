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

public class TestCreateCommentAPI {

    private TestCreateCommentAPI(CreateCommentParams createCommentParams, String testDescription, String codeExpectation, String messageExpectation) throws
            IOException {
        APIPath.setCreateComment(createCommentParams.auctionID);
        URL url = new URL(APIPath.getCreateComment());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        createCommentParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + createCommentParams.accessToken);


        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put(createCommentParams.content, createCommentParams.contentValue);
        params.put(createCommentParams.commentLastID, createCommentParams.commentLastIDValue);
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
        List<TestCase<CreateCommentParams>> listTestCase = new ArrayList<>();
        final String content = "content";
        final String comment_last_id = "comment_last_id";


        CreateCommentParams params1 = new CreateCommentParams(1, content, "ABCDEF", comment_last_id, "39");
        TestCase<CreateCommentParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct params", params1);
        listTestCase.add(testCase1);

        CreateCommentParams params2 = new CreateCommentParams(1, content, "", comment_last_id, "");
        TestCase<CreateCommentParams> testCase2 = new TestCase<>("1001", "", "Unit test 2: Should throw error 1001 with empty content", params2);
        listTestCase.add(testCase2);

        // data have code 1008
        CreateCommentParams params3 = new CreateCommentParams(2, content, "1234", comment_last_id, "6");
        TestCase<CreateCommentParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct params", params3);
        listTestCase.add(testCase3);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Create Comment API" + ColorTerminal.ANSI_RESET);

        for (TestCase<CreateCommentParams> testCase : listTestCase) {
            new TestCreateCommentAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class CreateCommentParams {
        int auctionID;
        String content;
        String contentValue;
        String commentLastID;
        String commentLastIDValue;
        String accessToken;

        private CreateCommentParams(int auctionID, String content, String contentValue, String commentLastID, String commentLastIDValue) {
            this.auctionID = auctionID;
            this.content = content;
            this.contentValue = contentValue;
            this.commentLastID = commentLastID;
            this.commentLastIDValue = commentLastIDValue;
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