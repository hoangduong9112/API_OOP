package Utils;

public class APIPath {
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";

    public static final String CREAT = "https://auctions-app-2.herokuapp.com/api/create";
    private static String deleteComments;
    public static final String CHANGE_PASS = "https://auctions-app-2.herokuapp.com/api/changepass";
    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";
    public static final String loginFailed = "http://auctions-app-2.herokuapp.com/api/loginfailed";

    public static void setDeleteComments(int auctionID) {
         deleteComments = String.format("https://auctions-app-2.herokuapp.com/api/comments/delete/%d", auctionID);
    }
    public static String getDeleteComment() {
        return deleteComments;
    }
}
