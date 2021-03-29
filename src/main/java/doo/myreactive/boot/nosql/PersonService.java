package doo.myreactive.boot.nosql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PersonService {

    private final ReactiveRedisConnectionFactory factory;

    private final ReactiveRedisOperations<String, Person> reactiveRedisOperations;

    private final ReactiveRedisTemplate<String, Person> stringStringRedisTemplate;

    private static final AtomicInteger count = new AtomicInteger(0);

    public PersonService(ReactiveRedisConnectionFactory factory
            , @Qualifier("personReactiveRedisOperations") ReactiveRedisOperations<String, Person> reactiveRedisOperations
            , @Qualifier("personRedisTemplate") ReactiveRedisTemplate<String, Person> stringStringRedisTemplate) {
        this.factory = factory;
        this.reactiveRedisOperations = reactiveRedisOperations;
        this.stringStringRedisTemplate = stringStringRedisTemplate;
    }

    Person findReactorByKey(String key) {

        System.out.println(stringStringRedisTemplate.opsForValue());
        System.out.println(stringStringRedisTemplate.opsForValue().get(key));


        return stringStringRedisTemplate.opsForValue().get(key).block();
//        return reactiveRedisOperations.opsForValue().get(key).block();
    }

//    Flux<String> findNormalByKey() {
//        return Flux.fromIterable(Objects.requireNonNull(stringStringRedisTemplate.keys("*"))
//                .stream()
//                .map(k -> stringStringRedisTemplate.opsForValue().get(k))
//                .collect(Collectors.toList()));
//    }

}
