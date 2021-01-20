package stackstock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import stackstock.service.GetKOSPIDataService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StackstockApplication implements CommandLineRunner {
    @Autowired
    GetKOSPIDataService getKOSPIDataService;

    public static void main(String[] args) {
        SpringApplication.run(StackstockApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> codes = new ArrayList<>();

        codes.add("005930");
        codes.add("000660");
        getKOSPIDataService.fetchKospiPages(codes);
    }
}
