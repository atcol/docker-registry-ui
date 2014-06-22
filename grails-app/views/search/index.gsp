<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image Search</title>
</head>

<body>

<h1>Docker Image Search</h1>

<g:form action="search" method="GET">
    <label for="q"><g:message code="labels.search.query"/><g:textField name="q"/></label>
</g:form>

</body>
</html>
