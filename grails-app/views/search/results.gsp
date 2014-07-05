<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Image Search</title>
    <jqDT:resources/>
</head>

<body>

<h1>Results</h1>

<g:if test="${!results.isEmpty()}">

    <table id="resultsTbl" class="table table-striped table-hover">
        <thead>
            <tr>
                <th>Registry</th>
                <th>Name</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${results.entrySet()}" var="entry">
                <g:each in="${entry.value.images}" var="img">
                    <tr>
                        <td>${entry.key.url}</td>
                        <td><g:link controller="repository" action="show" params="[registryId: entry.key.id, repoName: img.name, tag: img.tag, imgId: img.id]">
                            ${img.name}</g:link>
                        </td>
                        <td>${img.description}</td>
                        <td>
                            <a href="#" class="deleteImg" data-repoName="${img.name}" data-registryId="${entry.key.id}">Delete</a>
                        </td>
                    </tr>
                </g:each>
            </g:each>
        </tbody>
    </table>
</g:if>
<g:else>
    <p>No results. Sorry :(</p>
</g:else>
<g:include view="repository/image-prompts.js.gsp"/>
</body>
</html>