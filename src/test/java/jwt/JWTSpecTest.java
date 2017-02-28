package jwt;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

public class JWTSpecTest {

	@Test
	public void test() {
		//expected data
		byte[] secretKey = "secret".getBytes();
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
		
		//parse
		System.out.println("================PARSE=================");
		String tmp[] = token.split("\\.", 3);
		String header = new String(Base64Codec.BASE64.decode(tmp[0]));
		String payload = new String(Base64Codec.BASE64.decode(tmp[1]));
		String sign = tmp[2];
		assertEquals("{\"alg\":\"HS256\",\"typ\":\"JWT\"}", header);
		assertEquals("{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true}", payload);
		
		Jws<Claims> claims = Jwts.parser()
        	.setSigningKey(secretKey)
        	.parseClaimsJws(token);
		
		assertEquals("TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ", claims.getSignature());
		System.out.println("OK");
		
		
		secretKey = "secret###".getBytes();
		
		//build
		System.out.println("================BUILD=================");
		Map<String, Object> claim = new HashMap<String,Object>();
		claim.put("sub", "1234567890");
		claim.put("name", "John Doe");
		claim.put("admin", Boolean.TRUE);
		String jwt = Jwts.builder()
		.setHeaderParam("typ", "JWT")
        .setClaims(claim)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
		System.out.println(jwt);
	}

}
