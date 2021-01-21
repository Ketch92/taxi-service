<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add manufacturer</title>
</head>
<body>
<h1>Please, fill in the manufacturer info.</h1>

<form method="post" action=${pageContext.request.contextPath}"/manufacturers/add">
    Manufacturer's name <input type="text" name="name">
    Manufacturer's country <input type="text" name="country">
    <button type="submit">Send</button>
</form>
<p>
    <a href=${pageContext.request.contextPath}"/">Return to main page</a>
</p>
</body>
</html>
