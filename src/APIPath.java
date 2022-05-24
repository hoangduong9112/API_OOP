public class APIPath {
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    private static String GetListAuctionsByType;
    private static String GetDetailAuction;

    private static String GetListComments;

    public static String getGetListAuctionsByType() {
        return GetListAuctionsByType;
    }

    public static void setGetListAuctionsByType(int typeId, int statusID) {
        GetListAuctionsByType = String.format("https://auctions-app-2.herokuapp.com/api/auctions/listAuctions/%d/%d", typeId, statusID);
    }

    public static String getGetDetailAuction() {
        return GetDetailAuction;
    }

    public static void setGetDetailAuction(int auctionID) {
        GetDetailAuction = String.format("https://auctions-app-2.herokuapp.com/api/auctions/detail/%d", auctionID);
    }

    public static String getGetListComments() {
        return GetListComments;
    }

    public static void setGetListComments(int auctionID) {
        GetListComments = String.format("https://auctions-app-2.herokuapp.com/api/comments/%d", auctionID);
    }
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
}
