<%@ page import="docker.registry.web.Registry" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'registry.label', default: 'Registry')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <g:link class="create" action="create"><button class="btn btn-link" type="button">
                <g:message code="default.new.label" args="[entityName]"/></button></g:link>
            <g:link class="create" action="index"><button class="btn btn-link" type="button">
                <g:message code="default.list.label" args="[entityName]"/></button></g:link>
        </div>
        <div id="list-registry" class="content scaffold-list" role="main">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table class="table table-striped table-hover">
			<thead>
					<tr>
						<th>${message(code: 'registry.host.label', default: 'Hostname')}</th>
						<th>${message(code: 'registry.port.label', default: 'Port')}</th>
						<th>${message(code: 'registry.apiVersion.label', default: 'Api Version')}</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${registryInstanceList}" status="i" var="registryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${registryInstance.id}">${fieldValue(bean: registryInstance, field: "host")}</g:link></td>
						<td>${fieldValue(bean: registryInstance, field: "port")}</td>
						<td>${fieldValue(bean: registryInstance, field: "apiVersion")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${registryInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
