<%@include file="includes/header.jsp" %>
<%@include file="includes/navbar.jsp" %>

<div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <hr class="astonBlueBackground">
    </div>
    <div class="col-lg-2"></div>
</div>

<div class="container astonBlueBackground">
    <div class="col-lg-12">
        <br><br>
        <form id="form" action="<c:url value='/login.do'/>" method="POST">

            <div class="row">
                <div class="col-lg-1">
                    <span>Username:</span>
                </div>
                <div class="col-lg-11">
                    <input type="text" name="username" value=""/>
                </div>
            </div>

            <br>

            <div class="row">
                <div class="col-lg-1">
                    <span>Password:</span>
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
                    <input value="Login" name="submit" type="submit" class="btn bg-secondary"/>
                </div>
            </div>

            <br>

            <c:if test="${not empty param.err}">
                <div class="msg-container error">
                    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </div>
            </c:if>
            <c:if test="${not empty param.out}">
                <div class="msg-container logout">
                    You've logged out successfully.
                </div>
            </c:if>
            <c:if test="${not empty param.time}">
                <div class="msg-container time">
                    You've been logged out due to inactivity.
                </div>
            </c:if>

        </form>

    </div>
</div>

<div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <hr class="astonBlueBackground">
    </div>
    <div class="col-lg-2"></div>
</div>

<%@include file="includes/footer.jsp" %>