<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
    <link rel="stylesheet" href="${g.resource(dir: 'css', file:'bootstrap.journal.css')}"
</head>
<body>

<h1>Images</h1>

<table class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Registry</th>
            <th>Name</th>
            <th>Arch.</th>
            <th>OS</th>
            <th>Author</th>
            <th>Created</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${registryToImageMap.entrySet()}" var="entry">
            <g:each in="${entry.value}" var="img">
                <tr>
                    <td>${entry.key.url}</td>
                    <td>${img.displayName}</td>
                    <td>${img.architecture}</td>
                    <td>${img.os}</td>
                    <td>${img.author}</td>
                    <td>${img.created}</td>
                </tr>
            </g:each>
        </g:each>
    </tbody>
</table>
</body>
</html>
