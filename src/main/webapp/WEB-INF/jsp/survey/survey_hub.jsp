<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="jumbotron">
        <h1 class="text-center">Aston Technologies Survey Hub</h1>
        <h2>Finally, where all the magic happens!</h2>
        <p>Underneath each survey type is are buttons to Aston Technologies surveys.
            These surveys help us improve our performance as well our customer and employee satisfaction.
            Click any of these buttons below to take a survey and thanks for helping us out!</p>
    </div>

    <c:set var="subCountArrayRow" value="0"/>

    <div id="main-wrapper" class="col-lg-12">
        <c:forEach var="type" items="${surveyTypeArray}">
            <h2 class="text-center">${type[0].type}</h2>
            <hr class="astonBlueBackground">
            <div class="col-lg-12">
                <ul class="list-group">
                    <c1:forEach var="survey" items="${type}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="http://localhost:8080/takeSurvey/${survey.id}"
                               class="btn astonBlueBackground text-light">
                                    ${survey.name}
                            </a>
                            <span class="badge badge-primary badge-pill">${subCountsByTypeArray[subCountArrayRow][1]}</span>
                        </li>

                        <c:set var="subCountArrayRow" value="${subCountArrayRow + 1}"/>
                    </c1:forEach>
                </ul>
                <hr class="astonBlueBackground">
            </div>
        </c:forEach>
    </div>
    <div class="col-lg-12">
        <hr class="astonBlueBackground" style="margin-bottom: 15%">
    </div>
</div>


<%@include file="../includes/footer.jsp" %>