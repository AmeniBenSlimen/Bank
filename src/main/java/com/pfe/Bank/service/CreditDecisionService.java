package com.pfe.Bank.service;

import org.springframework.stereotype.Service;

@Service
public class CreditDecisionService {
    private static final int HIGH_THRESHOLD = 80;
    private static final int MEDIUM_THRESHOLD = 50;

    public String makeDecision(int score, boolean creditHistoryGood, boolean repaymentCapacityGood) {
        if (score >= HIGH_THRESHOLD) {
            return "Approuvé";
        } else if (score >= MEDIUM_THRESHOLD) {
            // Vérifiez les autres critères
            if (creditHistoryGood && repaymentCapacityGood) {
                return "Approuvé avec conditions";
            } else {
                return "À revoir";
            }
        } else {
            return "Refusé";
        }
    }
}
