package stackstock.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import lombok.extern.slf4j.Slf4j;
import stackstock.Stock;
import stackstock.repo.StockRepository;

@Slf4j
@Controller
@RequestMapping("/stock")
@SessionAttributes("stock")
public class StockController {

    private final StockRepository stockRepo;

    public StockController(StockRepository stockRepo) {
        this.stockRepo = stockRepo;
    }

    @GetMapping("/current")
    public String stockForm(){
        return "stockForm";
    }

    @PostMapping
    public String processStock(@Valid Stock stock, Errors errors,
                               SessionStatus sessionStatus) {
        if(errors.hasErrors()) {
            return "stockForm";
        }

        stockRepo.save(stock);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}