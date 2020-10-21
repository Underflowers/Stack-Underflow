<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<c:if test="${updated == true}">
    <div class="success">
        <span class="material-icons">done</span>
        <span><b class="font-bold">${user.firstname} ${user.lastname}</b> successfully updated</span><br>
    </div>
</c:if>

<div class="container container-content">
    <div class="max-w-3xl m-auto">
        <div class="flex items-center justify-between">
            <h1 class="h1">${user.firstname} ${user.lastname}</h1>
            <a href="/edit-profile" class="link">Edit profile</a>
        </div>
        <p>${user.email}</p>

        <!-- Statistics -->
        <hr class="my-8">

        <div class="flex flex-wrap justify-around">
            <div class="text-center">
                <h2 class="h2 mb-3">Questions</h2>
                <div class="circle circle--small">
                    ${questions.questions.size()}
                </div>
            </div>
            <div class="text-center">
                <h2 class="h2 mb-3">Answers</h2>
                <div class="circle circle--small">
                    ${answersCount}
                </div>
            </div>

        </div>

        <hr class="my-8">

        <!-- User's questions -->
        <h2 class="h2">Your questions</h2>
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
