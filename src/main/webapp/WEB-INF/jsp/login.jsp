<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>

<hr class="astonBlueBackground">
<div class="container" style="background: gray">
    <div class="col-lg-12">
        <br><br>
        <form id="form" action="<c:url value='/login.do'/>" method="POST">

            <div class="row">
                <div class="col-lg-1">
                    <span class="text-white">Username:</span>
                </div>
                <div class="col-lg-11">
                    <input type="text" name="username" value=""/>
                </div>
            </div>

            <br>

            <div class="row">
                <div class="col-lg-1">
                    <span class="text-white">Password:</span>
                </div>
                <div class="col-lg-11">
                    <input type="password" name="password" value=""/>
                </div>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="$_csrf.token"/>

            <br>

            <div class="row">
                <div class="col-lg-1"></div>
                <div class="col-lg-11">
                    <input value="Login" name="submit" type="submit" class="btn astonBlueBackground"/>
                </div>
            </div>

            <c:if test="${not empty param.err}">
                <div class="msg-container error text-white">
                    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </div>
            </c:if>
            <c:if test="${not empty param.out}">
                <div class="msg-container logout text-white">
                    You've logged out successfully.
                </div>
            </c:if>
            <c:if test="${not empty param.time}">
                <div class="msg-container time text-white">
                    You've been logged out due to inactivity.
                </div>
            </c:if>

        </form>

        <br>

    </div>
</div>
<hr class="astonBlueBackground">

<%@include file="includes/footer.jsp" %>