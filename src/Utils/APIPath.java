package Utils;

public class APIPath {
    // LoginPath
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    // SignupPath
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
    private static String getListAuctionsByType;
    private static String getDetailAuction;
    private static String getListComments;
    // GetListAuctionsPath
    private static String GetListAuctions;
    // CreateCommentPath
    private static String CreateComment;
    // CreateBidPath
    private static String CreateBid;
    public static String getGetListAuctionsByType() {
        return getListAuctionsByType;
    }

    public static void setGetListAuctionsByType(int typeId, int statusID) {
        getListAuctionsByType = String.format("https://auctions-app-2.herokuapp.com/api/auctions/listAuctions/%d/%d", typeId, statusID);
    }

    public static String getGetDetailAuction() {
        return getDetailAuction;
    }

    public static void setGetDetailAuction(int auctionID) {
        getDetailAuction = String.format("https://auctions-app-2.herokuapp.com/api/auctions/detail/%d", auctionID);
    }

    public static String getGetListComments() {
        return getListComments;
    }

    public static void setGetListComments(int auctionID) {
        getListComments = String.format("https://auctions-app-2.herokuapp.com/api/comments/%d", auctionID);
    }
    public static String getCreateComment() {
        return CreateComment;
    }

    public static void setCreateComment(int auctionID) {
        CreateComment = String.format("https://auctions-app-2.herokuapp.com/api/comments/create/%d", auctionID);
    }

    public static String getCreateBid() {
        return CreateBid;
    }

    public static void setCreateBid(int auctionID) {
        CreateBid = String.format("https://auctions-app-2.herokuapp.com/api/bids/create/%d", auctionID);
    }

    public static void setGetListAuctions(int statusID) {
        GetListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
    }

    public static String getGetListAuctions() {
        return GetListAuctions;
    }
}
