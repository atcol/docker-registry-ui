<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>
<body>

<h2>Images</h2>

<table id="imgTbl" class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Registry</th>
            <th>Name</th>
            <th>Arch.</th>
            <th>OS</th>
            <th>Author</th>
            <th>Created</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${registryToImageMap.entrySet()}" var="entry">
            <g:each in="${entry.value.images}" var="img">
                <tr>
                    <td>${entry.key.url}</td>
                    <td><g:link controller="repository" action="show" params="[registryId: entry.key.id, repoName: img.name, tag: img.tag, imgId: img.id]">
                        ${img.name}</g:link></td>
                    <td>${img.architecture}</td>
                    <td>${img.os}</td>
                    <td>${img.author}</td>
                    <td>${img.created}</td>
                    <td><a href="#" class="pullImg" data-pullName="${img.pullName}">Pull</a> | <a href="#" class="deleteImg" data-registryId="${entry.key.id}" data-repoName="${img.name}" data-tag="${img.tag}">Delete</a></td>
                </tr>
            </g:each>
        </g:each>
    </tbody>
</table>
<jqDT:resources/>
<g:include view="repository/image-prompts.js.gsp"/>
</body>
</html>
