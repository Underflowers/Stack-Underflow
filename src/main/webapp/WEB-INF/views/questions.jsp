<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">

    <div class="oneLineContainer mb-5">
        <h1 class="h1">All questions <span class="text-xl font-normal align-middle">(${questions.questions.size()})</span></h1>

        <c:if test="${authUser != null}">
            <a href="ask" class="btn btn--primary my-2">+ New Question</a>
        </c:if>
    </div>

    <div class="flex justify-center flex-wrap">
        <c:forEach var="question" items="${questions.questions}">
                <div class="card my-4 w-full" onclick="location.href='#';"> <!-- TODO add link to question page-->
                    <span class="subtitle">${question.author} asks</span>
                    <h2 class="h2 textLimiter">${question.title}</h2>
                    <p class="textLimiter textLimiter--2 text-gray-700">${question.content}</p>
                    <hr class="my-3 border-gray-300">
                    <div class="oneLineContainer">
                        <span class="subtitle">0 votes • 0 answers</span>
                        <span class="subtitle">${question.creationDate.toString()}</span>
                    </div>
                </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
