<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>

<div class="container">
    <div>
        <h1>${survey.type}</h1>
    </div>
    <div id="main-wrapper" class="col-lg-12">

        <c:set var="questionNum" value="0" scope="page"/>

        <div class="row">
            <%--<div class="col-lg-1">--%>
            <%--</div>--%>
            <div>
                <form:form cssClass="form-horizontal" modelAttribute="surveySubmissionVO"
                           action="/submitSurvey/${survey.id}" method="post">

                    <fieldset>

                    <c:forEach var="question" items="${questions}">

                    <div class="form-group" id="${question.id}">
                        <legend><c:out value="${questionNum + 1}"/>) ${question.questionText}</legend>

                        <c1:forEach var="choice" items="${question.choices}">

                            <div class="form-check">


                                <form:radiobutton path="choiceArray[${questionNum}]"
                                                  name="${question.id}"
                                                  value="${choice.id}"></form:radiobutton>
                                <label class="form-check-label">
                                        ${choice.choiceText}
                                </label>
                            </div>

                        </c1:forEach>

                    </div>
                        <c:set var="questionNum" value="${questionNum + 1}" scope="page"/>
                    <hr>
                    </c:forEach>

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

<%@include file="includes/footer.jsp" %>