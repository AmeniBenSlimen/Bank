package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariableServiceImpl implements VariableService{
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    ModeleRepository modeleRepository;
    @Override
    public Modele findById(Long id) throws MissingEntity {
        Optional<Modele> modele = modeleRepository.findById(id);
        if(!modele.isPresent()){
            throw new MissingEntity("Modele not found with Id Modele : "+id);
        }
        return modele.get();
    }
    @Override
    public Variable addVariable(Variable variable) {

            return variableRepository.save(variable);
        }

    /*@Override
    public Variable addVariable(Variable variable) {
        return null;
    }*/

}
