package KospiCrawler.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import KospiCrawler.Kospi;

@Repository
public interface KospiRepository extends ReactiveMongoRepository<Kospi, Long> {

    Mono<Kospi> findById(Long Id);
    Flux<Kospi> findFirstByCode(String code);
    Flux<Kospi> findByCode(String code);
    Flux<Kospi> deleteByCode(String code);

}
