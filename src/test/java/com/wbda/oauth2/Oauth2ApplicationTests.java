package com.wbda.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2ApplicationTests {

	@Test
	public void contextLoads() {
	    String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTc1NDIzNDUsInVzZXJfbmFtZSI6ImxlZnRzbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI0ODFlOTNjNi1hOTBlLTQxOGItYjc5YS0wZDNhNzczZWE2YzkiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWFwcCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.Jpi0HD7hBu9IbyYNCuumw8XnrfmB3EJVG4z5w9MNz44";
        Jwt jwtToken = JwtHelper.decode(accessToken);
        String claims = jwtToken.getClaims();
        HashMap claimsMap = null;
        try {
            claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
        } catch (Exception e) {

        }

        System.out.println(claimsMap);

	}

}
