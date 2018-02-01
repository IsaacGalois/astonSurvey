<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="col-lg-2"></div>

    <div class="col-lg-8">
        <h1>Admin Dashboard</h1>
        <hr>

        <%--todo: handle empty choice submission cases--%>

        <h2>Survey ${survey.name}</h2>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Question</th>
                <th scope="col">Choice</th>
                <th scope="col">Total Number of Submissions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="currQuestionNum" value="1"/>
            <c:set var="currRowInStatArray" value="0"/>
            <c:forEach var="question" items="${survey.questions}">
                <c:forEach var="choice" items="${question.choices}">
                    <tr>
                        <td scope="col">${question.id == statArray[currRowInStatArray][0] ? currQuestionNum : ""}</td>
                        <td scope="col">${question.id == statArray[currRowInStatArray][0] ? question.questionText : ""}</td>
                        <td scope="col">${choice.choiceText}</td>
                        <c:choose>
                            <c:when test="${question.id == statArray[currRowInStatArray][0]}">
                                <td scope="col">${statArray[currRowInStatArray][2]}</td>
                                <c:set var="currRowInStatArray" value="${currRowInStatArray + 1}"/>
                            </c:when>
                            <c:otherwise>
                                <td scope="col">0</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>

                </c:forEach>
                <tr>
                    <td scope="col"></td>
                    <td scope="col"></td>
                    <td scope="col">(Empty)</td>
                    <td scope="col">${statArray[currRowInStatArray][3]}</td>
                </tr>
                <c:set var="currQuestionNum" value="${currQuestionNum + 1}"/>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="col-lg-2"></div>
</div>


<%@include file="../includes/footer.jsp" %>