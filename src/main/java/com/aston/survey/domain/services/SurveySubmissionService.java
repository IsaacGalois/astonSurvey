package com.aston.survey.domain.services;

import com.aston.survey.domain.SurveySubmission;
import com.aston.survey.domain.vo.SurveySubmissionVO;

public interface SurveySubmissionService {

    Iterable<SurveySubmission> listAllSurveySubmissions();

    SurveySubmission getSurveySubmissionById(Long id);

    SurveySubmission saveSurveySubmission(SurveySubmission surveySubmission);

    Iterable<SurveySubmission> saveSurveySubmissionList(Iterable<SurveySubmission> surveySubmissionIterable);

    void deleteSurveySubmission(Long id);

    SurveySubmission saveSurveySubmissionFromVO(SurveySubmissionVO surveySubmissionVO, Long surveyId);

}
