<%@include file="../includes/header.jsp" %>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="jumbotron">
        <h2>New Survey Successfully Added!</h2>
        <p>Your survey was added to the database. To view and/or take your survey click on "Survey Hub" button below.</p>
    </div>
    <div class="col-lg-12 row">
        <div class="col-lg-6">
            <a href="https://afternoon-chamber-68582.herokuapp.com/surveyHub/" class="btn astonBlueBackground text-light">Visit Survey Hub</a>
        </div>
        <div class="col-lg-6">
            <a href="https://afternoon-chamber-68582.herokuapp.com/statsHub/" class="btn astonBlueBackground text-light">Visit Statistics Hub</a>
        </div>
    </div>
</div>

<%@include file="../includes/footer.jsp" %>