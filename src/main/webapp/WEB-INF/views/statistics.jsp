<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp" />

    <div class="container mx-auto text-center mb-16">

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
        <div class="flex items-start justify-evenly">
            <!-- Reputation -->
            <div class="flex justify-center flex-col">
                <h1 class="h1 text-3xl">${reputationLeaderboard.name}</h1>
                <c:forEach var="entry" items="${reputationLeaderboard.entries}" varStatus="loop">
                    <jsp:include page="fragments/leaderboardEntry.jsp">
                        <jsp:param name="name" value="${entry.firstname} ${entry.lastname}"/>
                        <jsp:param name="score" value="${entry.score}"/>
                        <jsp:param name="index" value="${loop.index}"/>
                    </jsp:include>
                </c:forEach>
            </div>
            <!-- Activity -->
            <div class="flex justify-center flex-col">
                <h1 class="h1 text-3xl">${activityLeaderboard.name}</h1>
                <c:forEach var="entry" items="${activityLeaderboard.entries}" varStatus="loop">
                    <jsp:include page="fragments/leaderboardEntry.jsp">
                        <jsp:param name="name" value="${entry.firstname} ${entry.lastname}"/>
                        <jsp:param name="score" value="${entry.score}"/>
                        <jsp:param name="index" value="${loop.index}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>
    </div>


<jsp:include page="fragments/footer.jsp" />
