<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="oneLineContainer">
    <div class="subtitle mr-2">${param.count} votes</div>

    <c:if test="${authUser != null}">
        <form action="${pageContext.request.contextPath}/vote.do" method="post">
            <input id="type" name="voteType" type="hidden" value="up"/>
            <input id="answerUuid" name="answerUuid" type="hidden" value="${param.answerUuid}"/>
            <input id="questionUuid" name="questionUuid" type="hidden" value="${param.questionUuid}" />
            <input id="redirectUuid" name="redirectUuid" type="hidden" value="${param.redirectUuid}" />
            <c:choose>
                <c:when test="${param.isUpvote == true}">
                    <button id="upvoteBtn" name="upvoteBtn" type="submit" class="btn btn--primary mr-2">+</button>
                </c:when>
                <c:otherwise>
                    <button id="upvoteBtn" name="upvoteBtn" type="submit" class="btn btn--default mr-2">+</button>
                </c:otherwise>
            </c:choose>
        </form>

        <form action="${pageContext.request.contextPath}/vote.do" method="post">
            <input id="type" name="voteType" type="hidden" value="down"/>
            <input id="answerUuid" name="answerUuid" type="hidden" value="${param.answerUuid}"/>
            <input id="questionUuid" name="questionUuid" type="hidden" value="${param.questionUuid}" />
            <input id="redirectUuid" name="redirectUuid" type="hidden" value="${param.redirectUuid}" />
            <c:choose>
                <c:when test="${param.isDownvote == false}">
                    <button id="downvoteBtn" name="downvoteBtn" type="submit" class="btn btn--primary">-</button>
                </c:when>
                <c:otherwise>
                    <button id="downvoteBtn" name="downvoteBtn" type="submit" class="btn btn--default">-</button>
                </c:otherwise>
            </c:choose>
        </form>
    </c:if>
</div>