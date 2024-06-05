package com.pfe.Bank.controller;

import com.pfe.Bank.dto.ModeleDto;
import com.pfe.Bank.dto.RoleDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.ModeleForm;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.service.ModeleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ModeleController {
    @Autowired
    ModeleService modeleService;

    @PostMapping("/addModele")
    public ModeleDto addModele(@RequestBody ModeleForm form) throws MissingEntity {
        Modele modele =modeleService.addModele(form);
        return ModeleDto.of(modele);
    }
    @GetMapping("/getAllModeles")
    List<ModeleDto> getAllModeles(){
        List<Modele> modele = modeleService.getAllModele();
        return ModeleDto.of(modele);
    }
    @GetMapping("/getModeleById/{id}")
    public ModeleDto getModeleById(@PathVariable Long id) throws MissingEntity{
        Modele modele = modeleService.getModeleById(id);
        return ModeleDto.of(modele);
    }
    @PutMapping("/updateModele/{id}")
    public ModeleDto updateModele(@PathVariable Long id, @RequestBody ModeleForm form) throws MissingEntity {
        Modele modele = modeleService.getModeleById(id);

        if (modele == null) {
            throw new MissingEntity("Modèle introuvable avec l'ID : " + id);
        }
        form.setDateCreation(modele.getDateCreation());
        modele.setUpdatebale(true);
        modele = modeleService.updateModele(id, form);

        return ModeleDto.of(modele);
    }
    @DeleteMapping("/softDeleteModel/{id}")
    public ResponseEntity<Void> deleteModele(@PathVariable Long id) {
        try {
            modeleService.deleteModele(id);
            return ResponseEntity.noContent().build();
        } catch (MissingEntity e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ModelsSoftDeleted")
    public List<Modele> getModelesToBeSoftDeleted() {
        return modeleService.getModelesToBeSoftDeleted();
    }
    @GetMapping("/ModelsSoftDeletedTrue")
    public List<Modele> getModelesSoftDeleted() {
        return modeleService.getModelesSoftDeleted();
    }
    @GetMapping("/ModelUsed")
    public ResponseEntity<List<Modele>> getModelUsed() {
        List<Modele> modelesUtilises = modeleService.getModelesUsed();
        return new ResponseEntity<>(modelesUtilises, HttpStatus.OK);
    }

    @GetMapping("/ModelNotUsed")
    public ResponseEntity<List<Modele>> getModelNotUsed() {
        List<Modele> modelesNonUtilises = modeleService.getModelesNotUsed();
        return new ResponseEntity<>(modelesNonUtilises, HttpStatus.OK);
    }
    @PutMapping("/restoreModele/{id}")
    public ResponseEntity<Void> restoreModele(@PathVariable Long id) {
        try {
            modeleService.restoreModele(id);
            return ResponseEntity.noContent().build();
        } catch (MissingEntity e) {
            return ResponseEntity.notFound().build();
        }
    }
}
