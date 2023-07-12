package JWT_generator_for_request_sign;

import java.util.HashMap;
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
            claims.put("redirect_uri", "https://sample.com");
            claims.put("scope", "normal-transfer");
            claims.put("expireDate", "1402/12/10");
            claims.put("MaxAmount", "2000");
            claims.put("TransactionMaxCount", "100");
            claims.put("response_type", "code");
            claims.put("client_id", "sampleClient");
            claims.put("state", "statefor123");
            String token = generator.generateJwt(claims);
            System.out.println( token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
