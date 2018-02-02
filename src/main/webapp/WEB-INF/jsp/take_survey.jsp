<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>

<c:url value="../../static/js/take_survey.js" var="takeSurvey"/>
<%--todo: loading external js below doesnt work--%>
<%--<script src="${takeSurvey}"></script>--%>

<script>
    $(document).ready(function () {

        var numQuestions = $('.questionRow').length;
        var choicePercent = (100 / numQuestions).toFixed(2);
        var progressionPercent = 0;
        var answeredQuestions = [];

        $('.progress-text').text(progressionPercent + '%');

        //update progress bar on choice selection
        $('.radioChoice').on('change', function () {
            if ($.inArray(this.name, answeredQuestions) === -1) {
                progressionPercent += +(choicePercent.replace('%', ''));

                //todo: assumes page has less than 50 questions (adjust this later if paging is added)
                if (progressionPercent > 99.5) {
                    progressionPercent = 100;
                }

                $('.progress-bar').css({'width': progressionPercent + '%'});
                $('.progress-text').text(progressionPercent + '%');
                answeredQuestions.push(this.name);

                //debug printoffs
//                console.log("after update progressionPercent: " + progressionPercent);
//                console.log("width now: " + $('.progress-bar').css('width'));
            }
//            else {
//                console.log("already answered question");
//            }
        });

    });
</script>

<div class="container">
    <div>

        <div class="progress" id="progress" style="width: 100%">
            <span class="progress-text"></span>
            <div id="progressBar" class="progress-bar progress-bar-striped progress-bar-animated bg-success"
                 role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div>
        </div>

        <h1>${survey.type}</h1>
    </div>
    <div id="main-wrapper" class="col-lg-12">

        <c:set var="questionNum" value="1" scope="page"/>

        <div class="row">

            <div>
                <form:form cssClass="form-horizontal" modelAttribute="surveySubmissionVO"
                           action="/submitSurvey/${survey.id}" method="post">

                    <fieldset>

                        <c:forEach var="question" items="${questions}">

                            <div class="form-group questionRow" id="${question.id}">
                                <legend><c:out value="${questionNum}"/>) ${question.questionText}</legend>

                                <c1:forEach var="choice" items="${question.choices}">

                                    <div class="form-check">

                                            <%--<c1:choose>--%>
                                            <%--todo implement text area support on survey--%>
                                            <%--<c1:when test="${question.choices.size == 1}">--%>
                                            <%--<form:textarea path="choiceArray[${questionNum}]"--%>
                                            <%--id="comment"--%>
                                            <%--value="${choice.id}"></form:textarea>--%>
                                            <%--</c1:when>--%>
                                            <%--<c1:otherwise>--%>

                                            <%--check if it is the empty choice--%>
                                        <c:choose>
                                            <%--not empty choice case--%>
                                            <c:when test="${choice.id != 1}">
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

                                            <%--</c1:otherwise>--%>
                                            <%--</c1:choose>--%>

                                        <label class="form-check-label">
                                                ${choice.choiceText}
                                        </label>
                                    </div>

                                </c1:forEach>

                            </div>
                            <c:set var="questionNum" value="${questionNum + 1}" scope="page"/>
                            <hr>
                        </c:forEach>
                    </fieldset>

                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">

                            <form:button type="reset" value="reset" onclick="window.location.reload()"
                                         class="btn btn-default">Reset</form:button>
                            <form:button type="submit" value="save" class="btn btn-primary">Submit</form:button>
                        </div>
                    </div>

                </form:form>
                <%--</div>--%>
                <%--<div class="col-lg-1">--%>
                <%--</div>--%>

            </div>
        </div>
    </div>
</div>

<include

<%@include file="includes/footer.jsp" %>