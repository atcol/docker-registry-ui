<%@ page import="docker.registry.web.Registry" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'registry.label', default: 'Registry')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="nav" role="navigation">
            <g:link class="create" action="create"><button class="btn btn-link" type="button">
                <g:message code="default.new.label" args="[entityName]"/></button></g:link>
            <g:link class="create" action="index"><button class="btn btn-link" type="button">
                <g:message code="default.list.label" args="[entityName]"/></button></g:link>
        </div>

        <div id="edit-registry" class="content scaffold-edit" role="main">

            <h4><g:message code="default.edit.label" args="[entityName]" /></h4>

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

            <g:form class="form-horizontal" url="[resource:registryInstance, action:'update']" method="PUT" >
                <g:hiddenField name="version" value="${registryInstance?.version}" />
                <fieldset class="form">
                    <div class="form-group">
                        <label for="apiVersion" class="col-lg-2 control-label"><g:message code="labels.registry.apiVersion"/> *</label>

                        <div class="col-lg-2">
                            <g:textField name="apiVersion" style="cursor: auto;" class="form-control"
                                         value="${registryInstance?.apiVersion}"></g:textField>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="protocol" class="col-lg-2 control-label"><g:message code="labels.registry.protocol"/> *</label>

                        <div class="col-lg-4">
                            <g:textField name="protocol" style="cursor: auto;" class="form-control"
                                         value="${registryInstance?.protocol}"></g:textField>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="host" class="col-lg-2 control-label"><g:message code="labels.registry.host"/> *</label>

                        <div class="col-lg-4">
                            <g:textField name="host" style="cursor: auto;" class="form-control"
                                         value="${registryInstance?.host}"></g:textField>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="port" class="col-lg-2 control-label"><g:message code="labels.registry.port"/> *</label>

                        <div class="col-lg-4">
                            <g:textField name="port" style="cursor: auto;" class="form-control"
                                         value="${registryInstance?.port}"></g:textField>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
