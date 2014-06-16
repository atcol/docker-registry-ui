<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Docker Registry - Image Search</title>
</head>

<body>

<h1>Results</h1>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${results}" var="image">
            <td>${image.name}</td>
            <td>${image.description}</td>
            <td><g:link controller="Image" action="delete" params="[id: image.id]">Delete</g:link></td>
        </g:each>
    </tbody>
</table>

</body>
</html>