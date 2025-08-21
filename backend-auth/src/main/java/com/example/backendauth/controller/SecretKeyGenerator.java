import javax.crypto.SecretKey;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("====================================================================");
        System.out.println("            Your New Base64 Encoded Secret Key");
        System.out.println("====================================================================");
        System.out.println(base64Key);
        System.out.println("====================================================================");
        System.out.println(">> Copy the key above and paste it into your application.properties file.");
    }
}