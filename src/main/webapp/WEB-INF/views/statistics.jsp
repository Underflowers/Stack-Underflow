<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp" />

    <div class="container mx-auto text-center">

        <h1 class="pt-8 h1">Statistics</h1>

        <!-- Global statistics -->
        <div class="pt-8 flex flex-wrap">

            <div class="w-4/12">
                <h2 class="h2 mb-6">Users</h2>
                <div class="circle circle--big">
                    ${usersCount}
                </div>
            </div>

            <div class="w-4/12">
                <h2 class="h2 mb-6">Questions</h2>
                <div class="circle circle--big">
                    ${questionsCount}
                </div>
            </div>

            <div class="w-4/12">
                <h2 class="h2 mb-6">Answers</h2>
                <div class="circle circle--big">
                    ${answersCount}
                </div>
            </div>

        </div>

        <!-- Leaderboards -->
        <h1 class="mt-16 mb-8 pt-8 h1">Leaderboards</h1>
        <div class="flex justify-evenly">
            <!-- Reputation -->
            <div class="flex justify-center text-left flex-col">
                <h1 class="h1 text-3xl">${reputationLeaderboard.name}</h1>
                <c:forEach var="entry" items="${reputationLeaderboard.entries}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index == 0}">
                            <b class="flex justify-between text-arches text-xl">
                                <span>${loop.index + 1}. ${entry.firstname} ${entry.lastname} </span>
                                <span class="ml-2">${entry.score}pts</span>
                            </b>
                        </c:when>
                        <c:when test="${loop.index == 1 || loop.index == 2}">
                            <b class="flex justify-between text-xl">
                                <span>${loop.index + 1}. ${entry.firstname} ${entry.lastname} </span>
                                <span class="ml-2">${entry.score}pts</span>
                            </b>
                        </c:when>
                        <c:otherwise>
                            <b class="flex justify-between">
                                <span>${loop.index + 1}. ${entry.firstname} ${entry.lastname} </span>
                                <span class="ml-2">${entry.score}pts</span>
                            </b>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <!-- Activity -->
            <div>
                <h1 class="h1 text-3xl">${activityLeaderboard.name}</h1>
                <c:forEach var="entry" items="${activityLeaderboard.entries}" varStatus="loop">
                    <%-- TODO --%>
                </c:forEach>
            </div>
        </div>
    </div>


<jsp:include page="fragments/footer.jsp" />
