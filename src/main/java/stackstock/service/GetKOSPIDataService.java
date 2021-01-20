package stackstock.service;

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
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class GetKOSPIDataService {
    @Autowired
    WebClient webClient;

    public void getData(){
        Mono<String> result = webClient.get().
                uri("https://finance.naver.com/item/main.nhn?code=005930").
                retrieve().
                bodyToMono(String.class);

        result.subscribe(i->{
            Document doc = Jsoup.parse(i);
            Elements elements = doc.getElementById("middle").
                    getElementsByClass("blind").
                    first().
                    getElementsByTag("dd");
            for(Element element : elements){
                System.out.println(element.text());
            }

        });
    }

    Mono <String> getKospiPageByCode(String code){
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
            Document doc = Jsoup.parse(i);
            Elements elements = doc.getElementById("middle").
                    getElementsByClass("blind").
                    first().
                    getElementsByTag("dd");
            for(Element element : elements){
                System.out.println(element.text());
            }
            System.out.println();
        });
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
