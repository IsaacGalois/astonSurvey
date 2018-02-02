<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>
<div class="container">
    <div class="jumbotron">
        <h2>Welcome to AstonTech Survey Application</h2>
        <p>This application handles surveys for Aston Technologies.</p>
    </div>

    <div class="row" id="surveyButtons">

    <c:forEach var="survey" items="${surveys}">
        <%--todo hardcoded width of 4 here will need to change later--%>
        <div class="col-lg-4 text-center">
            <a href="http://localhost:8080/takeSurvey/${survey.id}" class="btn btn-primary">
                Take survey ${survey.id}
            </a>
        </div>

    </c:forEach>
    </div>

    <hr>

    <div class="row" id="adminPages">

        <c:forEach var="survey" items="${surveys}">
            <%--todo hardcoded width of 4 here will need to change later--%>
            <div class="col-lg-4 text-center">
                <a href="http://localhost:8080/adminStats/${survey.id}" class="btn btn-primary">
                    View Admin Stats ${survey.id}
                </a>
            </div>

        </c:forEach>
    </div>
</div>


<%@include file="includes/footer.jsp" %>