<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<%--todo: try and make colored emptyCommentButtons work below?--%>
<%--<script>--%>
    <%--$(document).on('ready', function () {--%>
        <%--$('.emptyCommentButton').each(function(i,obj) {--%>
            <%--var id = obj.id();--%>
            <%--var commentStateArray = ${commentState};--%>
            <%--var commentState = commentStateArray[id];--%>
            <%--if (commentState == 0) {--%>
                <%--$('.emptyCommentButton').addClass('btn-danger')--%>
            <%--}--%>
            <%--if (commentState == 1) {--%>
                <%--$('.emptyCommentButton').addClass('btn-warning')--%>
            <%--}--%>
            <%--else {--%>
                <%--$('.emptyCommentButton').addClass('astonBlueBackground')--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>

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
                    <h2 class="text-left">${type[0].type} (# submissions)</h2>
                </div>
                <div class="col-lg-6">
                    <h2 class="text-right">Non-Empty Comments</h2>
                </div>
            </div>
            <hr class="astonBlueBackground">
            <div class="col-lg-12">
                <ul class="list-group">
                    <c1:forEach var="survey" items="${type}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <a href="${rootUrl}/admin/stats/${survey.id}"
                                   class="btn astonBlueBackground text-light">
                                        ${survey.name}
                                </a>
                                <span class="badge badge-primary badge-pill">${subCountsByTypeArray[subCountArrayRow][1]}</span>
                            </div>
                            <div>
                                <a href="${rootUrl}/admin/non-emptyComments/${survey.id}"
                                   class="btn text-light astonBlueBackground">
                                    Non-Empty Comments
                                </a>
                            </div>
                        </li>

                        <c:set var="subCountArrayRow" value="${subCountArrayRow + 1}"/>
                    </c1:forEach>
                </ul>
                <hr class="astonBlueBackground">
            </div>
        </c:forEach>
    </div>
    <div class="col-lg-12">
        <hr class="astonBlueBackground gimmeSpaceFloor">
    </div>
</div>


<%@include file="../includes/footer.jsp" %>