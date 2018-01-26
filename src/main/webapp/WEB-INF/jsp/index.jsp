<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>
<div class="container">
    <div class="jumbotron">
        <h2>Welcome to AstonTech Survey Application</h2>
        <p>This application handles surveys for Aston Technologies.</p>
    </div>
    <div class="container">
        <div class="col-lg-4">
            <a href="http://localhost:8080/takeSurvey/1" class="btn btn-primary">
                Take survey 1
            </a>
        </div>
        <div class="col-lg-4">
            <a href="http://localhost:8080/takeSurvey/2" class="btn btn-primary">
                Take survey 2
            </a>
        </div>
        <div class="col-lg-4">
            <a href="http://localhost:8080/takeSurvey/3" class="btn btn-primary">
                Take survey 3
            </a>
        </div>
    </div>
</div>


<%@include file="includes/footer.jsp" %>