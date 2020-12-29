<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p class="flex justify-between text-left
    <c:choose>
        <c:when test="${param.index == 0}">
            text-arches text-xl font-bold">
                <span>${param.index + 1}. ${param.name}</span>
        </c:when>
        <c:when test="${param.index < 3}">
             text-lg font-bold">
                <span>${param.index + 1}. ${param.name}</span>
        </c:when>
        <c:otherwise>
            ">
                <span><b>${param.index + 1}.</b> ${param.name}</span>
        </c:otherwise>
    </c:choose>
    <span class="ml-2">${param.score}pts</span>
</p>