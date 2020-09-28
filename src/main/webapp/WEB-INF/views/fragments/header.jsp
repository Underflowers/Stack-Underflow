<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stack Underflow</title>
    <meta name="description" content="The HTML5 Herald">
    <meta name="author" content="SitePoint">

    <link href="${pageContext.request.contextPath}/assets/dist/style.css" rel="stylesheet">

    <script src="https://kit.fontawesome.com/75e6517d2f.js" crossorigin="anonymous"></script>
</head>
<body class="h-full flex flex-col">
    <header class="bg-white">
        <nav class="flex flex-wrap items-center justify-between p-6 border-b-2">
            <div class="container flex flex-wrap mx-auto items-center justify-between">
                <div class="flex items-center flex-shrink-0 text-hof mr-6">
                    <a class="inline-block" href="">
                        <span class="font-semibold text-xl tracking-tight">Stack Underflow</span>
                    </a>
                </div>
                <div class="block flex-grow flex items-center w-auto">
                    <div class="text-md lg:flex-grow">
                        <a href="questions" class="link link--nav">Questions</a>
                        <a href="#" class="link link--nav">Tags</a>
                    </div>
                    <div>
                        <a href="login" class="btn btn--small btn--primary">Login</a>
                        <a href="register" class="btn btn--small btn--secondary-border">Register</a>
                    </div>
                </div>
            </div>
        </nav>
    </header>
    <main class="flex-grow bg-gray-100">