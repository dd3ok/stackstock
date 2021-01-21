package stackstock.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import stackstock.repo.KospiRepository;

import java.util.List;

@Slf4j
@Service
public class GetKOSPIDataService {
    @Autowired
    WebClient webClient;
    @Autowired
    KospiRepository kospiRepository;

    Mono <String> getKospiPageByCode(String code){
        log.info("Start time : {}",System.currentTimeMillis());
        return webClient.get().
                uri("https://finance.naver.com/item/main.nhn?code={code}", code).
                retrieve().
                bodyToMono(String.class);
    }

    public void fetchKospiPages(List<String> codes) {
        Flux <String> result = Flux.fromIterable(codes)
                        .parallel()
                        .runOn(Schedulers.elastic())
                        .flatMap(this::getKospiPageByCode)
                        .ordered((s1, s2) -> s1.length() - s2.length());

        result.subscribe(i->{

            long beforeTime = System.currentTimeMillis();

            Document doc = Jsoup.parse(i);
            Elements elements = doc.getElementById("middle").
                    getElementsByClass("blind").
                    first().
                    getElementsByTag("dd");
            long endTime = System.currentTimeMillis();
            log.info("End time : {}",endTime);
        });
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
