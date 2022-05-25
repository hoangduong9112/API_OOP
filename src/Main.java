import TestAPI.TestEditAccountAPI;
import TestAPI.TestLoginAPI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            TestLoginAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

