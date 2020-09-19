<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stack Underflow - Home</title>
</head>
<body>
    <h1>Welcome to Stack Overflow - Home</h1>
    <p>${question.author} asks "${question.content}"</p>
    <p>Content : ${question.content}</p>

    <p>
        You can see all questions <a href="questions">here</a>
    </p>

    <p>
        Register <a href="register">here</a>
    </p>
</body>
</html>
