package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.*;
import com.aston.survey.domain.repositories.SurveySubmissionRepository;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.CommentService;
import com.aston.survey.domain.services.SurveyService;
import com.aston.survey.domain.services.SurveySubmissionService;
import com.aston.survey.domain.vo.SurveySubmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SurveySubmissionServiceImpl implements SurveySubmissionService {

    @Autowired
    private SurveySubmissionRepository surveySubmissionRepository;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ChoiceService choiceService;

    private CommentService commentService;

    public SurveySubmissionServiceImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public Iterable<SurveySubmission> listAllSurveySubmissions() {
        return surveySubmissionRepository.findAll();
    }

    @Override
    public SurveySubmission getSurveySubmissionById(Long id) {
        return surveySubmissionRepository.findOne(id);
    }

    @Override
    public SurveySubmission saveSurveySubmission(SurveySubmission surveySubmission) {
        return surveySubmissionRepository.save(surveySubmission);
    }

    @Override
    public Iterable<SurveySubmission> saveSurveySubmissionList(Iterable<SurveySubmission> surveySubmissionIterable) {
        return surveySubmissionRepository.save(surveySubmissionIterable);
    }

    @Override
    public void deleteSurveySubmission(Long id) {
        surveySubmissionRepository.delete(id);
    }

    @Override
    public SurveySubmission saveSurveySubmissionFromVO(SurveySubmissionVO surveySubmissionVO, Long surveyId) {
        HashMap<Question, Choice> answers = new HashMap<>();

        Survey survey = surveyService.getSurveyById(surveyId);
        List<Question> questionList = survey.getQuestions();

        String id = null;
        Question question = null;
        
        String[] choiceArray = surveySubmissionVO.getChoiceArray();

        //get Comment possibility and data
        Comment comment = null;
        int[] commentQuestionIndices = surveyService.getCommentQuestionIndicesById(surveyId);
        int currCommentIndex = -1;
        if(commentQuestionIndices.length > 0)
            currCommentIndex = 0;

        //go through choiceArray of SurveySubmissionVO and fetch Questions and apply their answers (Choices) to answers HashMap
        //  if the Choice is a Comment, handle that as a special case.
        if (choiceArray != null) {
            //walk through each choice in choiceArray
            for (int i = 0; i < choiceArray.length; i++) {
                id = choiceArray[i];
                if (id != null) {

                    question = questionList.get(i);

                    //check if this question choice is a Comment
                    if(currCommentIndex != -1 && i == commentQuestionIndices[currCommentIndex]) {
                            comment = handleComment(id);
                            answers.put(question, comment);
                            if(currCommentIndex+1 < commentQuestionIndices.length)
                                currCommentIndex++;
                    } else {
                        Choice choice = choiceService.getChoiceById(Long.parseLong(id));
                        answers.put(question, choice);
                    }
                } else {
                    //todo: replace with log entry
                    System.out.println("ERROR: Null questionId in SurveySubmissionVO.");
                }
            }
        } else  //todo replace with log entry
            System.out.println("WARN: Null choice array in VO.");

        SurveySubmission ret = new SurveySubmission(survey, answers);
        surveySubmissionRepository.save(ret);

        return ret;
    }

    //handles Comment cases. Returns a new Comment with given commentText or the empty comment (or an Error).
    public Comment handleComment(String newCommentText) {

        Comment ret = commentService.getEmptyComment();

        //make new Comment with newCommentText or return empty comment (Error Otherwise)
        if(newCommentText != null) {
            if(!newCommentText.isEmpty()) {
                ret = new Comment(newCommentText);
            }
        } else { //todo: replace with log entry
            System.out.println("ERROR: Comment Text Returned Null.");
        }

        return ret;
    }

}
