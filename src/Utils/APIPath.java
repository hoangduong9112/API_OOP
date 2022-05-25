package Utils;

public class APIPath {
    // LoginPath
    public static final String LOGIN = "https://auctions-app-2.herokuapp.com/api/login";
    // SignupPath
    public static final String SIGNUP = "https://auctions-app-2.herokuapp.com/api/signup";
    // GetListAuctionsPath
    private static String GetListAuctions;
    public static String getGetListAuctions() {
        return GetListAuctions;
    }
    public static void setGetListAuctions(int statusID) {
        GetListAuctions = String.format("https://auctions-app-2.herokuapp.com/api/auctions/%d", statusID);
    }


    public static final String EDIT_ACCOUNT = "https://auctions-app-2.herokuapp.com/api/edit";


}
