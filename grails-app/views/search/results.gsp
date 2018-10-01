<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>

<body>

<g:each in="${registryToRepoMap}" var="entry">
    <g:set var="registry" value="${entry.key}"/>
    <g:set var="repositories" value="${entry.value}"/>

    <h3>Registry ${registry.host}</h3>

    <table id="imgTbl" class="table table-striped table-hover">
        <thead>
            <tr>
                <th>Tag</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${repositories}" var="repo">
                <g:each in="${repo.tags}" var="tag">
                    <tr>
                        <td>
                            <g:link controller="repository" action="show"
                                params="[registryId: registry.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                            <util:generatePullName registry="${registry}" repoName="${repo.name}" tag="${tag.name}"/></g:link>
                        </td>
                        <td>
                            <g:link controller="repository" action="show"
                                    params="[registryId: registry.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                                <asset:image src="icons/link.png" /></asset>
                            </g:link> |
                            <a href="#" class="pullImg" data-pullName='<util:generatePullName registry="${registry}" repoName="${repo.name}" tag="${tag.name}"/>'>
                                <asset:image src="icons/book_download.png" title="Docker pull command"/>
                            </a> |
                            <a href="#" class="deleteImg" data-registryId="${registry.id}" data-repoName="${repo.name}" data-tag="${tag.name}">
                                <asset:image src="icons/garbage.png" title="Delete"/>
                            </a>
                        </td>
                    </tr>
            </g:each>
        </tbody>
        </g:each>
    </table>
    <g:if test="${multipleRegistries}">
        <hr/>
    </g:if>
</g:each>
<jqDT:resources/>
<g:include view="repository/image-prompts.js.gsp"/>
</body>
</html>
