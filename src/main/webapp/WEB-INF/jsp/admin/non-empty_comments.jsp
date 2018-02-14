<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container" id="mainContainer">

    <div class="col-lg-12">
        <h1 class="text-center">Non-Empty Comments</h1>

        <h2 class="text-center">${survey.type} - ${survey.name}</h2>

        <c:choose>
            <%--Only index the array if it contains actual entries--%>
            <c:when test="${numRows != 0}">
                <table class="table table-hover table-striped" id="commentsTable">
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">Question</th>
                        <th scope="col" class="text-center">Comment Submission</th>
                    </tr>
                    </thead>
                    <tbody>

                        <%--Lists to display non-empty comments by comment question (multiple choice questions ignored)--%>
                    <c:forEach var="row" items="${neCommentsArray}">
                        <tr>
                            <%--only display the question's text if it is the first row for that question--%>
                            <td scope="col" class="text-center">${!prevRow.equals(row[0]) ? row[0]:""}</td>
                            <td scope="col" class="text-center">${row[1]}</td>
                        </tr>
                        <%--page variable to check if this is the first occurance of a question--%>
                        <c:set var="prevRow" value="${row[0]}"/>
                    </c:forEach>
                    </tbody>
                </table>

            </c:when>
            <%--If survey contains to non-empty comment submissions alert the viewer--%>
            <c:otherwise>
                <p class="text-center">Sorry, there are currently no non-empty comment submissions for this survey.</p>
            </c:otherwise>
        </c:choose>


        <hr class="astonBlueBackground"/>
        <div class="col-lg-12 row">
            <div class="col-lg-6">
                <a href="https://afternoon-chamber-68582.herokuapp.com/surveyHub/"
                   class="btn astonBlueBackground text-light">Visit Survey Hub</a>
            </div>
            <div class="col-lg-6">
                <a href="https://afternoon-chamber-68582.herokuapp.com/admin/statsHub/"
                   class="btn astonBlueBackground text-light">Visit Statistics Hub</a>
            </div>
        </div>
        <div class="col-lg-12">
            <hr class="astonBlueBackground gimmeSpaceFloor">
        </div>
    </div>
</div>

<%@include file="../includes/footer.jsp" %>