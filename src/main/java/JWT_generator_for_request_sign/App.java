package JWT_generator_for_request_sign;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )

    {
        try {
            JwtGenerator generator = new JwtGenerator();
            //claims are key and value request parameters
            Map<String, String> claims = new HashMap<>();
            claims.put("key1", "value1");
            claims.put("key2", "value2");
            claims.put("key3", Long.toString(System.currentTimeMillis()));
            claims.put("key4", "value3");
            claims.put("key5", "value4");
            claims.put("key6", "value5");
        /*
              "IdempotencyKey": "9100649682",
              "IssuedAt": "1690784022000",
              "DebtorAccount": "306826891",
              "Amount": "15000",
              "CreditorAccount": "3946957592",
              "ClientId": "faraboom_test-transfer"
             */
//            claims.put("redirect_uri", "https://sample.com");
//            claims.put("scope", "normal-transfer");
//            claims.put("expireDate", "1402/12/10");
//            claims.put("MaxAmount", "2000");
//            claims.put("TransactionMaxCount", "100");
//            claims.put("response_type", "code");
//            claims.put("client_id", "sampleClient");
//            claims.put("state", "statefor123");
            String token = generator.generateJwt(claims);
            System.out.println( token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
