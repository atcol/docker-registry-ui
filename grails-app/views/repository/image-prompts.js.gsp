<script language="JavaScript">
    $(document).ready(function() {
        // initialise
        $('#dialog-confirm-delete').hide();
        $('#dialog-confirm-pull').hide();
        setupImageDeleteLinks();
        setupImagePullLinks();
    });

    //TODO: generalize these "iterate and assign onclick func" methods
    function setupImagePullLinks() {
        $(document).ready(function() {
            var pullLinks = $('.pullImg');
            $.each(pullLinks, function (idx, val) {
                val = $(val);
                val.click(function () {
                    var dialog = $('#dialog-confirm-pull');
                    var pre = $('#dialog-confirm-pull > pre');
                    var pullName = val.attr("data-pullName");
                    pre.text("docker pull " + pullName);
                    $("#dialog-confirm-pull").dialog({
                        autoResize: true,
                        resizable: true,
                        height: 200,
                        width: 450,
                        modal: true
                    });
                    dialog.show();
                });
            });
        });
    }

    function setupImageDeleteLinks() {
        var imgLinks = $('.deleteImg');
        $.each(imgLinks, function (idx, val) {
            var anchor = $(val);
            anchor.click(function() {
                $('#dialog-confirm-delete').show();
                showConfirm(anchor.attr("data-registryId"), anchor.attr("data-repoName"), anchor.attr("data-tag"));
            });
        });
    }

    function doDelete(registryId, repoName, tag) {
        $.ajax({
            url: "${g.createLink(controller: 'repository', action: 'delete')}" + "?repoName=" + repoName + "&registry=" + registryId + "&tag=" + tag,
            type: "DELETE"
        }).done(function () {
            showSuccess('<g:message code="image.delete.success" />');
        }).fail(function () {
            showFail('<g:message code="image.delete.failure" />');
        });
    }

    function showConfirm(registryId, repoName, tag) {
        $("#dialog-confirm-delete").dialog({
            autoResize: true,
            resizable: true,
            height: 200,
            width: 450,
            modal: true,
            buttons: {
                "${g.message(code: 'ui.dialog.button.deleteImage')}": function () {
                    doDelete(registryId, repoName, tag);
                    $(this).dialog("close");
                },
                Cancel: function () {
                    $(this).dialog("close");
                }
            }
        });
    }
</script>
<div id="dialog-confirm-pull" title="${message(code:"image.pull.prompt.title")}">
    <p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span><g:message code="image.pull.prompt" /></p>
    <pre></pre>
</div>
<div id="dialog-confirm-delete" title="${message(code:"image.delete.prompt.title")}">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><g:message code="image.delete.tag.prompt" /></p>
</div>
