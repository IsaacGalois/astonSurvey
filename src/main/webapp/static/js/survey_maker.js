var survey = {
    "id": null,
    "version": null,
    "name": null,
    "type": null,
    "questions": []
}


// function populateMultipleChoice(questionNum) {
//
//     var idName = '#multipleChoiceContainer'+questionNum;
//
//     // console.log("idName: "+idName);
//     //
//     // console.log("mc1 test: "+$('#multipleChoiceContainer1').id)
//     // console.log("jquerytest: "+$('#multipleChoiceContainer'.concat(questionNum.toString())).id)
//
//     $('#multipleChoiceContainer'.concat(questionNum.toString())).append(
//         '<div id="choiceContainer_'+questionNum+'">\n' +
//         // hardcode 1 below because this will be the first choice. After that we call addChoice
//         '    <div id="choice"'+questionNum+'_1'+'>\n' +
//         '        <div class="form-group">\n' +
//         // hardcode 1 below because this will be the first choice. After that we call addChoice
//         '            <label for="inputChoiceText1" class="col-lg-2 control-label">Choice 1</label>\n' +
//         '            <div class="col-lg-10">\n' +
//         // hardcode 1 below because this will be the first choice. After that we call addChoice
//         '                <input type="text" class="form-control" id="inputChoiceText1"\n' +
//         '                                       placeholder="Input Choice Text">\n' +
//         '            </div>\n' +
//         '        </div>'+
//         '    </div>\n' +
//         '</div>\n' +
//         '\n' +
//         // hardcode 2 below because this will be the first choice. After that we call addChoice
//         '<button class="astonBlueBackground text-light" onclick="addChoice('+questionNum+','+2+')">Add Choice</button>\n' +
//         '\n' +
//         // close .questionContainer1 from base jsp (not visible here)
//         // '</div>' +
//         '\n' +
//         '<hr>\n' +
//         '\n' +
//         '<div id="questionContainer'+(questionNum+1)+'"></div>\n' +
//         '\n' +
//         '<button class="astonBlueBackground text-light" onclick="addQuestion('+2+')">Add Question</button>'
//     );
// }

// function addChoice(questionNum,newChoiceNum) {
//
//     $('#multipleChoiceContainer'.concat(questionNum.toString())).append(
//         '<div id="choiceContainer_'+questionNum+'">\n' +
//         '    <div id="choice"'+questionNum+'_'+newChoiceNum+'>\n' +
//         '        <div class="form-group">\n' +
//         '            <label for="inputChoiceText1" class="col-lg-2 control-label">Choice 1</label>\n' +
//         '            <div class="col-lg-10">\n' +
//         '                <input type="text" class="form-control" id="inputChoiceText1"' +
//         '                                       placeholder="Input Choice Text">\n' +
//         '            </div>\n' +
//         '        </div>' +
//         '    </div>\n' +
//         '</div>\n' +
//         '\n' +
//         '<button class="astonBlueBackground text-light" onclick="addChoice('+questionNum+','+(newChoiceNum+1)+')">Add Choice</button>\n' +
//         '\n' +
//         '<hr>\n' +
//         '\n' +
//         '<div id="newQuestionContainer"></div>\n' +
//         '\n' +
//         '<button class="astonBlueBackground text-light" onclick="addQuestion()">Add Question</button>'
//     );
// }

// function addQuestion(questionNum) {
//
//     $('#newQ').find('#choiceTail')
//         .append(
//             '<div class="form-group">'+
//             '    <label for="inputQuestionText'+questionNum+'" class="col-lg-2 control-label">Question '+questionNum+'</label>'+
//             '    <div class="col-lg-10">'+
//             '        <input type="text" class="form-control" id="inputQuestionText'+questionNum+'" placeholder="Input Question Text">'+
//             '    </div>'+
//             '</div>'
//         );
// }

function insertQuestion() {
    //clear fields in modal
    $('#questionId').val("");
    $('#questionVersion').val("");
    $('#inputQuestionText').val("");
    $('#textAreaChoices').val("");
    $('#commentNo').prop("checked", true);
    $('#commentYes').prop("checked", false);
    $('#textAreaChoicesContainer').show();

    //open modal
    $('#questionModal').modal('show');
};


function addQuestion() {
    var questionText = $('#inputQuestionText').val();
    var choices = $('#textAreaChoices').val();

    var choiceArray = choices.split('\n');
    // console.log(choiceArray);

    var hasSemiColon = false;

    for (index in choiceArray) {
        if (choiceArray[index].split(';').length > 1) {
            hasSemiColon = true;
        }
    }

    if (questionText !== "" && choices !== "" && choiceArray.length > 1 && !hasSemiColon) {
        var question = {
            id: null,
            version: null,
            questionText: questionText,
            choices: []
        };

        // console.log($('#commentNo').prop("checked"));
        // console.log($('#commentYes').prop("checked"));

        if ($('#commentNo').prop("checked")) {
            choiceArray.forEach(function (item) {
                question.choices.push(
                    {
                        id: null,
                        version: null,
                        choiceText: item
                    }
                )
            });
        }
        else {
            // console.log("a comment");
            question.choices.push(
                {
                    id: null,
                    version: null,
                    choiceText: "EMPTY COMMENT PL@C3H07D3R"
                }
            )
        }

        // console.log(JSON.stringify(question));

        survey.questions.push(question);
        survey.name = $('#inputSurveyName').val();
        survey.type = $('#inputSurveyType').val();

        populateQuestions();

        $('#questionModal').modal('hide');

        // console.log("Question:");
        // console.log(JSON.stringify(question));
    }
    else {
        var alertResponse = "Could not save Question for the following reason(s):\n\n";
        if (questionText === "") {
            alertResponse = alertResponse + "- Question Text is blank\n";
        }
        if (choices === "") {
            alertResponse = alertResponse + "- There are no Choices assigned to this Question\n";
        }
        if (choices.length === 1) {
            alertResponse = alertResponse + "- There is only one Choice assigned to this Question\n";
        }
        if (hasSemiColon) {
            alertResponse = alertResponse + "- There is a semicolon in one or more of your Choices";
        }

        alert(alertResponse);
    }

};

function populateQuestions() {
    $('#questionsContainer').find('ul').empty();

    $.each(survey.questions, function (qIndex, question) {
        $('#questionsContainer').find('ul').append(
            "<li id=" + qIndex + ">" +
            "<button type='button' class='btn btn-danger' onclick='removeQuestion(" + qIndex + ")'>X</button>" +
            "    " + question.questionText + " -   Choices: "
        )
        $.each(question.choices, function (cIndex, choice) {
            $('#' + qIndex).append(
                choice.choiceText + ", "
            )
        });
        $('#questionsContainer').append(
            "</li>\n" +
            "</ul>"
        )
    });
}

function removeQuestion(qIndex) {
    //ES2015 (ES6) Way
    survey.questions = survey.questions.filter(item => item !== survey.questions[qIndex])

    //Old School ES5 way
    // survey.questions = survey.questions.filter(function(item) {
    //     return item !== survey.questions[qIndex];
    // });

    populateQuestions();
}

function saveSurvey() {
    var surveyName = $('#inputSurveyName').val();
    var surveyType = $('#inputSurveyType').val();
    var questionsArray = survey.questions;

    var surveyNameHasSemiColon = false;
    var surveyTypeHasSemiColon = false;

    if (surveyName.split(';').length > 1) {
        surveyNameHasSemiColon = true;
    }

    if(surveyType.split(';').length > 1) {
        surveyTypeHasSemiColon = true;
    }


    if (surveyName !== "" && surveyType !== "" && questionsArray.length > 0 && !surveyNameHasSemiColon && !surveyTypeHasSemiColon) {
        var surveyToSave = {
            "id": null,
            "version": null,
            "name": surveyName,
            "type": surveyType,
            "questions": questionsArray
        }

        // console.log("Survey:");
        // console.log(JSON.stringify(surveyToSave));

        // adapt for http (localhost) or https (Heroku)
        var url = window.location.href;
        var surveyPostUrl = url.charAt(4) === 's' ? "https://afternoon-chamber-68582.herokuapp.com/api/surveys/" : "http://localhost:8080/api/surveys/";

        $.ajax({
            type: "post",
            data: JSON.stringify(surveyToSave),
            url: surveyPostUrl,
            async: true,
            dataType: "json",
            contentType: "application/json",
            success: function (dbResponse) {
                // console.log("Db Response: ");
                // console.log(dbResponse);
                window.location.href = "/admin/addSurvey";
            },
            error: function (dbResponse) {
                alert("ERROR: Could Not Save Survey");
                // console.log("Db Response: ");
                // console.log(dbResponse);
            }
        });
    }
    else {
        var alertResponse = "Could not save Survey for the following reason(s):\n\n";
        if (surveyName === "") {
            alertResponse = alertResponse + "- Survey Name is blank\n";
        }
        if (surveyType === "") {
            alertResponse = alertResponse + "- Survey Type is blank\n";
        }
        if (questionsArray.length === 0) {
            alertResponse = alertResponse + "- There are no Questions assigned to this Survey\n";
        }
        if (surveyNameHasSemiColon) {
            alertResponse = alertResponse + "- Survey Name contains a semicolon\n";
        }
        if (surveyTypeHasSemiColon) {
            alertResponse = alertResponse + "- Survey Type has a semicolon";
        }

        alert(alertResponse);
    }

}

//todo: chrome renders this page as French because 'Reset' on button is capitalized?