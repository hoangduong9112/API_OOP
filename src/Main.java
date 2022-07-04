import test_api.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            TestLoginAPI.main();

//            TestGetDetailAuctionAPI.main();
//            TestGetListAuctionsByTypeAPI.main();
//            TestGetListComments.main();
//            TestGetListAuctionsAPI.main();
//            TestSignUpAPI.main(); // need update test case
//            TestEditAccountAPI.main(); // need update test case
//            TestCreateBidAPI.main();
//            TestCreateCommentAPI.main(); // need update test case
//            TestGetListLikesAPI.main();
//            TestTotalLikesOfAuctionAPI.main();
//            TestGetNewsAPI.main();
//            TestReadNewsAPI.main();
//            TestCreateAuctionAPI.main();
//            TestGetSlider.main();
//            TestSearch.main();
//            TestTotalLikesOfAuction.main();
//            TestGetNews.main();
//            TestGetListBids.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

