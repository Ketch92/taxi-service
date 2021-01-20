<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Please, fill in the car info.</h1>

<form method="post" action=${pageContext.request.contextPath}"/car/add">
    Car's model <input type="text" name="model">
    Manufacturer's name <input type="text" name="manufacturerName">
    Manufacturer's country <input type="text" name="manufacturerCountry">
    <button type="submit">Send</button>
</form>
<p>
    <a href=${pageContext.request.contextPath}"/">Return to main page</a>
</p>
</body>
</html>
