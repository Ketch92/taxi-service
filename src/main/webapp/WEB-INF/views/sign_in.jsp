<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi service</title>
</head>
<body>
<h1>Please, sign in to the service.</h1>

<h4 style="color: red">${errorMessage}</h4>

<form method="post" action="${pageContext.request.contextPath}/login">
    Login <input required type="text" name="login">
    Password <input required type="password" name="password">
    <button type="Sign in">Send</button>
</form>

<a href="${pageContext.request.contextPath}/drivers/add">Sign up</a>
<p>

</p>
</body>
</html>
