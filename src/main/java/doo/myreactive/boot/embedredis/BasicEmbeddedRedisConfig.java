package doo.myreactive.boot.embedredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class BasicEmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    public BasicEmbeddedRedisConfig() {
    }

    @PostConstruct
    public void redisServer() {
        log.info("Embedded Redis Object Created");
        redisServer = new RedisServer(redisPort);
        log.info("Embedded Redis Object Created {}", redisPort);
        redisServer.start();
        log.info("Embedded Redis Started");
        log.info(" >> Port: " + redisServer.ports().stream().map(n -> String.valueOf(n)).collect(Collectors.joining()));
    }

    @PreDestroy
    public void stopRedis() {
        Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
    }
}
