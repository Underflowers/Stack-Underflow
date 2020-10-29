<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card card--hover my-4 w-full" onclick="location.href='/question?uuid=${param.uuid}';">
    <span class="subtitle">${param.author} asks</span>
    <h2 class="h2 textLimiter">${param.title}</h2>
    <p class="textLimiter textLimiter--2 text-gray-700">${param.content}</p>
    <hr class="my-3 border-gray-300">
    <div class="oneLineContainer">
        <span class="subtitle">${param.votes} votes • ${param.answers} answers</span>
        <span class="subtitle">${param.creationDate}</span>
    </div>
</div>
