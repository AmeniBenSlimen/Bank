package com.pfe.Bank.service;

import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.Modele;
import com.pfe.Bank.model.Type;
import com.pfe.Bank.model.Variable;

public interface VariableService {
    Modele findById(Long id) throws MissingEntity;

    //Variable addVariable(String code, String description, Double coefficient, Type type, long modeleId) throws MissingEntity;

    Variable addVariable(Variable variable);
}
