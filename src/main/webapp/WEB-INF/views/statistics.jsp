<jsp:include page="fragments/header.jsp" />

    <div class="container mx-auto">

        <h1 class="pt-8 h1">Statistics</h1>

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
    </div>



<jsp:include page="fragments/footer.jsp" />
