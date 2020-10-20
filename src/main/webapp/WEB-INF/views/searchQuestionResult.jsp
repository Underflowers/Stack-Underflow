<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">

    <div class="oneLineContainer mb-5">
        <h1 class="h1">${questions.questions.size()} question(s) found for "${searchTerm}"</span></h1>
    </div>

    <div class="flex justify-center flex-wrap">
        <c:forEach var="question" items="${questions.questions}">
            <div class="card card--hover my-4 w-full" onclick="location.href='/question?uuid=${question.uuid}';">
                <span class="subtitle">${question.author} asks</span>
                <h2 class="h2 textLimiter">${question.title}</h2>
                <p class="textLimiter textLimiter--2 text-gray-700">${question.content}</p>
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
