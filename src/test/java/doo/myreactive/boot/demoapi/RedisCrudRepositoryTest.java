package doo.myreactive.boot.demoapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCrudRepositoryTest {

    @Autowired
    private RedisCrudRepository redisCrudRepository;

    @After
    public void tearDown() throws Exception {
        redisCrudRepository.deleteAll();
    }

    @Test
    public void basicRegisterSearch() {
        Long id = 0L;
        String description = "description";
        LocalDateTime updatedAt = LocalDateTime.of(2021, 3, 29, 0, 0);

        RedisCrud redisCrudSave = RedisCrud.builder()
                .id(id)
                .description(description)
                .updatedAt(updatedAt)
                .build();

        redisCrudRepository.save(redisCrudSave);

        RedisCrud redisCrudFind = redisCrudRepository.findById(id).get();
        Assert.assertEquals("description", redisCrudFind.getDescription());
        Assert.assertEquals(updatedAt, redisCrudFind.getUpdatedAt());
    }

    @Test
    public void basicRegisterUpdate() {
        Long id = 0L;
        String description = "description";
        LocalDateTime updatedAt = LocalDateTime.of(2021, 3, 29, 0, 0);

        RedisCrud redisCrudSave = RedisCrud.builder()
                .id(id)
                .description(description)
                .updatedAt(updatedAt)
                .build();

        redisCrudRepository.save(redisCrudSave);

        RedisCrud redisCrudUpdate = redisCrudRepository.findById(id).get();
        redisCrudUpdate.update("updated description", LocalDateTime.of(2021, 4, 1, 0, 0));
        redisCrudRepository.save(redisCrudUpdate);

        RedisCrud redisCrudFind = redisCrudRepository.findById(id).get();
        Assert.assertEquals("updated description", redisCrudFind.getDescription());
    }
}
