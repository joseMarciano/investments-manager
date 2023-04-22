package com.investment.managment.api.health;

import com.investment.managment.http.feing.InvestmentCalculatorHttpSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InvestmentCalculatorCheck {

    private final InvestmentCalculatorHttpSearch investmentCalculatorHttpSearch;

    public InvestmentCalculatorCheck(final InvestmentCalculatorHttpSearch investmentCalculatorHttpSearch) {
        this.investmentCalculatorHttpSearch = investmentCalculatorHttpSearch;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkInvestmentCalculator() {
        try {
            investmentCalculatorHttpSearch.check();
        } catch (Exception e) {
            log.error("Error on check investment-calculator {}", e.getMessage());
        }
    }
}
