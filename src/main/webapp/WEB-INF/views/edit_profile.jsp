<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp" />

<div class="flex h-full">
    <div class="max-w-md m-auto text-center">

        <h1 class="text-4xl font-bold text-rausch">Edit profile</h1>

        <form action="${pageContext.request.contextPath}/profile/edit.do" method="post">
            <input id="firstname" name="firstname" type="text" placeholder="Firstname" class="input-text" value="${user.firstname}"/>
            <input id="lastname" name="lastname" type="text" placeholder="Lastname" class="input-text" value="${user.lastname}"/>
            <input id="email" name="email" type="email" placeholder="Email address" class="input-text" value="${user.email}"/>
            <input id="oldPassword" name="oldPassword" type="password" placeholder="Old password" class="input-text"/>
            <input id="newPassword" name="newPassword" type="password" placeholder="New password" class="input-text"/>
            <input id="newPasswordRepeat" name="newPasswordRepeat" type="password" placeholder="Repeat new password" class="input-text"/>
            <br/>
            <button name="editBtn" type="submit" class="btn btn--primary my-2">Edit</button>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp" />