<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Docker Registry - Image List</title>
</head>

<body>

<h1>Images</h1>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Created</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${images}" var="img">
            <tr>
                <td>${img.name}</td>
                <td>${img.description}</td>
                <td>${img.created}</td>
            </tr>
        </g:each>
    </tbody>
</table>
</body>
</html>
