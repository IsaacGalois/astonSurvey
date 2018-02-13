var survey = {
    "id" : null,
    "version" : null,
    "name" : null,
    "type" : null,
    "questions" : []
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

    //open modal
    $('#questionModal').modal('show');

};


function saveQuestion() {
    var questionText = $('#inputQuestionText').val();
    var choices = $('#textAreaChoices').val();

    var choiceArray = choices.split('\n');
    console.log(choiceArray);

    var question = {
        id: null,
        version: null,
        questionText: questionText,
        choices: []
        //     {
        //     id: 1,
        //     version: 0,
        //     choiceText: ""
        // }]
    };

    choiceArray.forEach(function(item) {
        question.choices.push(
            {
                id: null,
                version: null,
                choiceText: item
            }
        )
    });

    console.log(JSON.stringify(question));

    survey.questions.push(question);
    survey.name = $('#inputSurveyName').val();
    survey.type = $('#inputSurveyType').val();

    populateQuestions();

    $('#questionModal').modal('hide');

    console.log("Question:");
    console.log(JSON.stringify(question));

    // $.ajax({
    //     type: "post",
    //     data: JSON.stringify(question),
    //     url: "http://localhost:8080/api/questions/",
    //     async: true,
    //     dataType: "json",
    //     contentType: "application/json",
    //     success: function(dbResponse) {
    //         console.log("Db Response: ");
    //         console.log(dbResponse);
    //         // window.location.reload();
    //     },
    //     error: function(dbResponse) {
    //         alert("Could Not Save Question");
    //         console.log("Db Response: ");
    //         console.log(dbResponse);
    //     }
    // });


};

function populateQuestions() {
    $('#questionsContainer').find('ul').empty();

    $.each(survey.questions, function(qIndex,question) {
        $('#questionsContainer').find('ul').append(
            "<li id="+qIndex+">"+
            "<button type='button' class='btn btn-danger' onclick='removeQuestion("+qIndex+")'>X</button>" +
            "    " + question.questionText + " -   Choices: "
            // "<ul class='list-group'>"
        )
        $.each(question.choices, function(cIndex,choice) {
            $('#' + qIndex).append(
            // $('#questionsContainer').find('li').append(
                choice.choiceText + ", "
            )
        });
        $('#questionsContainer').append(
            "</li>\n"+
            "</ul>"
        )
    });
}

function saveSurvey() {

    var surveyToSave = {
        "id" : null,
        "version" : null,
        "name" : $('#inputSurveyName').val(),
        "type" : $('#inputSurveyType').val(),
        "questions" : survey.questions
    }

    console.log("Survey:");
    console.log(JSON.stringify(surveyToSave));

    var url = window.location.href;
    console.log("url: "+url);

    var surveyPostUrl = url.charAt(4) === 's' ? "https://localhost:8080/api/surveys/" : "http://localhost:8080/api/surveys/";

    console.log("surveyPostUrl: "+surveyPostUrl);

    $.ajax({
            type: "post",
            data: JSON.stringify(surveyToSave),
            url: surveyPostUrl,
            async: true,
            dataType: "json",
            contentType: "application/json",
            success: function(dbResponse) {
                console.log("Db Response: ");
                console.log(dbResponse);
                window.location.href = "/admin/addSurvey";
            },
            error: function(dbResponse) {
                alert("Could Not Save Survey");
                console.log("Db Response: ");
                console.log(dbResponse);
            }
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

// todo: add comment answer functionality
// todo: test admin lock-down, possibly eliminate choice and question rest endpoints
// todo: make survey not a global item but an iffy that has getters and setters?