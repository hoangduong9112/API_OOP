import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LoginAPI.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

