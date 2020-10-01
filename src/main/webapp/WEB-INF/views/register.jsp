<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp" />

<div class="flex h-full">
    <div class="max-w-md m-auto text-center">
        <p class="subtitle">${usersCount} registered users</p>

        <c:if test="${errorMsg != null}">
            <p class="error">Error: ${errorMsg}</p>
        </c:if>

        <c:if test="${user != null}">
            <p class="connectedUser">User created: ${user.firstname} ${user.lastname}</p>
        </c:if>

        <h1 class="text-4xl font-bold text-rausch">Register</h1>

        <form action="register.do" method="post">
            <input id="firstname" name="firstname" type="text" placeholder="Firstname" class="input-text"/>
            <input id="lastname" name="lastname" type="text" placeholder="Lastname" class="input-text"/>
            <input id="email" name="email" type="email" placeholder="Email address" class="input-text"/>
            <input id="password" name="password" type="password" placeholder="Password" class="input-text"/>
            <input id="passwordRepeat" name="passwordRepeat" type="password" placeholder="Repeat password" class="input-text"/>
            <br/>
            <button type="submit" class="btn btn--primary my-2">Register</button>
            <p class="my-2">
                <a href="login" class="link subtitle">Already have an account?</a>
            </p>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />