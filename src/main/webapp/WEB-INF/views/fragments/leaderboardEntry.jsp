<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.index == 0}">
        <b class="flex justify-between text-left text-arches text-xl">
            <span>${param.index + 1}. ${param.name}</span>
            <span class="ml-2">${param.score}pts</span>
        </b>
    </c:when>
    <c:when test="${param.index == 1 || param.index == 2}">
        <b class="flex justify-between text-left text-xl">
            <span>${param.index + 1}. ${param.name}</span>
            <span class="ml-2">${param.score}pts</span>
        </b>
    </c:when>
    <c:otherwise>
        <p class="flex justify-between text-left">
            <span><b>${param.index + 1}.</b> ${param.name}</span>
            <span class="ml-2">${param.score}pts</span>
        </p>
    </c:otherwise>
</c:choose>