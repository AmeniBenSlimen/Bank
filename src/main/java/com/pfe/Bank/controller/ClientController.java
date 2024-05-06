package com.pfe.Bank.controller;

import com.pfe.Bank.model.Client;
import com.pfe.Bank.repository.ClienRepository;
import com.pfe.Bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClienRepository clienRepository;
    @PostMapping(value = "/uploadClient", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadClients(
            @RequestPart("file")MultipartFile file) throws IOException {

        Set<Client> clients = clientService.uploadClients(file);

        clients.forEach(client -> clienRepository.save(client));

        return ResponseEntity.ok(clients.size());
    }

}
