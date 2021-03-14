package KospiCrawler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
public class WebClientTestService {
    @Autowired
    WebClient webClient;

    Mono<String> getPage(String code){
        log.info("Start time : {}",System.currentTimeMillis());
        return webClient.get().
                uri("http://localhost:8080/code/{code}",code).
                retrieve().
                bodyToMono(String.class);
    }

    public void fetchData(List<String> codes) {
        Flux<String> result = Flux.fromIterable(codes)
                .parallel()
                .runOn(Schedulers.elastic())
                .flatMap(this::getPage)
                .ordered((s1, s2) -> s1.length() - s2.length());

        result.subscribe(i->{

            long endTime = System.currentTimeMillis();
            log.info("End time : {}",endTime);
        });
    }
}
