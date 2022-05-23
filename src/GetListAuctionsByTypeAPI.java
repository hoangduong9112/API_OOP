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

public class GetListAuctionsByTypeAPI {

    private GetListAuctionsByTypeAPI(APIParams APIParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put(APIParams.index, APIParams.indexValue);
        params.put(APIParams.count, APIParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListAuctionsByType(APIParams.typeID, APIParams.statusID);
        URL url = new URL(APIPath.getGetListAuctionsByType() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (!APIParams.access_token_value.equals("")) {
            connection.addRequestProperty("Authorization", "Bearer " + APIParams.access_token_value);
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
        final String index = "index";
        final String count = "count";
        List<TestCase<APIParams>> listTestCase = new ArrayList<>();

        APIParams params1 = new APIParams(2, 0, index, "1", count, "2");
        TestCase<APIParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get List Auctions By Type API" + ColorTerminal.ANSI_RESET);

        for (TestCase<APIParams> testCase : listTestCase) {
            new GetListAuctionsByTypeAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class APIParams {
        int typeID;
        int statusID;
        String access_token_value = "";
        String index;
        String indexValue;
        String count;
        String countValue;

        private APIParams(int typeID, int statusID, String index, String indexValue, String count, String countValue) {
            this.typeID = typeID;
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
        }

        //this func can use when user login success
        public void setAccess_token_value(String access_token_value) {
            this.access_token_value = access_token_value;
        }
    }

}
