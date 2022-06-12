import TestAPI.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {


//            TestGetDetailAuctionAPI.main();
//            TestGetListAuctionsByTypeAPI.main();
//            TestGetListComments.main();

//            TestLoginAPI.main();

//            TestGetListAuctionsAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case
//            TestCreateBidAPI.main();
//            TestCreateCommentAPI.main(); // need update test case
//            TestGetListLikesAPI.main();
            TestTotalLikesOfAuctionAPI.main();
//            TestGetNewsAPI.main();
//            TestReadNewsAPI.main();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

