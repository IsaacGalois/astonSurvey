<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<script>
    $(document).ready(function () {

        var numQuestions = $('.questionRow').length;
        var choicePercent = (100 / numQuestions).toFixed(2);
        var progressionPercent = 0;
        var answeredQuestions = [];
        var numAnsweredQuestions = 0;

        $('.progress-text').text(numAnsweredQuestions+"/"+numQuestions+" ");

        //update progress bar on radio choice selection
        $('.radioChoice').on('change', function () {
            if ($.inArray(this.name, answeredQuestions) === -1) {
                progressionPercent += +(choicePercent.replace('%', ''));
                numAnsweredQuestions++;

                //todo: assumes page has less than 50 questions (adjust this later if paging is added)
                if (progressionPercent > 99.5) {
                    progressionPercent = 100;
                }

                $('.progress-bar').css({'width': progressionPercent.toFixed(2) + '%'});
                $('.progress-text').text(numAnsweredQuestions+"/"+numQuestions+" ");
                $('.progress-bar').text(progressionPercent.toFixed(2) + '%');
                answeredQuestions.push(this.name);
            }
        });

        //update progress bar on textArea click
        $('.surveyTextArea').on('click', function () {
            if ($.inArray(this.name, answeredQuestions) === -1) {
                progressionPercent += +(choicePercent.replace('%', ''));
                numAnsweredQuestions++;

                //todo: assumes page has less than 50 questions (adjust this later if paging is added)
                if (progressionPercent > 99.5) {
                    progressionPercent = 100;
                }

                $('.progress-bar').css({'width': progressionPercent.toFixed(2) + '%'});
                $('.progress-text').text(numAnsweredQuestions+"/"+numQuestions+" ");
                $('.progress-bar').text(progressionPercent.toFixed(2) + '%');
                answeredQuestions.push(this.name);
            }
        });

    });
</script>


<%--todo: add paging?--%>
<div class="container">
    <div>

        <div class="progress fixed-top" id="progress" style="width: 100%">
            <span class="progress-text"></span>
            <div id="progressBar" class="progress-bar progress-bar-striped progress-bar-animated bg-success"
                 role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 0%;color:#000000"></div>
        </div>

        <hr class="astonBlueBackground">

        <h1>${survey.type} - ${survey.name}</h1>
    </div>
    <div id="main-wrapper" class="col-lg-12">

        <c:set var="questionNum" value="1" scope="page"/>

        <div class="row">

            <div style="width: 100%">
                <form:form cssClass="form-horizontal" modelAttribute="surveySubmissionVO"
                           action="/submitSurvey/${survey.id}" method="post">

                    <fieldset>

                            <%--currComment index is a page variable that keeps track of which index on the commentIndexArray we are currently on.--%>
                        <c:set var="currCommentIndex" value="0"/>
                        <c:forEach var="question" items="${questions}">

                            <div class="form-group questionRow" id="${question.id}">
                                <legend><c:out value="${questionNum}"/>) ${question.questionText}</legend>

                                <c1:forEach var="choice" items="${question.choices}">

                                    <div class="form-check">

                                        <c1:choose>
                                            <%--Check if this question is a multiple choice question--%>
                                            <c1:when test="${commentIndexArray[currCommentIndex] != questionNum-1}">
                                                <%--check if it is the empty choice--%>
                                                <c:choose>
                                                    <%--not empty choice case--%>
                                                    <c:when test="${choice.id != emptyChoiceId}">
                                                        <form:radiobutton path="choiceArray[${questionNum-1}]"
                                                                          name="${question.id}"
                                                                          value="${choice.id}"
                                                                          class="radioChoice"></form:radiobutton>
                                                    </c:when>
                                                    <%--empty choice case--%>
                                                    <c:otherwise>
                                                        <form:radiobutton path="choiceArray[${questionNum-1}]"
                                                                          name="${question.id}"
                                                                          value="${choice.id}"
                                                                          checked="checked"
                                                                          style="display:none"
                                                                          class="radioChoice radioChoiceDefault"></form:radiobutton>
                                                    </c:otherwise>
                                                </c:choose>
                                                <label class="form-check-label">
                                                        ${choice.choiceText}
                                                </label>
                                            </c1:when>

                                            <%--Comment question case--%>
                                            <c1:otherwise>
                                                <%--check if it is the empty comment--%>
                                                <c:choose>
                                                    <c:when test="${choice.id == emptyCommentId}">
                                                        <%--empty comment case (should be only case)--%>
                                                        <form:textarea path="choiceArray[${questionNum-1}]"
                                                                       id="comment"
                                                                       name="${question.id}"
                                                                       placeholder="${choice.choiceText}"
                                                                       cssStyle="width: 60%"
                                                                       cssClass="surveyTextArea"
                                                                       rows="8"></form:textarea>
                                                    </c:when>
                                                    <%--empty choice case (should never get hit)--%>
                                                    <c:otherwise>
                                                        <%--todo: make a log entry (possible? post in form to controller?)--%>
                                                        <h1>ERROR: Non-Empty choice in a comment question!</h1>
                                                    </c:otherwise>
                                                </c:choose>

                                                <%--Update currCommentIndex, while ensuring it does not go out of bounds of currCommentIndex--%>
                                                <c:set var="currCommentIndex"
                                                       value="${currCommentIndex+1 == numComments ? currCommentIndex : currCommentIndex+1}"/>

                                            </c1:otherwise>
                                        </c1:choose>

                                    </div>

                                </c1:forEach>

                            </div>
                            <c:set var="questionNum" value="${questionNum + 1}" scope="page"/>
                            <hr class="astonBlueBackground">
                        </c:forEach>
                    </fieldset>

                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">

                            <form:button type="reset" value="reset" onclick="window.location.reload()"
                                         class="btn btn-default">Reset</form:button>
                            <form:button type="submit" value="save" class="btn astonBlueBackground text-light">Submit</form:button>
                        </div>
                    </div>

                </form:form>

            </div>
        </div>
        <div class="col-lg-12">
            <hr class="astonBlueBackground gimmeSpaceFloor">
        </div>
    </div>
</div>

<%@include file="../includes/footer.jsp" %>