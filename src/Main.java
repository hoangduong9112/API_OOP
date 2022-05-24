import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
//            LoginAPI.main();
            GetListAuctionsByTypeAPI.main();
//            GetDetailAuctionAPI.main();
//            GetListComments.main();
//            SignUpAPI.main();
//            EditAccountAPI.main();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

