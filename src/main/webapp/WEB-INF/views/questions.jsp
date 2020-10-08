<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">

    <c:forEach var="error" items="${errors}">
        <p class="error">Error: ${error}</p>
    </c:forEach>
    <c:if test="${authUser != null}">
        <p class="connectedUser">Authenticated user: ${authUser.firstname} ${authUser.lastname}</p>
    </c:if>

    <div class="oneLineContainer mb-5">
        <h1 class="h1">All questions <span class="text-xl font-normal align-middle">(${questions.questions.size()})</span></h1>

        <c:if test="${authUser != null}">
            <a href="ask" class="btn btn--primary my-2">+ New Question</a>
        </c:if>
    </div>

    <div class="flex justify-center flex-wrap">
        <c:forEach var="question" items="${questions.questions}">
            <a class="w-full block" href="#"> <!-- TODO add link to detailed question page -->
                <div class="card my-4">
                    <span class="subtitle">${question.author} asks</span>
                    <h2 class="h2 textLimiter">${question.title}</h2>
                    <p class="textLimiter textLimiter--2 text-gray-700">${question.content}</p>
                    <hr class="my-3 border-gray-300">
                    <div class="oneLineContainer">
                        <span class="subtitle">0 votes • 0 answers</span>
                        <span class="subtitle">${question.creationDate.toString()}</span>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
