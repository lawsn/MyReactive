package doo.myreactive.boot.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebfluxTest {

    @Test
    public void testFluxJustConsumer() {
        List<String> names = new ArrayList<>();
        Flux<String> flux = Flux.just("Ethan", "Collin").log();

        flux.subscribe(s -> {
            System.out.println("Accept Sequence : " +  s);
            names.add(s);
        });

        Assert.assertEquals(names, Arrays.asList("Ethan", "Collin"));
    }
}
