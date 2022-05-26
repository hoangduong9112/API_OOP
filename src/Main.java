import TestAPI.TestLoginAPI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {

//            TestAPI.GetListAuctionsByTypeAPI.main();
//            TestAPI.GetListComments.main();
//            TestAPI.TestGetDetailAuctionAPI.main();
//            TestAPI.TestGetListAuctionsByTypeAPI.main();
            TestAPI.TestGetListComments.main();
//            TestLoginAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

