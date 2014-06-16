
<%@ page import="docker.registry.web.Registry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registry.label', default: 'Registry')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-registry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-registry" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list registry">
			
				<g:if test="${registryInstance?.apiVersion}">
				<li class="fieldcontain">
					<span id="apiVersion-label" class="property-label"><g:message code="registry.apiVersion.label" default="Api Version" /></span>
					
						<span class="property-value" aria-labelledby="apiVersion-label"><g:fieldValue bean="${registryInstance}" field="apiVersion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registryInstance?.repositories}">
				<li class="fieldcontain">
					<span id="repositories-label" class="property-label"><g:message code="registry.repositories.label" default="Repositories" /></span>
					
						<g:each in="${registryInstance.repositories}" var="r">
						<span class="property-value" aria-labelledby="repositories-label"><g:link controller="repository" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${registryInstance?.url}">
				<li class="fieldcontain">
					<span id="url-label" class="property-label"><g:message code="registry.url.label" default="Url" /></span>
					
						<span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${registryInstance}" field="url"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:registryInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${registryInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
