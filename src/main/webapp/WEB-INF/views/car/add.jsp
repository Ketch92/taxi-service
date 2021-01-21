<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Please, fill in the car info.</h1>

<h4 style="color: red">${errorMessage}</h4>

<form method="post" action=${pageContext.request.contextPath}"/cars/add">
    Car's model <input type="text" name="model">
    Manufacturer's id <input type="text" name="manufacturerId">
    <button type="submit">Send</button>
</form>
<p>
    <a href=${pageContext.request.contextPath}"/">Return to main page</a>
</p>
</body>
</html>
