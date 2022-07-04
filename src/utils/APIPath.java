package utils;

public class APIPath {
    private APIPath() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    private static final String LOGIN_URL = "https://auctions-app-2.herokuapp.com/api/login";
    private static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
    public static final String LOGIN_FAILED = "http://auctions-app-2.herokuapp.com/api/loginfailed";
    public static final String GET_NEWS = "https://auctions-app-2.herokuapp.com/api/news";
    private static final String CREATE_AUCTION = "https://auctions-app-2.herokuapp.com/api/auctions/create";
    public static final String GET_SLIDER = "https://auctions-app-2.herokuapp.com/api/slider";
    public static final String SEARCH = "https://auctions-app-2.herokuapp.com/api/search";
    private static final String LOG_OUT = "https://auctions-app-2.herokuapp.com/api/logout";
    private static String getListAuctions;
    private static String getDetailAuction;
    private static String getListAuctionsByType;
    private static String getListComments;
    private static String createComment;
    private static String createBid;
    private static String createItem;
    private static String editItem;
    private static String infoItem;
    private static String getListLikes;
    private static String totalLikesOfAuction;
    private static String readNews;
    private static String getListBids;
    public static String getLoginURL() {
        return LOGIN_URL;
    }
    public static String getCreateAuctionURL() {
        return CREATE_AUCTION;
    }

    public static String getGetListAuctions() {
        return getListAuctions;
    }
    public static void setGetListAuctions(int statusID) {
        getListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
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
        return createComment;
    }
    public static void setCreateComment(int auctionID) {
        createComment = String.format("https://auctions-app-2.herokuapp.com/api/comments/create/%d", auctionID);
    }
    public static String getCreateBid() {
        return createBid;
    }
    public static void setCreateBid(int auctionID) {
        createBid = String.format("https://auctions-app-2.herokuapp.com/api/bids/create/%d", auctionID);
    }
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
    public static String getGetListBids(){ return getListBids; }

    public static void setGetListBids(int auctionID){
        getListBids = String.format("https://auctions-app-2.herokuapp.com/api/bids/%d", auctionID);
    }
    public static String getGetListLikes() {
        return getListLikes;
    }

    public static void setGetListLikes(int statusID) {
        getListLikes = String.format("https://auctions-app-2.herokuapp.com/api/likes/%d", statusID);
    }

    public static String getTotalLikesOfAuction() {
        return totalLikesOfAuction;
    }

    public static void setTotalLikesOfAuction(int auctionID) {
        totalLikesOfAuction = String.format("https://auctions-app-2.herokuapp.com/api/totalLikes/%d", auctionID);
    }

    public static String getReadNews() {
        return readNews;
    }

    public static void setReadNews(int newID) {
        readNews = String.format("https://auctions-app-2.herokuapp.com/api/news/read/%d", newID);
    }

    public static String getSignUp() {
        return SIGNUP;
    }

    public static String getLogOut() {
        return LOG_OUT;
    }
}

