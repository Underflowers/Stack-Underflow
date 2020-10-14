<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<div class="container container-content">
    <div class="max-w-3xl m-auto">
        <h1 class="h2">${question.title}</h1>
        <p>${question.content}</p>

        <jsp:include page="fragments/comment.jsp">
            <jsp:param name="questionuuid" value="${question.uuid}"/>
        </jsp:include>
        
        <form action="answer.do" method="post">
            <input id="questionUuid" name="questionUuid" type="hidden" value="${question.uuid}" />
            <textarea id="content" name="content" type="text" placeholder="Write your answer here..." class="input-text w-full" rows="7"></textarea>
            <br/>
            <button id="submitBtn" name="submitBtn" type="submit" class="btn btn--primary my-2">Submit</button>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
