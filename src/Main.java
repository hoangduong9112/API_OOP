import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws MalformedURLException, ProtocolException {
        try {
            LoginAPI.LoginAPI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

