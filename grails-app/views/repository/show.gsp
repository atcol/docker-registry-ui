<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image List</title>
</head>

<body>

<form class="form-horizontal">
    <fieldset>
        <legend><h2>${img?.displayName}</h2></legend>

        <div class="form-group">
            <label for="imagePull" class="col-lg-2 control-label"><g:message code="labels.image.pull"/></label>

            <div class="col-lg-6">
                <g:textField name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imagePull" value="docker pull ${img.pullName}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageId" class="col-lg-2 control-label"><g:message code="labels.image.id"/></label>

            <div class="col-lg-6">
                <g:textField name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imageId" value="${img.id}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageName" class="col-lg-2 control-label"><g:message code="labels.image.name"/></label>

            <div class="col-lg-6">
                <g:textField name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imageName" value="${img.name}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageTag" class="col-lg-2 control-label"><g:message code="labels.image.tag"/></label>

            <div class="col-lg-6">
                <g:textField name="tag" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imageTag" value="${img.tag}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageCreated" class="col-lg-2 control-label"><g:message code="labels.image.created"/></label>

            <div class="col-lg-6">
                <g:textField name="created" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                            id="imageCreated" value="${img.created}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageAuthor" class="col-lg-2 control-label"><g:message code="labels.image.author"/></label>

            <div class="col-lg-6">
                <g:textField name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                            id="imageAuthor" value="${img.author}"></g:textField>
            </div>
        </div>

        <div class="form-group">
            <label for="imageDescription" class="col-lg-2 control-label"><g:message code="labels.image.description"/></label>

            <div class="col-lg-6">
                <g:textArea name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imageDescription" value="${img.description}"></g:textArea>
            </div>
        </div>

        <div class="form-group">
            <label for="imageConfig" class="col-lg-2 control-label"><g:message code="labels.image.config"/></label>

            <div class="col-lg-6">
                <g:textArea name="name" readonly autocomplete="off" style="cursor: auto;" class="form-control"
                             id="imageConfig" value="${img.config}"></g:textArea>
            </div>
        </div>
    </fieldset>
</form>
<script>
    $(document).ready(function() {
        $('.form-control').each(function(inp) {
            $(inp).on("click", function() {
               $(this).select();
            });
        });
    });
</script>
</body>
</html>
