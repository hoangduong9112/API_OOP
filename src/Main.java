import TestAPI.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
//            TestCreateItemAPI.main(); // need update test case
            TestEditItemAPI.main();
//            TestInfoItemAPI.main();
//            TestLoginAPI.main();
//            TestGetListAuctionsAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case
//            TestCreateBidAPI.main(); // need update test case
//            TestCreateCommentAPI.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

