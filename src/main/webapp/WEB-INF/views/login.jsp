<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Stack Underflow - Login</title>
</head>
<body>
<c:forEach var="error" items="${errors}">
    <p class="error">Error: ${error}</p>
</c:forEach>

<c:if test="${authUser != null}">
    <p class="connectedUser">Authenticated used: ${authUser.firstname} ${authUser.lastname}</p>
</c:if>

<h1>Login</h1>
<form action="login.do" method="post">
    <label for="email">
        Email
        <input id="email" name="email" type="email"/>
    </label>
    <br/>
    <label for="password">
        Password
        <input id="password" name="password" type="password"/>
    </label>
    <br/>
    <button type="submit">Login</button>
    <br/>
    <a href="register">Don't have an account?</a>
</form>
</body>
</html>
