import TestAPI.TestGetListAuctionsAPI;
import TestAPI.TestLoginAPI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
              TestLoginAPI.main();
//            TestGetListAuctionsAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

