package com.pfe.Bank.service;

import com.pfe.Bank.form.ModulForm;
import com.pfe.Bank.model.Modul;

import java.util.List;
import java.util.Map;

public interface ModulService {
    public Modul addModul(ModulForm form);
    public List<Modul> getModules();

    public Modul findByCodmodule(String codmodule);
    public Modul updateModule(String codmodule, ModulForm form);

    public Map<String,Boolean> deleteModule(String codmodule);

}
