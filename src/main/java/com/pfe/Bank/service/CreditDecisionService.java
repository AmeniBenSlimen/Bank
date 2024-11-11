package com.pfe.Bank.service;

import com.pfe.Bank.dto.SituationClientRetailDTO;
import com.pfe.Bank.model.SituationClientRetail;
import com.pfe.Bank.repository.SituationClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditDecisionService {
    @Autowired
    SituationClientRepository situationClientRepository;
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
    @Autowired
    public List<SituationClientRetailDTO> getClientSituations() {
        List<SituationClientRetail> situations = situationClientRepository.findAll();
        return situations.stream()
                .map(situation -> new SituationClientRetailDTO(
                        situation.getId(),
                        situation.getCodeRelation(),
                        situation.getNumeroComptePrincipal(),
                        situation.getRationEndettement(),
                        situation.getClasseRisqueLegacy(),
                        situation.getScoreClientLegacy()
                )).collect(Collectors.toList());
    }
    public double calculateTotalDebtRatio(double encoursCT, double encoursMT, double encoursCreditTresorerie, double mntEnConsolidation) {
        return (encoursCT + encoursMT + encoursCreditTresorerie) / mntEnConsolidation;
    }
}
