package com.pfe.Bank.service;

import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.NotationRepository;
import com.pfe.Bank.repository.ResponseRepository;
import com.pfe.Bank.repository.VariableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.pfe.Bank.model.Type.*;
import static com.pfe.Bank.model.Type.DATE;

@RequiredArgsConstructor
@Service
public class NotationService {
    @Autowired
    private NotationRepository notationRepository;
    @Autowired
    private VariableRepository variableRepository;
    @Autowired
    private ResponseRepository responseRepository;

    public Notation createNotation(Notation notation){
        List<Response> responses = notation.getResponses();

        notation.setResponses(null);

        Notation savedNotation = notationRepository.save(notation);

        for(Response response : responses){
            response.setNotation(savedNotation);
        }
        notation.setResponses(responses);
        notationRepository.save(notation);
        return notation;
    }

    public Notation updateNotation(NotationDto notation){
        Notation notation1 = notationRepository.findById(notation.getId()).get();

        List<Response> responseList = notation1.getResponses();
        List<Response> updateResponses = notation.getResponses();

        /*for(Response response: responseList){
            System.out.println(response.getId());

            responseRepository.deleteById(response.getId());
        }*/

        for(int i=0;i<responseList.size();i++){
            responseList.get(i).setResponse(updateResponses.get(i).getResponse());
        }


        /*List<Response> responses = notation.getResponses();
        for(Response response : responses){
            response.setNotation(notation1);
        }*/

        notation1.setResponses(responseList);

        return notationRepository.save(notation1);
    }

    public List<VariableResponse> getInProgress(long id){
        List<Response> responses = notationRepository.findById(id).get().getResponses();
        List<VariableResponse> variableResponses = new ArrayList<>();
        List<Long> done = new ArrayList<>();

        for(Response response : responses){
            Variable variable = variableRepository.findById(response.getVariableId()).get();
            variableResponses.add(new VariableResponse(variable.getId(),variable.getCode(),variable.getDescription(),variable.getCoefficient(),response.getResponse(),variable.getType()));
            done.add(variable.getId());
        }

        List<Variable> variables = variableRepository.findAll();

        for(Variable variable : variables){
            if(! done.contains(variable.getId())) {
                variableResponses.add(new VariableResponse(variable.getId(),variable.getCode(),variable.getDescription(),variable.getCoefficient(),null,variable.getType()));
            }
        }

        return variableResponses;
    }

    public List<NotationQuest> getTerminated(){
        List<Notation> notations = notationRepository.findByStatus(ResponseStatus.DONE);

        return notations.stream().map(notation -> {
            List<ResponseQuest> responseQuests = notation.getResponses().stream()
                    .map(response -> new ResponseQuest(response.getId(), response.getVariableId(), response.getResponse(), variableRepository.findById(response.getVariableId()).get().getDescription()))
                    .toList();

            return new NotationQuest(notation.getId(),notation.getStatus(),notation.getNote(),responseQuests);
        }).toList();
    }

    public List<Notation> getInProgress(){
        return notationRepository.findByStatus(ResponseStatus.IN_PROGRESS);
    }

    public Notation determineNote(Notation notation){
        Double note = notation.getResponses().stream()
                .map(this::calculateNote)
                .reduce(0.0,Double::sum);
        System.out.println(note);
        notation.setNote(note);

        List<Response> responses = notation.getResponses();

        notation.setResponses(null);

        Notation savedNotation = notationRepository.save(notation);

        for(Response response : responses){
            response.setNotation(savedNotation);
        }
        notation.setResponses(responses);
        notationRepository.save(notation);
        return notation;
    }

    private double calculateNote(Response response){
        Variable variable = variableRepository.findById(response.getVariableId()).get();
        Double score = null;

        if(NUMBER.equals(variable.getType())){
            score = variable.getScores().stream()
                    .filter(score1 ->  isMatchingNumber(score1,response))
                    .findFirst()
                    .get().getScore();
        }

        if (ENUMERATION.equals(variable.getType())){
            score = variable.getScores().stream()
                    .filter(score1 -> isMatchingEnumeration(score1,response))
                    .findFirst()
                    .get().getScore();
        }

        if(INTERVALE.equals(variable.getType())){
            score = variable.getScores().stream()
                    .filter(score1 -> isBetweenInterval(score1,response))
                    .findFirst()
                    .get().getScore();
        }

        if(DATE.equals(variable.getType())){
            score = variable.getScores().stream()
                    .filter(score1 -> {
                        try {
                            return isMatchingDate(score1,response);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .findFirst()
                    .get().getScore();
        }

        return score * variable.getCoefficient();
    }

    public boolean isMatchingNumber(Score score, Response response) {
        double targetValue = Double.parseDouble(response.getResponse());
        NUMBER number = (NUMBER) score;

        return number.getValeur().equals(targetValue);
    }

    public boolean isMatchingEnumeration(Score score, Response response) {
        ENUMERATION enumeration = (ENUMERATION) score;
        System.out.println(score.getId());
        return enumeration.getValeur().equals(response.getResponse());
    }

    public boolean isBetweenInterval(Score score,Response response){
        INTERVALE intervale = (INTERVALE) score;
        double vMin = Double.parseDouble(intervale.getvMin());
        double vMax = Double.parseDouble(intervale.getvMax());

        double responseValue = Double.parseDouble(response.getResponse());

        return vMin >=  responseValue && responseValue <= vMax;
    }

    public boolean isMatchingDate(Score score , Response response) throws ParseException {
        DATE date = (DATE) score;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        Date responseDate = formatter.parse(response.getResponse());

        return date.getValeur().equals(responseDate);
    }
}
