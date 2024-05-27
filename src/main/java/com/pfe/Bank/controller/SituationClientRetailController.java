package com.pfe.Bank.controller;

import com.pfe.Bank.model.Client;
import com.pfe.Bank.model.SituationClientRetail;
import com.pfe.Bank.repository.SituationClientRepository;
import com.pfe.Bank.service.SituationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequiredArgsConstructor
public class SituationClientRetailController {
    @Autowired
    private  SituationClientService situationService;
    @Autowired
    SituationClientRepository situationRepository;

    @PostMapping(value = "/uploadSituation", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadStuations(
            @RequestPart("file")MultipartFile file) throws IOException {

        Set<SituationClientRetail> situations = situationService.uploadSituations(file);

        situations.forEach(client -> situationRepository.save(client));

        return ResponseEntity.ok(situations.size());
    }
    @GetMapping("/ConsulterSituation")
    public List<SituationClientRetail> getAllClients(){
        return situationService.getSituations();
    }
}
