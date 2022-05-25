public class APIPath {
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
    private static String CreateComment;
    private static String CreateBid;
    public static String getCreateComment() {
        return CreateComment;
    }
    public static void setCreateComment(int auctionID) {
        CreateComment = String.format("https://auctions-app-2.herokuapp.com/api/comments/create/%d", auctionID);
    }
    public static String getCreateBid(){
        return CreateBid;
    }
    public static void setCreateBid(int auctionID){
        CreateBid = String.format("https://auctions-app-2.herokuapp.com/api/bids/create/%d", auctionID);
    }
}
