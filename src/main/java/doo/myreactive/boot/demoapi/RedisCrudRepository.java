package doo.myreactive.boot.demoapi;

import org.springframework.data.repository.CrudRepository;

public interface RedisCrudRepository extends CrudRepository<RedisCrud, Long> {
}
