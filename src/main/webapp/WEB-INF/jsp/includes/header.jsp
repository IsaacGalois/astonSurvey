<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c1" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c2" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%--todo: clean up js and css imports below--%>

    <%--FAVICON--%>
    <%--<link href="/static/images/favicon.ico" rel="icon" type="image/x-icon"/>--%>

    <%--Bootstrap--%>
    <%--<link rel="stylesheet" href="/static/css/bootstrap.css" type="text/css">--%>

    <%--Jquery--%>
    <c:url value="/webjars/jquery/2.1.4/jquery.min.js" var="jquery"/>
    <script src="${jquery}"></script>

    <%--Bootstrap--%>
    <c:url value="/webjars/bootstrap/3.3.4/js/bootstrap.min.js" var="bootstrapJS"/>
    <script src="${bootstrapJS}"></script>

    <link rel="stylesheet" href="/static/css/bootswatch_yeti.css" type="text/css">

    <%--Custom--%>
    <link rel="stylesheet" href="/static/css/astonsurvey.css" type="text/css">

</head>
<body>
<%--<script src="/static/js/common.js" type="text/javascript"></script>--%>
<%--<script src="/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>--%>
<%--<script src="/static/js/bootstrap.js" type="text/javascript"></script>--%>

<%--<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>--%>
<%--<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>