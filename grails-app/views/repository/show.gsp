<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>
<body>

<h2>${img?.displayName} @ ${registry?.url}</h2>

<p class="col-lg-8">Pull this repository<pre>docker pull ${img.displayName}</pre></body>

<table id="imgTbl" class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Name</th>
            <th>Arch.</th>
            <th>OS</th>
            <th>Author</th>
            <th>Created</th>
        </tr>
    </thead>
    <tbody>
            <tr>
                <td>${img.name}</td>
                <td>${img.architecture}</td>
                <td>${img.os}</td>
                <td>${img.author}</td>
                <td>${img.created}</td>
            </tr>
    </tbody>
</table>
</body>
</html>
