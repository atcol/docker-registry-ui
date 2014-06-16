<%@ page import="docker.registry.web.Registry" %>



<div class="fieldcontain ${hasErrors(bean: registryInstance, field: 'apiVersion', 'error')} required">
	<label for="apiVersion">
		<g:message code="registry.apiVersion.label" default="Api Version" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apiVersion" required="" value="${registryInstance?.apiVersion}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: registryInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="registry.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" value="${registryInstance?.url}"/>

</div>

