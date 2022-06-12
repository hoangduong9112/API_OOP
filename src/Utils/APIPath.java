package Utils;

public class APIPath {
    // LoginPath
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    // SignupPath
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
    public static final String loginFailed = "http://auctions-app-2.herokuapp.com/api/loginfailed";
    // GetListAuctionsPath
    private static String GetListAuctions;
    //    Get Detail Auction
    private static String getDetailAuction;
    //    Get List Auction By Type
    private static String getListAuctionsByType;
    //    Get List Comments
    private static String getListComments;
    // CreateCommentPath
    private static String CreateComment;
    // CreateBidPath
    private static String CreateBid;
    private static String createItem;
    private static String editItem;
    //    Get info Item
    private static String infoItem;

    public static String getGetListAuctions() {
        return GetListAuctions;
    }

    public static void setGetListAuctions(int statusID) {
        GetListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
    }

    public static String getGetDetailAuction() {
        return getDetailAuction;
    }

    public static void setGetDetailAuction(int auctionID) {
        getDetailAuction = String.format("https://auctions-app-2.herokuapp.com/api/auctions/detail/%d", auctionID);
    }

    public static String getGetListAuctionsByType() {
        return getListAuctionsByType;
    }

    public static void setGetListAuctionsByType(int typeId, int statusID) {
        getListAuctionsByType = String.format("https://auctions-app-2.herokuapp.com/api/auctions/listAuctions/%d/%d", typeId, statusID);
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

    // Edit Item

    public static String getCreateItem() {
        return createItem;
    }

    public static void setCreateItem(int auctionID) {
        createItem = String.format("https://auctions-app-2.herokuapp.com/api/items/create/%d", auctionID);
    }

    public static String getEditItem() {
        return editItem;
    }

    public static void setEditItem(int itemID) {
        editItem = String.format("https://auctions-app-2.herokuapp.com/api/items/edit/%d", itemID);
    }

    public static String getInfoItem() {
        return infoItem;
    }

    public static void setInfoItem(int itemID) {
        infoItem = String.format("https://auctions-app-2.herokuapp.com/api/items/info/%d", itemID);
    }
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

}