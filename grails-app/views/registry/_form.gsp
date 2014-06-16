<%@ page import="docker.registry.web.Registry" %>



<div class="fieldcontain ${hasErrors(bean: registryInstance, field: 'apiVersion', 'error')} required">
	<label for="apiVersion">
		<g:message code="registry.apiVersion.label" default="Api Version" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apiVersion" required="" value="${registryInstance?.apiVersion}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: registryInstance, field: 'repositories', 'error')} ">
	<label for="repositories">
		<g:message code="registry.repositories.label" default="Repositories" />
		
	</label>
	<g:select name="repositories" from="${docker.registry.web.Repository.list()}" multiple="multiple" optionKey="id" size="5" value="${registryInstance?.repositories*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: registryInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="registry.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" value="${registryInstance?.url}"/>

</div>

