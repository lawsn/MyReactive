package doo.myreactive.boot.nosql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PersonTest {

    @Autowired
    private ReactiveRedisTemplate<String, Person> personRedisTemplate;

    @Autowired
    ReactiveRedisOperations<String, Person> personReactiveRedisOperations;


    @Autowired
    private PersonService personService;

    @Test
    public void testDataHandling() throws Exception {
        String key = "key:springboot";
        Person person = new Person("Vincent", "lawsnland@gmail.com");

        System.out.println("1 " + person.toString());

        personReactiveRedisOperations.opsForValue().set(key, person);
        System.out.println("2 " + person.toString());


//        Person monoPerson = personService.findReactorByKey(key);
        Person monoPerson = personReactiveRedisOperations.keys("*").flatMap(keyx -> personReactiveRedisOperations.opsForValue().get(keyx)).blockFirst();;
        System.out.println("3 " + monoPerson);

        Assert.assertEquals("Vincent", monoPerson.getName());

    }
}
