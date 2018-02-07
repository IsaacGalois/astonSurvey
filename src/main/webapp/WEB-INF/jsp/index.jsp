<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>
<div class="container">
    <div class="jumbotron">
        <h2>Welcome to AstonTech Survey Application</h2>
        <p>This application handles surveys for Aston Technologies.</p>
    </div>

    <div class="row" id="surveyButtons">

        <div class="card bg-secondary col-lg-6">
            <div class="card-header blueBackground text-light">Aston Tech Survey Hub</div>
            <div class="card-body">
                <h4 class="card-title">One-Stop Shop for Aston Tech Surveys</h4>
                <p class="card-text">Take a look at some of Aston Technologies' fine surveys, and take as many as you want!</p>
                <a href="http://localhost:8080/surveyHub/" class="btn blueBackground text-light">Visit Survey Hub</a>
            </div>
        </div>

        <div class="card bg-secondary col-lg-6">
            <div class="card-header blueBackground text-light">Survey Research & Analytics</div>
            <div class="card-body">
                <h4 class="card-title">Meaningful Statistics & Analysis</h4>
                <p class="card-text">See some of the aggregated results of our surveys. These metrics are used to fine tune the way we do business to serve you better!</p>
                <a href="http://localhost:8080/statsHub" class="btn blueBackground text-light">Visit Stats Hub</a>
            </div>
        </div>
    </div>

    <hr>

</div>


<%@include file="includes/footer.jsp" %>