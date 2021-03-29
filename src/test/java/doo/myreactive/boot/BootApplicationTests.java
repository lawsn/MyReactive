package doo.myreactive.boot;

import doo.myreactive.boot.nosql.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class BootApplicationTests {

    @Autowired
    ReactiveRedisTemplate<String, Person> personRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void opsValue() {
        ReactiveValueOperations<String, Person> valueOps = personRedisTemplate.opsForValue();
        Set<String> cacheKeys = new HashSet<>();
        // async process
        System.out.println("Step-1");
        for (int i = 0; i < 5000; i++) {
            String key = "value_" + i;
            cacheKeys.add(key);
            valueOps.set(key, new Person("Vincoh", "vincent.oha@gmail.com"));
        }
        System.out.println("Step-2");
        Mono<List<Person>> values = valueOps.multiGet(cacheKeys);
        System.out.println("Step-3");
        StepVerifier.create(values)
                .expectNextMatches(x -> x.size() == 5000).verifyComplete();
        System.out.println("Step-4");
    }

}
