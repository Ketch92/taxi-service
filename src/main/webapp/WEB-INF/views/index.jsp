
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi service</title>
</head>
<body>
<h1>Taxi service</h1>

<p>
    <a href=${pageContext.request.contextPath}"/driver/add">Add driver</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/driver/all">All drivers</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/manufacturer/add">Add manufacturer</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/manufacturer/all">All manufacturers</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/car/add">Add car</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/cars/all">All cars</a>
</p>
<p>
    <a href=${pageContext.request.contextPath}"/car/add/driver">Add driver to a car</a>
</p>
</body>
</html>
