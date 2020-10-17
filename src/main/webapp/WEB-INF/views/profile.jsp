<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">
    <div class="max-w-3xl m-auto">
        <div class="flex items-center justify-between">
            <h1 class="h1">${user.firstname} ${user.lastname}</h1>
            <a href="#" class="link">Edit profile</a>
        </div>
        <p>${user.email}</p>

        <hr class="my-8">

        <!-- Statistics -->

        <!-- User's questions -->
        <h2 class="h2">Your questions (${questions.questions.size()})</h2>
        <c:forEach var="question" items="${questions.questions}">
        <div class="card card--hover my-4 w-full" onclick="location.href='/question?uuid=${question.uuid}';">
            <h2 class="h2 textLimiter">${question.title}</h2>
            <p class="text-gray-700">${question.content}</p>
            <hr class="my-3 border-gray-300">
            <div class="oneLineContainer">
                <span class="subtitle">0 votes • 0 answers</span> <!-- TODO retrieve actual data -->
                <span class="subtitle">${question.creationDate.toString()}</span>
            </div>
        </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
