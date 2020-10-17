<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stack Underflow</title>

    <meta charset="UTF-8">
    <meta name="description" content="The HTML5 Herald">
    <meta name="author" content="SitePoint">

    <link href="${pageContext.request.contextPath}/assets/dist/style.css" rel="stylesheet">

    <script src="https://kit.fontawesome.com/75e6517d2f.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="h-full flex flex-col">
    <header class="bg-white">
        <nav class="flex flex-wrap items-center justify-between p-6 border-b-2">
            <div class="container flex flex-wrap mx-auto items-center justify-between">
                <div class="flex items-center flex-shrink-0 text-hof mr-6">
                    <a class="inline-block" href="${pageContext.request.contextPath}/">
                        <span class="font-semibold text-xl tracking-tight">Stack Underflow</span>
                    </a>
                </div>
                <div class="block flex-grow flex items-center w-auto">
                    <div class="text-md lg:flex-grow">
                        <a href="questions" class="link link--nav">Questions</a>
                        <a href="statistics" class="link link--nav">Statistics</a>
                    </div>
                    <div class="flex items-center">
                        <c:choose>
                            <c:when test="${authUser != null}">
                                <span class="subtitle mr-2">${authUser.email}</span>
                                <form action="logout.do" method="post" class="m-0">
                                    <button type="submit" class="btn btn--small btn--secondary-border">Logout</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <a href="login" class="btn btn--small btn--primary mr-2">Login</a>
                                <a href="register" class="btn btn--small btn--secondary-border">Register</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </nav>
    </header>

    <c:forEach var="error" items="${errors}">
        <div class="error">
            <span class="material-icons">error_outline</span>
            <span><b class="font-bold">Error:</b> ${error}</span><br>
        </div>
    </c:forEach>

    <main class="flex-grow">