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
        You can see all questions <a class="questionsLink" href="${pageContext.request.contextPath}/questions">here</a>
    </p>

    <p>
        Register <a class="registerLink" href="${pageContext.request.contextPath}/register">here</a>
    </p>

    <p>
        Login <a class="loginLink" href="${pageContext.request.contextPath}/login">here</a>
    </p>
</body>
</html>
