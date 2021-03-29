package doo.myreactive.boot.demoapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RedisCrudRepository redisCrudRepository;

    @After
    public void tearDown() throws Exception {
        redisCrudRepository.deleteAll();
    }

    @Test
    public void basic() {
        String url = "http://localhost:" + port;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("ok", responseEntity.getBody());
    }

    @Test
    public void basicRegisterSearch() {
        RedisCrudSaveRequestDto requestDto = RedisCrudSaveRequestDto.builder()
                .id(1L)
                .description("description")
                .updatedAt(LocalDateTime.now())
                .build();

        String url = "http://localhost:" + port + "/save";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(1L == responseEntity.getBody());
    }
}
