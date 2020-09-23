<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Stack Underflow - Questions</title>
</head>
<body>

    <h1>All questions</h1>
    <h2>${questions.size()} questions</h2>

    <c:forEach items="${questions}" var="question">
        <b>${question.title}</b><br>
        ${question.content}<br>
        <i>by ${question.author}</i>
        <br><br>
    </c:forEach>
</body>
</html>
