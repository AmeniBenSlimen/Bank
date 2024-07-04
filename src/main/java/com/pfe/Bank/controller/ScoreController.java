package com.pfe.Bank.controller;

import com.pfe.Bank.service.CalculScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ScoreController {
    @Autowired
    CalculScoreService calculScoreService;
    @PostMapping("/CalculeScore")
    public ResponseEntity<Double> calculateScore(@RequestBody Map<String, String> values) {
        double score = calculScoreService.calculateScore(values);
        return ResponseEntity.ok(score);
    }
}
