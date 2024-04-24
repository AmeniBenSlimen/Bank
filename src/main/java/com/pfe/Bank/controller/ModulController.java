package com.pfe.Bank.controller;

import com.pfe.Bank.dto.ModulDto;
import com.pfe.Bank.form.ModulForm;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.service.ModulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ModulController {
    @Autowired
    ModulService modulService;

    @PostMapping("/addModule")
    public ModulDto addModules(@RequestBody ModulForm form){
        Modul modul =modulService.addModul(form);
        return ModulDto.of(modul);
    }

    @GetMapping("/getAllModul")
    List<ModulDto> getAllModules(){
        List<Modul> modul = modulService.getModules();
        return ModulDto.of(modul);
    }

    @GetMapping("/getByCodModule/{codmodule}")
    public ModulDto getModul(@PathVariable String codmodule){
        Modul modul = modulService.findByCodmodule(codmodule);
        return ModulDto.of(modul);
    }
    @DeleteMapping("/deleteCodModule/{codmodule}")
    public Map<String,Boolean> deleteModule(@PathVariable String codmodule){
        return modulService.deleteModule(codmodule);
    }
    @PutMapping("/updateModule/{codmodule}")
    public ModulDto updateModul(@PathVariable String codmodule, @RequestBody ModulForm form) {
        Modul modul = modulService.updateModule(codmodule,form);
        return ModulDto.of(modul);
    }
}

