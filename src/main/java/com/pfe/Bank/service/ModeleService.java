package com.pfe.Bank.service;

import com.pfe.Bank.dto.ModeleDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.MenuForm;
import com.pfe.Bank.form.ModeleForm;
import com.pfe.Bank.form.RoleForm;
import com.pfe.Bank.model.Menu;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Role;

import java.util.List;

public interface ModeleService {
    List<Modele> getAllModele();

    public Modele addModele(ModeleForm form) throws MissingEntity;
    Modele getModeleById(long id) throws MissingEntity;
    public Modele updateModele(Long id, ModeleForm form) throws MissingEntity;
    void deleteModele(Long id) throws MissingEntity;
    public List<Modele> getModelesToBeSoftDisabled();
    List<ModeleDto> getModelesSoftDisabled();
    public List<Modele> getModelesUsed();

    public List<Modele> getModelesNotUsed();
    public void restoreModele(Long id) throws MissingEntity;
    public List<Modele> searchByNameAndAnnee(String name , int annee);


}
