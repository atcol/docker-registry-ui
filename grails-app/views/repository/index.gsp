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
        <th></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${registryToRepoMap}" var="entry">
        <g:each in="${entry.value}" var="repo">
            <g:each in="${repo.tags}" var="tag">
                <tr>
                    <td>${entry.key.url}</td>
                    <td><g:link controller="repository" action="show"
                                params="[registryId: entry.key.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                        <util:generatePullName registry="${entry.key}" repoName="${repo.name}"/></g:link></td>
                    <td><a href="#" class="pullImg" data-pullName='<util:generatePullName registry="${entry.key}" repoName="${repo.name}"/>'>Pull</a> | <a href="#"
                                                                                         class="deleteImg"
                                                                                         data-registryId="${entry.key.id}"
                                                                                         data-repoName="${repo.name}"
                                                                                         data-tag="${tag.name}">Delete</a>
                    </td>
                </tr>

            </g:each>
        </g:each>

    </g:each>
    </tbody>
</table>
<jqDT:resources/>
<g:include view="repository/image-prompts.js.gsp"/>
</body>
</html>
