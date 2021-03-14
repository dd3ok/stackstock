package KospiCrawler.handler;

import KospiCrawler.Kospi;
import KospiCrawler.repository.KospiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class KospiHandler {

    @Autowired
    KospiRepository kospiRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getKospi(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux <Kospi> fluxKospi = kospiRepository.findByCode(code);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxKospi,Kospi.class);
    }

    public Mono<ServerResponse> postKospi(ServerRequest request) {
        Mono<Kospi> kospi = request.bodyToMono(Kospi.class);
        kospi.subscribe(i -> {
            kospiRepository.save(i);
        });
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(kospi,Kospi.class)
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> putKospi(ServerRequest request) {
        Mono<Kospi> kospi = request.bodyToMono(Kospi.class);
        kospi.subscribe(i -> {
            kospiRepository.insert(i);
        });
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(kospi,Kospi.class);
    }

    public Mono<ServerResponse> deleteKospi(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux<Kospi> fluxKospi = kospiRepository.deleteByCode(code);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxKospi,Void.class);
    }
    
}
