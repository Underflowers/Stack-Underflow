<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp" />

<c:forEach var="error" items="${errors}">
    <p class="error">Error: ${error}</p>
</c:forEach>

<c:if test="${authUser != null}">
    <p class="connectedUser">Authenticated user: ${authUser.firstname} ${authUser.lastname}</p>
</c:if>

<div class="flex h-full">
    <div class="max-w-md m-auto">
        <h1 class="text-4xl font-bold text-rausch">Ask your question</h1>

        <form action="submitQuestion.do" method="post">
            <input id="title" name="title" type="text" placeholder="Title" class="input-text"/>
            <textarea id="content" name="content" type="text" placeholder="Write your question here..." class="form-textarea"></textarea>
            <br/>
            <button id="submitBtn" name="submitBtn" type="submit" class="btn btn--primary my-2">Submit</button>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />