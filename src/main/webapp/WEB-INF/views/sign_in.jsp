<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi service</title>
</head>
<body>
<h1>Please, sign in to the service.</h1>

<form method="post" action=${pageContext.request.contextPath}"/login">
    Login <input required type="text" name="login">
    Password <input required type="password" name="password">
    <button type="Sign in">Send</button>
</form>
<p>

</p>
</body>
</html>
