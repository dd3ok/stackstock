package stackstock.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stackstock.Kospi;

@Repository
public interface KospiRepository extends ReactiveMongoRepository<Kospi, Long> {

    Mono<Kospi> findById(Long Id);

}
