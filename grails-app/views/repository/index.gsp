<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>

<body>

<g:set var="multipleRegistries" value="${registryToRepoMap.size() > 1}"/>
<g:each in="${registryToRepoMap}" var="entry">
    <h3>Registry ${entry.key.url} </h3>

    <table id="imgTbl" class="table table-striped table-hover">
        <thead>
            <tr>
                <th>Tag</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${entry.value}" var="repo">
                <g:each in="${repo.tags}" var="tag">
                    <tr>
                        <td>
                            <g:link controller="repository" action="show"
                                params="[registryId: entry.key.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                            <util:generatePullName registry="${entry.key}" repoName="${repo.name}" tag="${tag.name}"/></g:link>
                        </td>
                        <td>
                            <g:link controller="repository" action="show"
                                    params="[registryId: entry.key.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                                <asset:image src="icons/185087 - link streamline.png" /></asset>
                            </g:link> |
                            <a href="#" class="pullImg" data-pullName='<util:generatePullName registry="${entry.key}" repoName="${repo.name}" tag="${tag.name}"/>'>
                                <asset:image src="icons/185073 - book dowload streamline.png"/>
                            </a> |
                            <a href="#" class="deleteImg" data-registryId="${entry.key.id}" data-repoName="${repo.name}" data-tag="${tag.name}">
                                <asset:image src="icons/185090 - delete garbage streamline.png"/>
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
<g:javascript>
    $(document).ready(function() {
        $('.accordion').accordion({
            collapsible: true
        });
    });
</g:javascript>
</body>
</html>
