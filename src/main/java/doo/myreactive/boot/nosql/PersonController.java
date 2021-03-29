package doo.myreactive.boot.nosql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {

    private ReactiveRedisOperations<String, Person> personOps;

    public PersonController(@Qualifier("personReactiveRedisOperations") ReactiveRedisOperations<String, Person> personOps) {
        this.personOps = personOps;
    }

    @GetMapping("/person/{id}")
    public Mono<Person> person(@PathVariable long id) {
        personOps.opsForSet().add(String.valueOf(id), new Person("Vincent", "lawsnland@gmail.com"));
        return personOps.opsForValue().get("person:" + id);
    }

    @GetMapping("/persons")
    public Flux<Person> allPerson() {
        return personOps.keys("*").flatMap(personOps.opsForValue()::get);
    }
}
