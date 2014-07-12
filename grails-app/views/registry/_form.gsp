<%@ page import="docker.registry.web.Registry" %>

<div class="form-group">
    <label for="apiVersion" class="col-lg-2 control-label"><g:message code="labels.registry.apiVersion"/> *</label>

    <div class="col-lg-2">
        <g:textField name="apiVersion" style="cursor: auto;" class="form-control"
                     value="${registryInstance?.apiVersion}"></g:textField>
    </div>
</div>

<div class="form-group">
    <label for="url" class="col-lg-2 control-label"><g:message code="labels.registry.url"/> *</label>

    <div class="col-lg-4">
        <g:textField name="url" style="cursor: auto;" class="form-control"
                     value="${registryInstance?.url}"></g:textField>
    </div>
</div>

