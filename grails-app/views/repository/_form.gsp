<%@ page import="docker.registry.web.Repository" %>



<div class="fieldcontain ${hasErrors(bean: repositoryInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="repository.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${repositoryInstance?.name}"/>

</div>

