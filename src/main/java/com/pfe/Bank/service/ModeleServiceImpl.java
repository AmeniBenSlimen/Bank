package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.ModeleForm;
import com.pfe.Bank.model.Modele;

import com.pfe.Bank.repository.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ModeleServiceImpl implements ModeleService{
    @Autowired
    ModeleRepository modeleRepository;

    @Override
    public List<Modele> getAllModele() {
        return modeleRepository.findAll();
    }

    @Override
    public Modele addModele(ModeleForm form) throws MissingEntity {
        Modele modele = new Modele();
        modele.setId(form.getId());
        modele.setName(form.getNom());
        modele.setDescription(form.getDescription());
        modele.setDisabled(form.isDisabled());
        modele.setDateUpdate(form.getUpdateDate());
        modele.setUsed(form.isUsed());
        modele.setDateCreation(form.getDateCreation());
        modele.setUpdatebale(form.isUpdatebale());
        if (modele.isUsed()) {
            modele.setLastUsedDate(new Date());
        }
        if (modele.isUpdatebale()) {
            modele.setNextUpdateDate(new Date());
        }
        modele.setDisabled(form.isDisabled());
        return modeleRepository.save(modele);
    }

    @Override
    public Modele getModeleById(long id) throws MissingEntity {
        Optional<Modele> optional = modeleRepository.findById(id);
        if(!optional.isPresent()){
            throw new MissingEntity("Modele not found with code Menu : "+id);
        }
        return optional.get();
    }

    @Override
    public Modele updateModele(Long id, ModeleForm form) throws MissingEntity {
        Modele modele = getModeleById(id);
        modele.setName(form.getNom());
        modele.setDescription(form.getDescription());
        modele.setDateUpdate(form.getUpdateDate());
        modele.setUsed(form.isUsed());
        modele.setDateCreation(form.getDateCreation());
        modele.setUpdatebale(form.isUpdatebale());
        modele.setUpdatebale(true);
        modele.setLastUsedDate(new Date());
        modele.setNextUpdateDate(new Date());
        modele.setDisabled(form.isDisabled());
        return modeleRepository.save(modele);
    }
    @Override
    public void deleteModele(Long id) throws MissingEntity {
        Modele modele = getModeleById(id);
        modele.setDeleted(true);
        modeleRepository.save(modele);
    }
    public List<Modele> getModelesToBeSoftDeleted() {
        return modeleRepository.findModelesToBeSoftDeleted();
    }

    @Override
    public List<Modele> getModelesSoftDeleted() {
        return modeleRepository.findModelesSoftDeleted();
    }

    @Override
    public List<Modele> getModelesUsed() {

        return modeleRepository.findByUsed(true);
    }

    @Override
    public List<Modele> getModelesNotUsed() {

        return modeleRepository.findByUsed(false);
    }
    public void restoreModele(Long id) throws MissingEntity {
        Modele modele = modeleRepository.findById(id).orElseThrow(() -> new MissingEntity("Modele not found"));
        modele.setDeleted(false);
        modeleRepository.save(modele);
    }
}
