<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi service</title>
</head>
<body>
<h1>Taxi service</h1>

<p>
    <a href="${pageContext.request.contextPath}/drivers/add">Add driver</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/drivers">All drivers</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/manufacturers/add">Add manufacturer</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/manufacturers">All manufacturers</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/cars/add">Add car</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/cars">All cars</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/cars/drivers/add">Add driver to a car</a>
</p>
</body>
</html>
