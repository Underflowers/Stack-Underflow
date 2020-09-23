<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stack Underflow - Login</title>
</head>
<body>
<h1>Login</h1>
<form action="loginCommand" method="post">
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
