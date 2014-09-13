<%@ page import="docker.registry.web.Registry" %>

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

<div class="form-group">
    <label for="username" class="col-lg-2 control-label"><g:message code="labels.registry.username"/></label>

    <div class="col-lg-4">
        <g:textField name="username" style="cursor: auto;" class="form-control"
                     value="${registryInstance?.username}"></g:textField>
    </div>
</div>

<div class="form-group">
    <label for="password" class="col-lg-2 control-label"><g:message code="labels.registry.password"/></label>

    <div class="col-lg-4">
        <g:textField name="password" style="cursor: auto;" class="form-control"
                     value="${registryInstance?.password}"></g:textField>
    </div>
</div>
