package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.Question;
import com.aston.survey.domain.Survey;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.CommentService;
import com.aston.survey.domain.services.QuestionService;
import com.aston.survey.domain.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    //region AUTOWIRED SERVICES AND BASIC CRUD
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

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
    //endregion

    //region STATISTICS
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

        for (int i = 0; i < statMatrix.length; i++) {
            ArrayList<Long> row = new ArrayList<>();

//            row is a multiple choice, add it to cleanArray
            if (commentIdArray.length > 1 && statMatrix[i][0] != commentIdArray[currCommentIndex]) {
                for (int j = 0; j < statMatrix[0].length; j++) {
                    row.add(statMatrix[i][j]);
                }
                cleanArray.add(row);
            } else {
//                row is a comment row
//                check if row is the empty comment row, if so add it to cleanArray
                if (statMatrix[i][1] == emptyCommentId) {
                    for (int j = 0; j < statMatrix[0].length; j++) {
                        row.add(statMatrix[i][j]);
                    }
                    cleanArray.add(row);
                }

                //if we are not at the end of the statMatrix and the next row is for a different question,
                //  increase currCommentIndex while ensuring it stays within the bounds of commentIdArray
                if (i != statMatrix.length - 1 && statMatrix[i + 1][0] != commentIdArray[currCommentIndex])
                    currCommentIndex = currCommentIndex + 1 == commentIdArray.length ? currCommentIndex : currCommentIndex + 1;
            }
        }

        cleanMatrix = new long[cleanArray.size()][3];
        for (int i = 0; i < cleanArray.size(); i++) {
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
    //endregion

    //region AGGREGATE QUERIES AND COMMENT HELPERS
    @Override
    public long[][] getTotalQuestionSubmissionsBySurveyId(Long id) {
        List<Object[]> questionTotalArray = surveyRepository.getTotalQuestionSubmissionsById(id);
        long[][] ret = new long[questionTotalArray.size()][2];

        if (questionTotalArray.size() > 0) {
            for (int i = 0; i < questionTotalArray.size(); i++) {
                ret[i][0] = ((java.math.BigInteger) questionTotalArray.get(i)[0]).longValue();
                ret[i][1] = ((java.math.BigInteger) questionTotalArray.get(i)[1]).longValue();
            }
        } else {
            List<Question> questionList = surveyRepository.findOne(id).getQuestions();
            ret = new long[questionList.size()][2];
            for (int i = 0; i < questionList.size(); i++) {
                ret[i][0] = questionList.get(i).getId();
                ret[i][1] = 0l;
            }
        }

        return ret;
    }

    //This returns an array of the indices of the questions of a survey which are comment questions
    @Override
    public int[] getCommentQuestionIndicesById(Long id) {

        Survey survey = surveyRepository.findOne(id);
        List<Question> questions = survey.getQuestions();

        return questions.stream().filter(i -> i.getChoices().size() == 1).mapToInt(i -> questions.indexOf(i)).toArray();
    }

    //This returns an array of the questionIds of the questions of a survey which are comment questions
    @Override
    public long[] getCommentQuestionIdsBySurveyId(Long id) {
        Survey survey = surveyRepository.findOne(id);
        List<Question> questions = survey.getQuestions();

        return questions.stream().filter(i -> i.getChoices().size() == 1).mapToLong(i -> i.getId()).toArray();
    }

    @Override
    public Survey[][] getSurveysInTypes() {
        List<List<Survey>> surveyArray = new ArrayList<>();
        Survey[][] ret;
        List<Survey> sqlListing = surveyRepository.findAllByOrderByTypeAsc();
        List<Survey> row = new ArrayList<>();
        String thisType = null;
        int numTypes = 0;

//        for(Survey survey:sqlListing)
//            System.out.println(survey.toString());
//        System.out.println();

        for (Survey survey : sqlListing) {
            if (thisType == null) {
                thisType = survey.getType();
                numTypes++;
            }

            if (thisType == survey.getType())
                row.add(survey);
            else {
                surveyArray.add(row);
                thisType = survey.getType();
                row = new ArrayList<>();
                row.add(survey);
                numTypes++;
            }
        }
        surveyArray.add(row);

//        System.out.println("surveyArray: ");
//        for(List<Survey> surveys : surveyArray)
//            System.out.println(surveys.toString());
//        System.out.println();

        ret = new Survey[numTypes][];

        for (int i = 0; i < surveyArray.size(); i++) {
            Survey[] thisRow = new Survey[surveyArray.get(i).size()];
            for (int j = 0; j < surveyArray.get(i).size(); j++) {
                thisRow[j] = surveyArray.get(i).get(j);
            }
            ret[i] = thisRow;
        }

//        System.out.println("ret: ");
//        for (int i = 0; i < ret.length; i++) {
//            for (int j = 0; j < ret[i].length; j++){
//                System.out.println(ret[i][j]);
//            }
//            System.out.println();
//        }

        return ret;
    }

    @Override
    public long[][] getSurveySubmissionCounts() {
        List<Object[]> subCountArray = surveyRepository.getSurveySubmissionCountsOrderedByType();
        long[][] ret = new long[subCountArray.size()][2];

        for (int i = 0; i < subCountArray.size(); i++) {
            ret[i][0] = ((java.math.BigInteger) subCountArray.get(i)[0]).longValue();
            ret[i][1] = ((java.math.BigInteger) subCountArray.get(i)[2]).longValue();
        }

        return ret;
    }
    //endregion

    //region MAKER HELPER METHODS
    @Override
    public Survey addEmptyChoiceAndOrCommentToMakerQuestions(Survey survey) {
        List<Question> questionList = new ArrayList<>();

        List<Question> oldQuestionList = survey.getQuestions();

        for (int i=0;i<oldQuestionList.size();i++) {
            Question question = oldQuestionList.get(i);
            List<Choice> oldChoiceList = question.getChoices();
            if (oldChoiceList.size() == 1 && oldChoiceList.get(0).getChoiceText().equals("a comment")) {
                List<Choice> choiceList = new ArrayList<>();
                choiceList.add(commentService.getEmptyComment());
                question.setChoices(choiceList);
                questionList.add(question);
            } else {
                questionList.add(questionService.addEmptyChoiceToFrontOfChoiceList(question));
            }
        }

        survey.setQuestions(questionList);

        return survey;
    }

    //endregion
}