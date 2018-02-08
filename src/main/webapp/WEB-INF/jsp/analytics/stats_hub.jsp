<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="jumbotron">
        <h1 class="text-center">Aston Technologies Statistics Hub</h1>
        <h2>The Holy Grail!</h2>
        <p>Underneath each survey type is are buttons to Aston Technologies survey submission statistics.
            These statistics are the real bread and butter of our performance and efficiency analysis.
            Click any of these buttons below to see the aggregated results of our surveys.</p>
    </div>

    <c:set var="subCountArrayRow" value="0"/>

    <div id="main-wrapper" class="col-lg-12">
        <c:forEach var="type" items="${surveyTypeArray}">
            <div class="row">
                <div class="col-lg-6">
                    <h2 class="text-left">${type[0].type}</h2>
                </div>
                <div class="col-lg-6">
                    <h2 class="text-right">Total Submissions</h2>
                </div>
            </div>
            <hr class="astonBlueBackground">
            <div class="col-lg-12">
                <ul class="list-group">
                    <c1:forEach var="survey" items="${type}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="https://afternoon-chamber-68582.herokuapp.com/stats/${survey.id}"
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