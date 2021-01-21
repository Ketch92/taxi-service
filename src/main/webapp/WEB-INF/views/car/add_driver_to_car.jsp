<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Please, fill in the car info.</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Car's id <input required type="number" name="carId">
    Driver id <input required type="number" name="driverId">
    <button type="submit">Send</button>
</form>
</body>
</html>
