<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Please, fill in the car info.</h1>

<form method="post" action=${pageContext.request.contextPath}"/cars/add">
    Car's model <input required type="text" name="model">
    Manufacturer's id <input required type="text" name="manufacturerId">
    <button type="submit">Send</button>
</form>
<p>
    <a href=${pageContext.request.contextPath}"/">Return to main page</a>
</p>
</body>
</html>
