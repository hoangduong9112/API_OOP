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

    // LoginFailed Path
    public static final String loginFailed = "http://auctions-app-2.herokuapp.com/api/loginfailed";
    // GetListLikesPath
    private static String GetListLikes;

    public static String getGetListLikes() {
        return GetListLikes;
    }

    public static void setGetListLikes(int statusID) {
        GetListLikes = String.format("https://auctions-app-2.herokuapp.com/api/likes/%d", statusID);
    }

    // TotalLikesOfAuctionPath
    private static String TotalLikesOfAuction;

    public static String getTotalLikesOfAuction() {
        return TotalLikesOfAuction;
    }

    public static void setTotalLikesOfAuction(int auctionID) {
        TotalLikesOfAuction = String.format("https://auctions-app-2.herokuapp.com/api/totalLikes/%d", auctionID);
    }

    // GetNewsPath
    public static final String GET_NEWS = "https://auctions-app-2.herokuapp.com/api/news";
    // ReadNewsPath
    private static String ReadNews;

    public static String getReadNews() {
        return ReadNews;
    }

    public static void setReadNews(int newID) {
        ReadNews = String.format("https://auctions-app-2.herokuapp.com/api/news/read/%d", newID);
    }

    public static void setGetListAuctions(int statusID) {
        GetListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
    }

    public static String getGetListAuctions() {
        return GetListAuctions;
    }
}
