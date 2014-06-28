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
                <g:each in="${entry.value}" var="img" status="it">
                    <tr>
                        <td>${entry.key.url}</td>
                        <td>${img.name}</td>
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
<g:include view="image/util.js.gsp"/>
</body>
</html>