<%@ page import="docker.registry.web.Registry" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'registry.label', default: 'Registry')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div class="nav" role="navigation">
    <g:link class="create" action="create"><button class="btn btn-link" type="button">
        <g:message code="default.new.label" args="[entityName]"/></button></g:link>
    <g:link class="create" action="index"><button class="btn btn-link" type="button">
        <g:message code="default.list.label" args="[entityName]"/></button></g:link>
</div>

<div id="show-registry" class="content scaffold-show" role="main">
    <h4><g:message code="default.show.label" args="[entityName]"/></h4>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:form class="form-horizontal" url="[resource:registryInstance, action:'save']" >
        <fieldset>
            <div class="form-group">
                <label for="apiVersion" class="col-lg-2 control-label"><g:message code="labels.registry.apiVersion"/> </label>

                <div class="col-lg-2">
                    <g:textField name="apiVersion" style="cursor: auto;" class="form-control"
                                 value="${registryInstance?.apiVersion}" readonly=""></g:textField>
                </div>
            </div>

            <div class="form-group">
                <label for="url" class="col-lg-2 control-label"><g:message code="labels.registry.url"/> </label>

                <div class="col-lg-4">
                    <g:textField name="url" style="cursor: auto;" class="form-control"
                                 value="${registryInstance?.url}" readonly=""></g:textField>
                </div>
            </div>
        </fieldset>

    </g:form>

    <g:form url="[resource: registryInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="btn btn-primary" action="edit" resource="${registryInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="btn btn-primary" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
