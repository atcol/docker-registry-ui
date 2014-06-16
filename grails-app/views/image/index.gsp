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
            <th>Registry</th>
            <th>Name</th>
            <th>Description</th>
            <th>Created</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${registryToImageMap.entrySet()}" var="entry">
            <g:each in="${entry.value}" var="img">
                <tr>
                    <td>${entry.key.url}</td>
                    <td>${img.name}</td>
                    <td>${img}</td>
                    <td>${img.created}</td>
                </tr>
            </g:each>
        </g:each>
    </tbody>
</table>
</body>
</html>
