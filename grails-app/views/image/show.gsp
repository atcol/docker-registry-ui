<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>
<body>

<h2>${image?.displayName} @ ${registry?.url}</h2>

<p>Pull this image<pre></pre></body>

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
                <td>${img?.name}</td>
                <td>${img?.architecture}</td>
                <td>${img?.os}</td>
                <td>${img?.author}</td>
                <td>${img?.created}</td>
                %{--<td><a href="#" class="pullImg" data-pullName="${img.pullName}">Pull</a> | <a href="#" class="deleteImg" data-registryId="${registry.id}" data-repoName="${img.name}" data-tag="${img.tag}">Delete</a></td>--}%
            </tr>
    </tbody>
</table>
<jqDT:resources/>
<g:include view="image/image-prompts.js.gsp"/>
</body>
</html>
