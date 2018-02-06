package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Question;
import com.aston.survey.domain.Survey;
import com.aston.survey.domain.repositories.CommentRepository;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.CommentService;
import com.aston.survey.domain.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private CommentService commentService;

    @Override
    public Iterable<Survey> listAllSurveys() {
        return surveyRepository.findAll();
    }

    @Override
    public Survey getSurveyById(Long id) {
        return surveyRepository.findOne(id);
    }

    @Override
    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public Iterable<Survey> saveSurveyList(Iterable<Survey> surveyIterable) {
        return surveyRepository.save(surveyIterable);
    }

    @Override
    public void deleteSurvey(Long id) {
        surveyRepository.delete(id);
    }

//    Returns the count of each choice by question in a survey. If cleanComments is true, it trims the non-empty comment submissions from the result
    @Override
    public long[][] getAnswerFrequencyBySurveyId(Long id) {
        List<Object[]> statReturned = surveyRepository.getAnswerCountsById(id);
        long[][] statMatrix = new long[statReturned.size()][];

        for (int i = 0; i < statReturned.size(); i++) {
            long[] row = new long[statReturned.get(i).length];
            Arrays.fill(row, 0l);
            statMatrix[i] = row;
            for (int j = 0; j < statReturned.get(i).length; j++) {
                statMatrix[i][j] = ((java.math.BigInteger) statReturned.get(i)[j]).longValue();
            }
        }

//        debug printoff of statArray
//        System.out.println("statArray: ");
//        for(long[] roe: statMatrix)
//            System.out.println(Arrays.toString(roe));
//        System.out.println();

        return statMatrix;
    }

    //This "cleans" or removes from output all rows which refer to comment questions in which the submission was
    // not the empty comment. (Makes for more concise and meaningful statistical results)
    @Override
    public long[][] cleanAnswerFrequencyMatrix(Long id, long[][] statMatrix) {
        long[][] cleanMatrix = null;
        ArrayList<ArrayList<Long>> cleanArray = new ArrayList<>();
        long[] commentIdArray = getCommentQuestionIdsBySurveyId(id);
        int currCommentIndex = 0;
        Long emptyCommentId = commentService.getEmptyComment().getId();

        for(int i=0;i<statMatrix.length;i++) {
            ArrayList<Long> row = new ArrayList<>();

//            row is a multiple choice, add it to cleanArray
            if(commentIdArray.length > 1 && statMatrix[i][0] != commentIdArray[currCommentIndex]) {
                for(int j=0;j<statMatrix[0].length;j++) {
                    row.add(statMatrix[i][j]);
                }
                cleanArray.add(row);
            } else {
//                row is a comment row
//                check if row is the empty comment row, if so add it to cleanArray
                if(statMatrix[i][1] == emptyCommentId) {
                    for(int j=0;j<statMatrix[0].length;j++) {
                        row.add(statMatrix[i][j]);
                    }
                    cleanArray.add(row);
                }

                //if we are not at the end of the statMatrix and the next row is for a different question,
                //  increase currCommentIndex while ensuring it stays within the bounds of commentIdArray
                if(i != statMatrix.length-1 && statMatrix[i+1][0] != commentIdArray[currCommentIndex])
                    currCommentIndex = currCommentIndex+1 == commentIdArray.length ? currCommentIndex : currCommentIndex+1;
            }
        }

        cleanMatrix = new long[cleanArray.size()][3];
        for(int i=0;i<cleanArray.size();i++) {
            Arrays.fill(cleanMatrix[i], 0l);
            for (int j = 0; j < cleanArray.get(i).size(); j++) {
                cleanMatrix[i][j] = cleanArray.get(i).get(j);
            }
        }

        //debug printoffs
//        System.out.println("cleanArray");
//        for(ArrayList<Long> row:cleanArray)
//            System.out.println(row.toString());
//
//        System.out.println("CleanMatrix: ");
//        for(int i=0;i<cleanMatrix.length;i++)
//            System.out.println(Arrays.toString(cleanMatrix[i]));
//        System.out.println();

        return cleanMatrix;
    }

    //This returns an array of the indices of the questions of a survey which are comment questions
    @Override
    public int[] getCommentQuestionIndicesById(Long id) {

        Survey survey = surveyRepository.findOne(id);
        List<Question> questions = survey.getQuestions();

        return questions.stream().filter(i -> i.getChoices().size()==1).mapToInt(i-> questions.indexOf(i)).toArray();
    }

    //This returns an array of the questionIds of the questions of a survey which are comment questions
    @Override
    public long[] getCommentQuestionIdsBySurveyId(Long id) {
        Survey survey = surveyRepository.findOne(id);
        List<Question> questions = survey.getQuestions();

        return questions.stream().filter(i -> i.getChoices().size()==1).mapToLong(i-> i.getId()).toArray();
    }
}