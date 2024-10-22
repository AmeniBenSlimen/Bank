package com.pfe.Bank.service;

import com.pfe.Bank.dto.ScoreDto;
import com.pfe.Bank.dto.VariableDto;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.ModeleRepository;
import com.pfe.Bank.repository.ScoreVariableRepository;
import com.pfe.Bank.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VariableServiceImpl implements VariableService{
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    ScoreVariableRepository scoreVariableRepository;

    /*@Override
    public Modele findById(Long id) throws MissingEntity {
        Optional<Modele> modele = modeleRepository.findById(id);
        if(!modele.isPresent()){
            throw new MissingEntity("Modele not found with Id Modele : "+id);
        }
        return modele.get();
    }*/
    /*@Override
    public Variable addVariable(Variable variable) {

            return variableRepository.save(variable);
    }*/
    public void addScoreToVariable(long variableId, Score score) throws IllegalArgumentException {
        Variable variable = variableRepository.findById(variableId)
                .orElseThrow(() -> new IllegalArgumentException("Variable not found"));

        if ((variable.getType() == Type.NUMBER && !(score instanceof NUMBER)) ||
                (variable.getType() == Type.DATE && !(score instanceof DATE)) ||
                (variable.getType() == Type.ENUMERATION && !(score instanceof ENUMERATION)) ||
                (variable.getType() == Type.INTERVALE && !(score instanceof INTERVALE))) {
            throw new IllegalArgumentException("Score type does not match variable type");
        }

        score.setVariable(variable);
        scoreVariableRepository.save(score);
    }
    @Override
    public Variable findByIdVariable(Long id) {
        return variableRepository.findById(id).orElse(null);
    }
    @Override
    public Optional<Variable> getVariableWithScores(Long id) {
        return variableRepository.findByIdWithScores(id);
    }
    @Override
    public Variable createVariable(Variable variable, long modelId) {
        Modele modele = modeleRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Modele not found"));

    variable.setModele(modele);

    return variableRepository.save(variable);
    }
    @Override
    public List<Variable> getAllVariable() {
        return variableRepository.findAll();
    }

    @Override
    public Variable getVariableById(long id) throws MissingEntity {
        Optional<Variable> variable = variableRepository.findById(id);
        if(!variable.isPresent()){
            throw new MissingEntity("Variable not found with ID : "+id);
        }
        return variable.get();
    }
    /*public Optional<Variable> getVariableWithScores(Long id) {
        return variableRepository.findById(id);
    }*/

    @Override
    public Variable updateVariable(Long id, Variable updatedVariable) {
        if (updatedVariable == null) {
            throw new IllegalArgumentException("La variable mise à jour est null.");
        }

        Variable existingVariable = variableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variable not found with id: " + id));
        existingVariable.setCode(updatedVariable.getCode());
        existingVariable.setDescription(updatedVariable.getDescription());
        existingVariable.setCoefficient(updatedVariable.getCoefficient());
        existingVariable.setType(updatedVariable.getType());
        return variableRepository.save(existingVariable);
    }
    @Override
    public Variable save(Variable variable) {
        return variableRepository.save(variable);
    }
    @Override
    public Variable findById(long id) {
        return variableRepository.findById(id).orElse(null);
    }
  /*  @Override
    public double calculateScore(List<String> values) {
        double note = 0.0;
        for (String value : values) {
            List<Score> scores = scoreVariableRepository.findByValeur(value);
            for (Score score : scores) {
                Variable variable = score.getVariable();
                note += score.getScore() * variable.getCoefficient();
            }
        }
        return note;
    }
*/
    @Override
    public List<ScoreDto> getScoresByVariableId(Long variableId) {
        List<Score> scores = scoreVariableRepository.findByVariable_Id(variableId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Modifiez le format selon vos besoins

        return scores.stream().map(score -> {
            ScoreDto scoreDto = new ScoreDto();
            scoreDto.setId(score.getId());
            scoreDto.setVariableId(score.getVariable().getId());
            scoreDto.setScore(score.getScore());
            scoreDto.setType(score instanceof NUMBER ? "NUMBER" :
                    score instanceof ENUMERATION ? "ENUMERATION" :
                            score instanceof INTERVALE ? "INTERVALE" :
                                    score instanceof DATE ? "DATE" : "UNKNOWN");

            // Mappage des valeurs spécifiques en fonction du type
            if (score instanceof NUMBER) {
                NUMBER svNumber = (NUMBER) score;
                scoreDto.setValeur(svNumber.getValeur());
            } else if (score instanceof ENUMERATION) {
                ENUMERATION svEnum = (ENUMERATION) score;
                scoreDto.setValeur(svEnum.getValeur());
            } else if (score instanceof INTERVALE) {
                INTERVALE svInterval = (INTERVALE) score;
                scoreDto.setVmin(svInterval.getvMin());
                scoreDto.setVmax(svInterval.getvMax());
            } else if (score instanceof DATE) {
                DATE svDate = (DATE) score;
                try {
                    // Assurez-vous que svDate.getValeur() retourne une chaîne de caractères
                    String dateString = String.valueOf(svDate.getValeur());
                    Date dateValue = dateFormat.parse(dateString);
                    scoreDto.setDate(dateValue); // Assurez-vous que dateValue est de type Date
                    scoreDto.setValeur(dateString); // Stockez également la valeur en tant que String si nécessaire
                } catch (ParseException e) {
                    e.printStackTrace(); // Gérer l'exception selon les besoins
                }
            }

            // Mettez à jour les autres champs selon les besoins
            return scoreDto;
        }).collect(Collectors.toList());
    }
    @Override
    public void deleteVariable(Long id) {
        if (variableRepository.existsById(id)) {
            variableRepository.deleteById(id);
        } else {
            throw new RuntimeException("Variable not found with ID: " + id);
        }
    }
    @Override
    public double calculatePonderationForVariable(Long variableId) {
        Optional<Variable> variableOpt = variableRepository.findById(variableId);

        if (variableOpt.isPresent()) {
            Variable variable = variableOpt.get();
            double coefficient = variable.getCoefficient();
            double totalCoefficient = variableRepository.findAll().stream()
                    .mapToDouble(Variable::getCoefficient)
                    .sum();

            return totalCoefficient > 0 ? coefficient / totalCoefficient : 0;
        }

        return 0;
    }

    @Override
    public List<VariableDto> getVariablesByModeleId(Long modeleId) {
        return variableRepository.findByModeleIdAndUsedTrue(modeleId)
                .stream()
                .map(variable -> {
                    // Directly populate VariableDto
                    VariableDto dto = new VariableDto();
                    dto.setId(variable.getId());
                    dto.setCode(variable.getCode());
                    dto.setDescription(variable.getDescription());
                    dto.setCoefficient(variable.getCoefficient());
                    dto.setType(variable.getType());
                    dto.setModelId(variable.getModele().getId());

                    List<ScoreDto> scoreDtos = variable.getScores().stream()
                            .map(score -> {
                                ScoreDto scoreDto = new ScoreDto();
                                scoreDto.setId(score.getId());
                                scoreDto.setScore(score.getScore());
                                // Map based on Score type
                                if (score instanceof NUMBER) {
                                    scoreDto.setType("NUMBER");
                                    scoreDto.setNum(((NUMBER) score).getValeur());
                                } else if (score instanceof ENUMERATION) {
                                    scoreDto.setType("ENUMERATION");
                                    scoreDto.setEnumeration(((ENUMERATION) score).getValeur());
                                } else if (score instanceof INTERVALE) {
                                    scoreDto.setType("INTERVALE");
                                    scoreDto.setVmin(((INTERVALE) score).getvMin());
                                    scoreDto.setVmax(((INTERVALE) score).getvMax());
                                } else if (score instanceof DATE) {
                                    scoreDto.setType("DATE");
                                    scoreDto.setDate(((DATE) score).getValeur());
                                }
                                return scoreDto;
                            })
                            .collect(Collectors.toList());

                    dto.setScores(scoreDtos);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Variable> getVariablesByActiveModele() {
        Optional<Modele> activeModele = modeleRepository.findByUsedTrue();

        if (!activeModele.isPresent()) {
            throw new EntityNotFoundException("Aucun modèle activé trouvé.");
        }

        return variableRepository.findByModeleIdAndUsedTrue(activeModele.get().getId());
    }

}
