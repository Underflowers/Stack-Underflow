<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/header.jsp" />

    <div class="container mx-auto">

        <h1 class="pt-8 h1">Statistics</h1>

        <!-- Global statistics -->
        <div class="pt-8 flex flex-wrap">

            <div class="w-4/12 text-center">
                <h2 class="h2 mb-6">Users</h2>
                <div class="circle circle--big">
                    ${usersCount}
                </div>
            </div>

            <div class="w-4/12 text-center">
                <h2 class="h2 mb-6">Questions</h2>
                <div class="circle circle--big">
                    ${questionsCount}
                </div>
            </div>

            <div class="w-4/12 text-center">
                <h2 class="h2 mb-6">Answers</h2>
                <div class="circle circle--big">
                    ${answersCount}
                </div>
            </div>

        </div>

        <!-- Leaderboards -->
        <div class="mt-8">
            <h1 class="h1">Leaderboards</h1>

            <h2 class="h2">${reputationLeaderboard.name}</h2>
            <c:forEach var="entry" items="${reputationLeaderboard.entries}">
                <p>${entry.firstname} ${entry.lastname}: ${entry.score}pts</p>
            </c:forEach>

            <h2 class="h2">${activityLeaderboard.name}</h2>
            <c:forEach var="entry" items="${activityLeaderboard.entries}">
                <p>${entry.firstname} ${entry.lastname}: ${entry.score}pts</p>
            </c:forEach>
        </div>
    </div>


<jsp:include page="fragments/footer.jsp" />
