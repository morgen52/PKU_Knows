import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class KeyTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        System.out.println(Base64.encodeBase64String(UUID.randomUUID().toString().replace("-", "").getBytes()));
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey key = keyGenerator.generateKey();
//        System.out.println(Base64.encodeBase64String(key.getEncoded()));

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] encoded = key.getEncoded();
        String keyStr = Base64.encodeBase64String(encoded);
        System.out.println("keyStr = " + keyStr);
        long timestamp = System.currentTimeMillis();
        String jws = Jwts.builder()
                .setSubject("Tom")
                .setExpiration(new Date(timestamp + 7200000))
                .signWith(key)
                .compact();
        System.out.println("jws = " + jws);
    }
}
