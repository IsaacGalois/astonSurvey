<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <%--left side empty framing column--%>
    <div class="col-lg-2"></div>

    <%--main content column--%>
    <div class="col-lg-8">
        <h1 class="text-center">Choice Frequency Overview</h1>
        <hr style="display: none">

        <h2 class="text-center">${survey.name}</h2>
        <%--table to display question/answer frequency statistics--%>
        <table class="table table-hover table-striped">
            <%--Make table column headers--%>
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Question</th>
                <th scope="col">Choice</th>
                <th scope="col" class="text-center">Total Number of Submissions</th>
            </tr>
            </thead>
            <tbody>

            <%--currQuestionNum is a page variable to keep track of which question number (1-based) of this survey we are on--%>
            <c:set var="currQuestionNum" value="1"/>
            <%--currRowInStatArray is a page variable to keep track of which row (0-based) in the statArray 2d matrix we are on--%>
            <c:set var="currRowInStatArray" value="0"/>
            <%--currCommentIndex is a page variable to keep track of which index in the commentIndexArray we are on--%>
            <c:set var="currCommentIndex" value="0"/>

            <%--Loop over all questions--%>
            <c:forEach var="question" items="${survey.questions}">
                <%--Loop over all choices in each question--%>
                <c:forEach var="choice" items="${question.choices}">
                    <%--Since statArray only includes values that have been submitted, statArrayRowIsForThisQuestion is a boolean that checks if there is a statArray entry for this question--%>
                    <c:set var="statArrayRowIsForThisQuestion"
                           value="${question.id == statArray[currRowInStatArray][0]}"/>
                    <%--Similar to statArrayRowIsForThisQuestion is a boolean that checks whether there is a statArray entry for this choice in this question.--%>
                    <c:set var="statArrayRowIsForThisChoice" value="${choice.id == statArray[currRowInStatArray][1]}"/>

                    <%--debug printoff of values--%>
                    <%--<% System.out.println("questionNum: "+pageContext.findAttribute("currQuestionNum")+--%>
                    <%--" currRowInStatArray: "+pageContext.findAttribute("currRowInStatArray")+--%>
                    <%--" currCommentIndex: "+pageContext.findAttribute("currCommentIndex")+--%>
                    <%--" statArrayRowIsForThisQuestion: "+pageContext.findAttribute("statArrayRowIsForThisQuestion")+"\t"+--%>
                    <%--" statArrayRowIsForThisChoice: "+pageContext.findAttribute("statArrayRowIsForThisChoice")--%>
                    <%--); %>--%>

                    <%--and it will grab the stats of the next question with a submitted choice. FIX!!--%>

                    <tr>
                        <c:choose>
                            <%--Check if this question is a multiple choice question--%>
                            <c:when test="${currQuestionNum-1 != commentIndexArray[currCommentIndex]}">
                                <%--Check if choice is the empty choice. If so we know it is the first entry of a new question so display currQuestionNum, questionText, and (Empty) in different columns.--%>
                                <td scope="col">${choice.id == emptyChoiceId ? currQuestionNum : ""}</td>
                                <td scope="col">${choice.id == emptyChoiceId ? question.questionText : ""}</td>
                                <td scope="col">${!choice.choiceText.equals("") ? choice.choiceText : "(Empty)"}</td>

                                <c1:choose>
                                    <%--Check if this entry is a submission for this question and this choice, if so move to next row, otherwise entry must be zero.
                                            We must generate these because entries for choices not made are not put in the database --%>
                                    <c1:when test="${statArrayRowIsForThisQuestion && statArrayRowIsForThisChoice}">
                                        <td scope="col" class="text-center">${statArray[currRowInStatArray][2]}</td>
                                        <c:set var="currRowInStatArray" value="${currRowInStatArray + 1}"/>
                                    </c1:when>
                                    <c1:otherwise>
                                        <td scope="col" class="text-center">0</td>
                                    </c1:otherwise>
                                </c1:choose>

                            </c:when>
                            <%--Otherwise it is a comment question--%>
                            <c:otherwise>
                                <%--For Comment Questions only display the frequency of empty comments--%>
                                <td scope="col">${currQuestionNum}</td>
                                <td scope="col">${question.questionText}</td>
                                <td scope="col">(Empty Comment)</td>

                                <c1:choose>
                                    <%--Check if this entry is a submission for this question and this choice, if so move to next row, otherwise entry must be zero.--%>
                                    <c1:when
                                            test="${statArrayRowIsForThisQuestion && choice.id == statArray[currRowInStatArray][1] && choice.id == emptyCommentId}">
                                        <td scope="col" class="text-center">${statArray[currRowInStatArray][2]}</td>
                                        <c:set var="currRowInStatArray" value="${currRowInStatArray + 1}"/>
                                    </c1:when>

                                    <c1:otherwise>
                                        <%--If first element of statArray for a comment question is not for the empty choice, then there are no submitted empty choices. (Because empty comment has lowest choiceId of all comments)--%>
                                        <c:if test="${statArray[currRowInStatArray][1] != emptyCommentId}"/>
                                        <td scope="col" class="text-center">0</td>
                                        <c:set var="currRowInStatArray" value="${currRowInStatArray + 1}"/>
                                    </c1:otherwise>
                                </c1:choose>

                                <%--Increase currCommentIndex if it will remain within the bounds of commentIndexArray--%>
                                <c:set var="currCommentIndex"
                                       value="${currCommentIndex == numComments ? currCommentIndex : currCommentIndex+1}"/>
                            </c:otherwise>

                        </c:choose>

                    </tr>

                </c:forEach>
                <%--Increase currQuestionNum--%>
                <c:set var="currQuestionNum" value="${currQuestionNum + 1}"/>
            </c:forEach>

            </tbody>
        </table>
        <div class="col-lg-12">
            <hr class="bg-primary" style="margin-bottom: 15%">
        </div>
    </div>

    <%--right side empty framing column--%>
    <div class="col-lg-2"></div>
</div>


<%@include file="../includes/footer.jsp" %>