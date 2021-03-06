<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp" />


<div class="flex h-full">
    <div class="max-w-3xl m-auto">
        <h1 class="text-4xl font-bold text-rausch">Ask your question</h1>

        <form action="${pageContext.request.contextPath}/ask.do" method="post">
            <input id="title" name="title" type="text" placeholder="Title" class="input-text w-full"/>
            <textarea id="content" name="content" type="text" placeholder="Write your question here..." class="input-text w-full" rows="7"></textarea>
            <br/>
            <button id="submitBtn" name="submitBtn" type="submit" class="btn btn--primary my-2">Submit</button>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />