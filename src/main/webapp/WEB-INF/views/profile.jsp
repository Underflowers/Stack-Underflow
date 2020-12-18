<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<c:if test="${updated == true}">
    <div class="success">
        <span><i class="fas fa-check mr-5"></i><b class="font-bold">${user.firstname} ${user.lastname}</b> successfully updated</span><br>
    </div>
</c:if>

<div class="container container-content">
    <div class="max-w-3xl m-auto">
        <div class="flex items-center justify-between">
            <h1 class="h1">${user.firstname} ${user.lastname}</h1>
            <a href="${pageContext.request.contextPath}/profile/edit" class="link">Edit profile</a>
        </div>
        <p>${user.email}</p>

        <h2 class="h2 mt-6">Scales points</h2>
        <c:if test="${scores.size() == 0}">
            No points for the moment
        </c:if>
        <div class="flex">
            <c:forEach var="scale" items="${scores}">
                <div class="profile-scale">
                    <div class="text-gray-700">${scale.pointScaleName}</div>
                    <div class="text-rausch text-xl text-center">${scale.score} <span class="text-sm">pts</span></div>
                </div>
            </c:forEach>
        </div>
        <!-- Statistics -->
        <hr class="my-8">

        <div class="flex flex-wrap justify-around">
            <div class="text-center">
                <h2 class="h2 mb-3">Questions</h2>
                <div class="circle circle--small">
                    ${questionsCount}
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
        <h2 class="h2">Last questions</h2>
        <c:forEach var="question" items="${questions.questions}">
            <jsp:include page="fragments/questionSummary.jsp">
                <jsp:param name="uuid" value="${question.uuid}"/>
                <jsp:param name="author" value="${question.author}"/>
                <jsp:param name="title" value="${question.title}"/>
                <jsp:param name="content" value="${question.content}"/>
                <jsp:param name="votes" value="${question.votes.count}"/>
                <jsp:param name="answers" value="${question.answers.answers.size()}"/>
                <jsp:param name="creationDate" value="${question.creationDate.toString()}"/>
            </jsp:include>
        </c:forEach>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
