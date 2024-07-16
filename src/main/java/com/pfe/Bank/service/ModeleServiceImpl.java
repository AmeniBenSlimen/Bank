package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.ModeleForm;
import com.pfe.Bank.model.Modele;

import com.pfe.Bank.repository.ModeleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ModeleServiceImpl implements ModeleService{
    private static final Logger logger = LoggerFactory.getLogger(Modele.class);

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
        modele.setName(form.getName());
        modele.setDescription(form.getDescription());
        modele.setDisabled(form.isDisabled());
        modele.setDateUpdate(form.getUpdateDate());
        modele.setUsed(form.isUsed());
        modele.setDateCreation(form.getDateCreation());
        modele.setUpdatebale(form.isUpdatebale());
        if (modele.getDateCreation() == null) {
            Date currentDate = new Date();
            modele.setDateCreation(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            modele.setAnnee(calendar.get(Calendar.YEAR));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(modele.getDateCreation());
            modele.setAnnee(calendar.get(Calendar.YEAR));
        }
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
            throw new MissingEntity("Modele not found with code Modele : "+id);
        }
        return optional.get();
    }

    @Override
    public Modele updateModele(Long id, ModeleForm form) throws MissingEntity {
        Modele modele = getModeleById(id);
        modele.setName(form.getName());
        modele.setDescription(form.getDescription());
        modele.setDateUpdate(form.getUpdateDate());
        modele.setUsed(form.isUsed());
        modele.setDateCreation(form.getDateCreation());
        modele.setUpdatebale(form.isUpdatebale());
        modele.setUpdatebale(true);
        //modele.setLastUsedDate(new Date());
        modele.setNextUpdateDate(new Date());
        modele.setDisabled(form.isDisabled());
        modele.setAnnee(form.getAnnee());
        return modeleRepository.save(modele);
    }
    @Override
    public void deleteModele(Long id) throws MissingEntity {
        Modele modele = getModeleById(id);
        modele.setDisabled(true);
        modeleRepository.save(modele);
    }
    public List<Modele> getModelesToBeSoftDisabled() {
        return modeleRepository.findModelesToBeSoftDisabled();
    }

    @Override
    public List<Modele> getModelesSoftDisabled() {
        return modeleRepository.findModelesSoftDisabled();
    }

    @Override
    public List<Modele> getModelesUsed() {

        return modeleRepository.findByUsed(true);
    }

    @Override
    public List<Modele> getModelesNotUsed() {

        return modeleRepository.findByUsed(false);
    }
    @Override
    public void restoreModele(Long id) throws MissingEntity {
        Modele modele = modeleRepository.findById(id).orElseThrow(() -> new MissingEntity("Modele not found"));
        modele.setDisabled(false);
        modeleRepository.save(modele);
    }

    @Override
    public List<Modele> searchByNameAndAnnee(String name, int annee) {
        logger.info("Searching for models with name: {} and year: {}", name, annee);
        List<Modele> result = modeleRepository.findByNameAndAnnee(name, annee);
        logger.info("Found {} models", result.size());
        return result;
    }

}
