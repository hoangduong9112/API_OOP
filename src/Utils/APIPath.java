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

    public static String getGetListAuctions() {
        return GetListAuctions;
    }

    public static void setGetListAuctions(int statusID) {
        GetListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
    }

    // CreateCommentPath
    private static String CreateComment;

    public static String getCreateComment() {
        return CreateComment;
    }

    public static void setCreateComment(int auctionID) {
        CreateComment = String.format("https://auctions-app-2.herokuapp.com/api/comments/create/%d", auctionID);
    }

    // CreateBidPath
    private static String CreateBid;

    public static String getCreateBid() {
        return CreateBid;
    }

    public static void setCreateBid(int auctionID) {
        CreateBid = String.format("https://auctions-app-2.herokuapp.com/api/bids/create/%d", auctionID);
    }

    private static String createItem;
    public static String getCreateItem() {
        return createItem;
    }

    public static void setCreateItem(int auctionID) {
        createItem = String.format("https://auctions-app-2.herokuapp.com/api/items/create/%d", auctionID);
    }

    // Edit Item

    private static String editItem;

    public static String getEditItem() {
        return editItem;
    }

    public static void setEditItem(int itemID) {
        editItem = String.format("https://auctions-app-2.herokuapp.com/api/items/edit/%d", itemID);;
    }

//    Get info Item
    private static String infoItem;
    public static String getInfoItem(){
        return infoItem;
    }

    public static void setInfoItem(int itemID){
        infoItem = String.format("https://auctions-app-2.herokuapp.com/api/items/info/%d", itemID);
    }

// Accept max bid
    private static String accessMaxBid;
    public static String getAccessMaxBid(){return accessMaxBid;}

    public static void setAccessMaxBid(int auctionID){
        accessMaxBid = String.format("https://auctions-app-2.herokuapp.com/api/accept/%d", auctionID);
    }

//    Contact us
    public static final String contactUs= "https://auctions-app-2.herokuapp.com/api/contactUs";

// Like Auction
    private static String likeAuction;

    public static String getLikeAuction() {
        return likeAuction;
    }

    public static void setLikeAuction(int auctionID) {
        likeAuction = String.format("https://auctions-app-2.herokuapp.com/api/updateLike/%d", auctionID);
    }
}
