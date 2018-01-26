<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>

<div class="container">
    <div>
        <h1>${survey.type}</h1>
    </div>
    <div id="main-wrapper" class="col-lg-10">
        <div class="col-lg-8">
            <form:form cssClass="form-horizontal" modelAttribute="surveySubmissionVO" action="/submitSurvey/${survey.id}">


                <c:forEach var="question" items="${questions}">

                    <fieldset class="form-group">
                        <legend>${question.questionText}</legend>

                        <c1:forEach var="choice" items="${question.choices}">

                            <div class="form-check">
                                <label class="form-check-label">
                                    <form:input class="form-check-input" name="optionsRadios" id="optionsRadios1"
                                           value="option1"
                                           checked="" type="radio">
                                        ${choice}
                                </label>
                            </div>
                        </c1:forEach>

                    </fieldset>
                    <hr>
                </c:forEach>

                <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                        <form:button type="reset" value="reset" class="btn btn-default">Reset</form:button>
                        <form:button type="submit" value="save" class="btn btn-primary">Save</form:button>
                    </div>
                </div>

            </form:form>
        </div>

    </div>
</div>

<%@include file="includes/footer.jsp" %>