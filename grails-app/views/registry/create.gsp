<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registry.label', default: 'Registry')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav" role="navigation">
            <g:link class="create" action="create"><button class="btn btn-link" type="button">
                <g:message code="default.new.label" args="[entityName]"/></button></g:link>
            <g:link class="create" action="index"><button class="btn btn-link" type="button">
                <g:message code="default.list.label" args="[entityName]"/></button></g:link>
		</div>



		<div id="create-registry" class="content scaffold-create" role="main">
			<h4><g:message code="default.create.label" args="[entityName]" /></h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${registryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${registryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>

			<g:form class="form-horizontal" url="[resource:registryInstance, action:'save']" >
				<fieldset>
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>

		</div>
	</body>
</html>
