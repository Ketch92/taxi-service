<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Please, fill in the driver info.</h1>

<form method="post" action=${pageContext.request.contextPath}"/drivers/add">
    Driver's name <input required type="text" name="driverName">
    Driver's licence number <input required type="text" name="driverLicence">
    <button type="submit">Send</button>
</form>
<p>
    <a href=${pageContext.request.contextPath}"/">Return to main page</a>
</p>
</body>
</html>
