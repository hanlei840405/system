package com.bird.framework.system;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
public class SystemApplicationTests {

    @Test
    public void contextLoads() {
        String credentials = "admin:12345";
        String base64Credentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8201/user/username/admin", HttpMethod.GET, request, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        log.info(httpStatus.toString());
    }

}
