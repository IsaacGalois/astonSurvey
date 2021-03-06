<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>
<div class="container">
    <div class="jumbotron">
        <h1 class="text-center">Welcome to the AstonTech Survey Application</h1>
        <p>You've finally found what you were looking for, the Aston Technologies Survey Portal!
            Feel free to click the links below and have a look around. When you're ready you can take any number of surveys you want.
            Also don't hesitate to check out our statistics pages. These can show you interesting trends and aggregated results of previous survey entries,
            and you don't even have to take a survey first, nice!
        </p>
    </div>

    <div class="row">

        <div class="card bg-secondary col-lg-6">
            <div class="card-header astonBlueBackground text-light">Aston Tech Survey Hub</div>
            <div class="card-body">
                <h4 class="card-title">One-Stop Shop for Aston Tech Surveys</h4>
                <p class="card-text">Take a look at some of Aston Technologies' fine surveys, and take as many as you want!</p>
                <a href="${rootUrl}/surveyHub/" class="btn astonBlueBackground text-light">Visit Survey Hub</a>
            </div>
        </div>

        <div class="card bg-secondary col-lg-6">
            <div class="card-header astonBlueBackground text-light">Survey Research & Analytics</div>
            <div class="card-body">
                <h4 class="card-title">Meaningful Statistics & Analysis</h4>
                <p class="card-text">See some of the aggregated results of our surveys. These metrics are used to fine tune the way we do business to serve you better!</p>
                <a href="${rootUrl}/admin/statsHub" class="btn astonBlueBackground text-light">Visit Statistics Hub</a>
            </div>
        </div>
    </div>

    <hr class="astonBlueBackground">

    <div class="row">

        <div class="card bg-secondary col-lg-6">
            <div class="card-header astonBlueBackground text-light">Aston Tech Survey Hub</div>
            <div class="card-body">
                <h4 class="card-title">Make a Custom Survey</h4>
                <p class="card-text">Want to ask the burning questions only you can come up with? Have a go at it with our Custom Survey Maker!</p>
                <a href="${rootUrl}/admin/surveyMaker/" class="btn astonBlueBackground text-light">Visit Survey Maker</a>
            </div>
        </div>

        <div class="card bg-secondary col-lg-6">
            <div class="card-header astonBlueBackground text-light">This Space for Rent</div>
            <div class="card-body">
                <h4 class="card-title">TBD</h4>
                <p class="card-text"><i>(This space left intentionally blank)</i></p>
                <a href="${rootUrl}/" class="btn astonBlueBackground text-light">Visit Nowhere</a>
            </div>
        </div>
    </div>

    <hr class="astonBlueBackground gimmeSpaceFloor">

</div>


<%@include file="includes/footer.jsp" %>