import test_api.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
//            TestLoginAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestCreateAuctionAPI.main(); // need update test case
//            TestGetListBids.main();
//            TestCreateBidAPI.main(); // need update test case
//            TestCreateCommentAPI.main();
//            TestGetListComments.main();
            TestGetListAuctionsAPI.main();

//            TestCreateItemAPI.main(); // need update test case
//            TestGetDetailAuctionAPI.main();
//            TestEditAccountAPI.main(); // need update test case
//            TestGetListLikesAPI.main();
//            TestReadNewsAPI.main();
//            TestGetSlider.main();
//            TestSearch.main();
//            TestTotalLikesOfAuction.main();
//            TestGetNews.main();
//            TestGetListAuctionsByTypeAPI.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

