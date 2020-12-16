package stackstock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GetKOSPIDataService {
    @Autowired
    WebClient webClient;

    void doSometing(){
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
