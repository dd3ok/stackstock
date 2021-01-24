package KospiCrawler.service;

import KospiCrawler.Kospi;
import KospiCrawler.repository.KospiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class KospiService {

    @Autowired
    KospiRepository kospiRepository;

    private Flux<Kospi> findByCode (String code){
        return kospiRepository.findByCode(code);
    }

}
