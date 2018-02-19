<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c1" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c2" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>

    <%--FAVICON--%>     <%--todo: add favicon--%>
    <%--<link href="/static/images/favicon.ico" rel="icon" type="image/x-icon"/>--%>

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