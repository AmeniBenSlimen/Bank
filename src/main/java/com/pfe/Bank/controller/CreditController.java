package com.pfe.Bank.controller;

import com.pfe.Bank.dto.SituationClientRetailDTO;
import com.pfe.Bank.model.Appreciation;
import com.pfe.Bank.repository.NotationRepository;
import com.pfe.Bank.service.CreditDecisionService;
import com.pfe.Bank.service.SituationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreditController {

    @Autowired
    private CreditDecisionService creditDecisionService;
    @Autowired
    private NotationRepository notationRepository;
    @PostMapping("/decision")
    public String getCreditDecision(@RequestParam int score,
                                    @RequestParam boolean creditHistoryGood,
                                    @RequestParam boolean repaymentCapacityGood) {
        return creditDecisionService.makeDecision(score, creditHistoryGood, repaymentCapacityGood);
    }
    @GetMapping("/appreciations")
    public ResponseEntity<Map<String, Long>> getAppreciationsStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("TRES_BON", notationRepository.countByAppreciation(Appreciation.TRES_BON));
        stats.put("BON", notationRepository.countByAppreciation(Appreciation.BON));
        stats.put("MOYEN", notationRepository.countByAppreciation(Appreciation.MOYEN));
        stats.put("FAIBLE", notationRepository.countByAppreciation(Appreciation.FAIBLE));
        stats.put("TRES_FAIBLE", notationRepository.countByAppreciation(Appreciation.TRES_FAIBLE));
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/rating")
    public List<SituationClientRetailDTO> getAllSituations() {
        return creditDecisionService.getClientSituations();
        }
    @GetMapping("/debt-ratio")
    public double getDebtRatio(@RequestParam double encoursCT, @RequestParam double encoursMT,
                               @RequestParam double encoursCreditTresorerie, @RequestParam double mntEnConsolidation) {
        // Assurez-vous que la logique de calcul est correcte
        return creditDecisionService.calculateTotalDebtRatio(encoursCT, encoursMT, encoursCreditTresorerie, mntEnConsolidation);
    }

}
