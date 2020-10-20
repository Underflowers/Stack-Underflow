<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">
    <div class="max-w-3xl m-auto">


        <div class="card my-4 w-full">
            <span class="subtitle">${question.author} asks</span>
            <h2 class="h2 textLimiter">${question.title}</h2>
            <p class="text-gray-700">${question.content}</p>
            <hr class="my-3 border-gray-300">
            <div class="oneLineContainer">  
                <span class="subtitle">0 votes • 0 answers</span> <!-- TODO retrieve actual data -->
                <span class="subtitle">${question.creationDate.toString()}</span>
            </div>
        </div>
        <c:set var="comments" value="${question.comments.comments}" scope="request" />
        <jsp:include page="fragments/comment.jsp">
            <jsp:param name="questionuuid" value="${question.uuid}"/>
        </jsp:include>

        <h2 class="h2">Answers</h2>

        <c:forEach var="answer" items="${question.answers.answers}">
            <div class="card my-4 w-full">
                <span class="subtitle">${answer.author} says</span>
                <p>${answer.content}</p>
                <div class="oneLineContainer">
                    <span class="subtitle">${answer.createdAt.toString()}</span>
                </div>

                <c:set var="comments" value="${answer.comments.comments}" scope="request" />
                <jsp:include page="fragments/comment.jsp">
                    <jsp:param name="answerUuid" value="${answer.uuid}"/>
                    <jsp:param name="questionuuid" value="${question.uuid}"/>
                </jsp:include>
            </div>
        </c:forEach>

        <form action="answer.do" method="post">
            <input id="questionUuid" name="questionUuid" type="hidden" value="${question.uuid}" />
            <textarea id="content" name="content" type="text" placeholder="Write your answer here..." class="input-text w-full" rows="7"></textarea>
            <br/>
            <button id="submitBtn" name="submitBtn" type="submit" class="btn btn--primary my-2">Submit</button>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
