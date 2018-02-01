<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="col-lg-2"></div>

    <div class="col-lg-8">
        <h1 class="text-center">Admin Dashboard</h1>
        <hr style="display: none">

        <h2>Survey ${survey.name}</h2>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Question</th>
                <th scope="col">Choice</th>
                <th scope="col" class="text-center">Total Number of Submissions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="currQuestionNum" value="1"/>
            <c:set var="currRowInStatArray" value="0"/>
            <c:forEach var="question" items="${survey.questions}">
                <c:forEach var="choice" items="${question.choices}">
                    <c:set var="statArrayRowIsForThisQuestion" value="${question.id == statArray[currRowInStatArray][0]}" />
                    <c:set var="statArrayRowIsForThisChoice" value="${choice.id == statArray[currRowInStatArray][1]}" />
                    <tr>
                        <td scope="col">${choice.id == 1 ? currQuestionNum : ""}</td>
                        <td scope="col">${choice.id == 1 ? question.questionText : ""}</td>
                        <td scope="col">${!choice.choiceText.equals("") ? choice.choiceText : "(Empty)"}</td>
                        <c:choose>
                            <%--check if this entry is a submission for this question and this choice, if so move to next row, otherwise entry must be zero.
                                    We must generate these because entries for choices not made are not put in the database --%>
                            <c:when test="${statArrayRowIsForThisQuestion && statArrayRowIsForThisChoice}">
                                <td scope="col" class="text-center">${statArray[currRowInStatArray][2]}</td>
                                <c:set var="currRowInStatArray" value="${currRowInStatArray + 1}"/>
                            </c:when>
                            <c:otherwise>
                                <td scope="col" class="text-center">0</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>

                </c:forEach>
                <c:set var="currQuestionNum" value="${currQuestionNum + 1}"/>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="col-lg-2"></div>
</div>


<%@include file="../includes/footer.jsp" %>