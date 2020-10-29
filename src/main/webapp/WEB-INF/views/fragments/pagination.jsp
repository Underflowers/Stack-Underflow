<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="oneLineContainer mu-5">
    <c:if test="${!param.previousPage.equals('-1')}">
        <a href="${pageContext.request.contextPath}/${param.page}/${param.previousPage}" class="btn btn--primary my-2"><< Previous</a>
    </c:if>

    <c:if test="${!param.nextPage.equals('-1')}">
        <a href="${pageContext.request.contextPath}/${param.page}/${param.nextPage}" class="btn btn--primary my-2">Next >></a>
    </c:if>
</div>
