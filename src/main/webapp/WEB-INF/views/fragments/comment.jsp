<form action="comment.do" method="post">

    <input id="answerUuid" name="answerUuid" type="hidden" value="${param.answerUuid}"/>
    <input id="questionUuid" name="questionUuid" type="hidden" value="${param.questionuuid}" />
    <textarea id="content" name="content" type="text" placeholder="comment here..." class="input-text w-full" rows="1"></textarea>
    <br/>
    <button id="commentBtn" name="commentBtn" type="submit" class="btn btn--primary my-2">Comment</button>
</form>
