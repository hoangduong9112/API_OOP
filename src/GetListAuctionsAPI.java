import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetListAuctionsAPI {
    private GetListAuctionsAPI(GetListAuctionsParams getListAuctionsParams, String testDescription, String codeExpectation, String messageExpectation) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(getListAuctionsParams.index, getListAuctionsParams.indexValue);
        params.put(getListAuctionsParams.count, getListAuctionsParams.countValue);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (query.length() != 0) {
                query.append('&');
            }
            query.append(param.getKey());
            query.append('=');
            query.append(param.getValue());
        }
        APIPath.setGetListAuctions(getListAuctionsParams.statusID);
        URL url = new URL(APIPath.getGetListAuctions() + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (!getListAuctionsParams.access_token_value.equals("")) {
            connection.addRequestProperty("Authorization", "Bearer " + getListAuctionsParams.access_token_value);
        }
        connection.setRequestMethod("GET");
        if(!getListAuctionsParams.remainder.isEmpty()){
            connection.addRequestProperty("" + getListAuctionsParams.remainder, "" + getListAuctionsParams.remainderValue);
        }
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
    public static void main() throws IOException{
        List<TestCase<GetListAuctionsParams>> listTestCase = new ArrayList<>();

        GetListAuctionsParams params1 = new GetListAuctionsParams(1, "index", "1", "count", "1");
        TestCase<GetListAuctionsParams> testCase1 = new TestCase<>("1000", "OK", "Unit test 1: Should be successful with correct param", params1);
        listTestCase.add(testCase1);

        GetListAuctionsParams params2 = new GetListAuctionsParams(1, "index", "1", "count", "1", "user_id", "3" );
        TestCase<GetListAuctionsParams> testCase2 = new TestCase<>("1000", "OK", "Unit test 2: Should be successful with correct param", params2);
        listTestCase.add(testCase2);

        GetListAuctionsParams params3 = new GetListAuctionsParams(1, "index", "1", "count", "1", "category_id", "1" );
        TestCase<GetListAuctionsParams> testCase3 = new TestCase<>("1000", "OK", "Unit test 3: Should be successful with correct param", params3);
        listTestCase.add(testCase3);

        GetListAuctionsParams params4 = new GetListAuctionsParams(1, "index", "1", "count", "1", "type", "1" );
        TestCase<GetListAuctionsParams> testCase4 = new TestCase<>("1000", "OK", "Unit test 4: Should be successful with correct param", params4);
        listTestCase.add(testCase4);

        GetListAuctionsParams params5 = new GetListAuctionsParams(1, "index", "", "count", "");
        TestCase<GetListAuctionsParams> testCase5 = new TestCase<>("1000", "OK", "Unit test 5: Should be successful with correct param", params5);
        listTestCase.add(testCase5);

        GetListAuctionsParams params6 = new GetListAuctionsParams(1, "index", "", "count", "");
        TestCase<GetListAuctionsParams> testCase6 = new TestCase<>("1001", "Lỗi khi validate", "Unit test 6: Should be successful with correct param", params6);
        listTestCase.add(testCase6);

        GetListAuctionsParams params7 = new GetListAuctionsParams(1, "index", "", "count", "","user_id","");
        TestCase<GetListAuctionsParams> testCase7 = new TestCase<>("1001", "Lỗi khi validate", "Unit test 7: Should be successful with correct param", params7);
        listTestCase.add(testCase7);

        GetListAuctionsParams params8 = new GetListAuctionsParams(1, "index", "", "count", "");
        TestCase<GetListAuctionsParams> testCase8 = new TestCase<>("1001", "Lỗi khi validate", "Unit test 8: Should be successful with correct param", params8);
        listTestCase.add(testCase8);

        System.out.println(ColorTerminal.ANSI_BLUE + "Testing Get List Auction API" + ColorTerminal.ANSI_RESET);
        for (TestCase<GetListAuctionsParams> testCase : listTestCase) {
            new GetListAuctionsAPI(testCase.getParams(), testCase.getTestDescription(), testCase.getCodeExpectation(), testCase.getMessageExpectation());
        }
    }

    private static class GetListAuctionsParams {
        int statusID;
        String access_token_value = "";
        String index;
        String indexValue;
        String count;
        String countValue;
        String remainder = "";
        String remainderValue;
        private GetListAuctionsParams(int statusID, String index, String indexValue, String count, String countValue) {
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
        }
        private GetListAuctionsParams(int statusID, String index, String indexValue, String count, String countValue, String remainder, String remainderValue) {
            this.statusID = statusID;
            this.index = index;
            this.indexValue = indexValue;
            this.count = count;
            this.countValue = countValue;
            this.remainder = remainder;
            this.remainderValue = remainderValue;
        }
        public void setAccess_token_value(String access_token_value) {
            this.access_token_value = access_token_value;
        }
    }

}

