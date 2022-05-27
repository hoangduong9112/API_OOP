package TestAPI;

import Utils.API.LoginAPI;
import Utils.APIPath;
import Utils.ColorTerminal;
import Utils.Response;
import Utils.TestCase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGetListLikesAPI {
    private TestGetListLikesAPI(GetListLikesParams getListLikesParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(getListLikesParams.index, getListLikesParams.indexValue);
        params.put(getListLikesParams.count, getListLikesParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListLikes(getListLikesParams.statusID);
        URL url = new URL(APIPath.getGetListLikes() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        getListLikesParams.setAccessToken();
        connection.addRequestProperty("Authorization", "Bearer " + getListLikesParams.accessToken);


        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
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

    public static void main() throws IOException {
        List<TestCase<GetListLikesParams>> listTestCase = new ArrayList<>();

        GetListLikesParams params1 = new GetListLikesParams(true, 3, "index", "1", "count", "1");
        TestCase<GetListLikesParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);
        /*GetListLikesParams params2 = new GetListLikesParams(false, 3, "index", "1", "count", "1");
        TestCase<GetListLikesParams> testCase2 = new TestCase<>("1000", "", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);*/
        GetListLikesParams params3 = new GetListLikesParams(true, 3, "index", "", "count", "1");
        TestCase<GetListLikesParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with empty index", params3);
        listTestCase.add(testCase3);
        GetListLikesParams params4 = new GetListLikesParams(true, 3, "index", "1", "count", "");
        TestCase<GetListLikesParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with empty count", params4);
        listTestCase.add(testCase4);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get List Likes" + "API" + ColorTerminal.ANSI_RESET);
        for (TestCase<GetListLikesParams> testCase : listTestCase) {
            new TestGetListLikesAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class GetListLikesParams {
        String accessToken;
        boolean isToken;
        private GetListLikesParams(boolean isToken, int statusID, String index, String indexValue, String count, String countValue) {
            this.isToken = isToken;
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
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

