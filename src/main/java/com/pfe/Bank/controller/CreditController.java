package com.pfe.Bank.controller;

import com.pfe.Bank.service.CreditDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreditController {
    @Autowired
    private CreditDecisionService creditDecisionService;

    @PostMapping("/decision")
    public String getCreditDecision(@RequestParam int score,
                                    @RequestParam boolean creditHistoryGood,
                                    @RequestParam boolean repaymentCapacityGood) {
        return creditDecisionService.makeDecision(score, creditHistoryGood, repaymentCapacityGood);
    }
}
