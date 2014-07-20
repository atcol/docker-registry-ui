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
    <g:each in="${entry.value}" var="repo">
        <div class="accordion ui-accordion ui-widget " role="tablist">
            <g:each in="${repo.tags}" var="tag">
                <h3>
                    <g:link controller="repository" action="show"
                            params="[registryId: entry.key.id, repoName: repo.name, tag: tag.name, imgId: tag.imageId]">
                        <util:generatePullName registry="${entry.key}" repoName="${repo.name}" tag="${tag.name}"/></g:link>
                 </h3>
                <div id="ui-id-2" class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" style="display: none; height: 133px;" aria-labelledby="ui-id-1" role="tabpanel" aria-hidden="true">
                    <span>
                        <strong>Actions:</strong>
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
                    </span>
                    <p></p>
                </div>
            </g:each>
        </div>
    </g:each>
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
