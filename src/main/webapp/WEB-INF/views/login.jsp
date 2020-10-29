<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp" />



<div class="flex h-full">
    <div class="max-w-md m-auto text-center">
        <h1 class="text-4xl font-bold text-rausch">Login</h1>

        <form action="${pageContext.request.contextPath}/login.do" method="post">
            <input id="email" name="email" type="email" placeholder="Email address" class="input-text"/>
            <input id="password" name="password" type="password" placeholder="Password" class="input-text"/>
            <br/>
            <button id="loginBtn" name="loginBtn" type="submit" class="btn btn--primary my-2">Login</button>
            <p class="my-2">
                <a href="${pageContext.request.contextPath}/register" class="link subtitle">Don't have an account?</a>
            </p>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />