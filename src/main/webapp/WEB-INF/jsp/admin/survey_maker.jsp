<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<c:url value="../../../static/js/survey_maker.js" var="surveyMakerJS"/>
<script src="${surveyMakerJS}"></script>

<script>
    $(document).ready(function () {

//        console.log(JSON.stringify(survey));

        $('#commentNo').on('click', function () {
            $('#textAreaChoicesContainer').show();
        });

        $('#commentYes').on('click', function () {
            $('#textAreaChoicesContainer').hide();
        })

    });
</script>

<div class="container">

    <div id="main-wrapper" class="col-lg-12">

        <h2>Survey Maker: Input new Survey details below</h2>

        <hr class="astonBlueBackground">

        <div id="surveyContainer">
            <%--Survey Name--%>
            <div class="form-group">
                <label for="inputSurveyName" class="col-lg-2 control-label">Survey Name</label>
                <div class="col-lg-10">
                    <input type="text" class="form-control" id="inputSurveyName"
                           placeholder="Input Survey Name">
                </div>
            </div>

            <%--Survey Type--%>
            <div class="form-group">
                <label for="inputSurveyType" class="col-lg-2 control-label">Survey Type</label>
                <div class="col-lg-10">
                    <input type="text" class="form-control" id="inputSurveyType"
                           placeholder="Input Survey Type">
                </div>
            </div>

            <%--display Questions--%>
            <div id="questionsContainer" class="col-lg-12">
                <p>Questions:</p>
                <ul class="list-group">
                </ul>
            </div>

            <hr class="astonBlueBackground">

            <div class="row">
                <div class="col-lg-1">
                    <button type="button" class="btn btn-danger" onclick="window.location.reload()">Reset</button>
                </div>
                <div class="col-lg-1">
                    <button onclick="insertQuestion()" class="btn btn-success">Add Question</button>
                </div>
            </div>

            <hr class="astonBlueBackground">

            <button onclick="saveSurvey()" class="astonBlueBackground btn text-light">Save Survey</button>

        </div>
        <hr class="astonBlueBackground gimmeSpaceFloor">

    </div>


    <div class="modal" id="questionModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header astonBlueBackground text-light">
                    <h4 class="modal-title">Question Details</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="questionForm">
                        <fieldset>

                            <hidden id="questionId"/>
                            <hidden id="questionVersion"/>

                            <div class="form-group">
                                <label for="inputQuestionText" class="col-lg-2 control-label">Question</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" id="inputQuestionText"
                                           placeholder="Input Question Text">
                                </div>
                            </div>

                            <p>Will the answer to this question be a Comment?</p>
                            <div class="form-check">
                                <div class="col-lg-12">
                                    <input type="radio" id="commentNo" name="commentYesNo" value="false"
                                           class="radioChoice commentNo" checked="checked">
                                    <label for="commentNo" class="form-check-label">No</label>

                                    <input type="radio" id="commentYes" name="commentYesNo" value="true"
                                           class="radioChoice commentYes">
                                    <label for="commentYes" class="form-check-label">Yes</label>
                                </div>
                            </div>

                            <div class="form-group" id="textAreaChoicesContainer">
                                <label for="textAreaChoices" class="col-lg-2 control-label">Choices</label>
                                <div class="col-lg-10">
                                    <textarea class="form-control" rows="4" id="textAreaChoices"
                                              placeholder="Choices"></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn astonBlueBackground text-light" onclick="addQuestion()">
                        Add Question
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>


<%@include file="../includes/footer.jsp" %>