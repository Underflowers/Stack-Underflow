<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Stack Underflow - Register</title>
</head>
<body>
    <h1>Register</h1>
    <c:forEach var="error" items="${errors}">
        <p class="error">Error: ${error}</p>
    </c:forEach>

    <form action="register.do" method="post">
        <label for="firstname">
            Firstname
            <input id="firstname" name="firstname" type="text"/>
        </label>
        <br/>
        <label for="lastname">
            Lastname
            <input id="lastname" name="lastname" type="text"/>
        </label>
        <br/>
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
        <label for="passwordRepeat">
            Repeat password
            <input id="passwordRepeat" name="passwordRepeat" type="password"/>
        </label>
        <br/>
        <button type="submit">Register</button>
        <br/>
        <a href="login">Already have an account?</a>
    </form>
</body>
</html>
