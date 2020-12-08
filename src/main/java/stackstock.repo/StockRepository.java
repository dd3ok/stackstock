package stackstock.repo;

import org.springframework.data.repository.CrudRepository;
import stackstock.Stock;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Optional<Stock> findById(Long Id);

}
