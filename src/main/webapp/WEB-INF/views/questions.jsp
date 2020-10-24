<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">

    <div class="oneLineContainer mb-5">
        <h1 class="h1">All questions <span class="text-xl font-normal align-middle">(${questions.questions.size()})</span></h1>

        <c:if test="${authUser != null}">
            <a href="${pageContext.request.contextPath}/ask" class="btn btn--primary my-2">+ New Question</a>
        </c:if>
    </div>

    <div class="flex justify-center flex-wrap">
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
