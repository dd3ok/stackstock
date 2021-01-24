package KospiCrawler.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import KospiCrawler.Kospi;
import KospiCrawler.repository.KospiRepository;

import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Service
public class KospiCrawlingService {
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
                        .flatMap(this::getKospiPageByCode);

        result.subscribe(i->{

            long beforeTime = System.currentTimeMillis();

            Document doc = Jsoup.parse(i);
            Elements elements = doc.getElementById("middle").
                    getElementsByClass("blind").
                    first().
                    getElementsByTag("dd");

            Kospi kospi = new Kospi();

            for(Element element : elements){
                String elementText = element.text();
                elementText = elementText.replace(",", "");
                StringTokenizer stringTokenizer = new StringTokenizer(elementText);
                String sName    = stringTokenizer.nextToken(" ");
                String value    = stringTokenizer.nextToken(" ");
                if(sName.equals("종목코드")){
                    kospi.setCode(value);
                }else if(sName.equals("현재가")){
                    kospi.setPrice(Long.parseLong(value));
                }else if(sName.equals("거래량")){
                    kospi.setTradeVolume(Long.parseLong(value));
                }
            }

            kospiRepository
                    .save(kospi)
                    .subscribe(resultKospiRepository -> log.info("Entity has been saved: {}", resultKospiRepository));

            long endTime = System.currentTimeMillis();
            log.info("End time : {}",endTime);
        });
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
