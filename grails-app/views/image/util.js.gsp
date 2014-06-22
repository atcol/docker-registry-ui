<script language="JavaScript">
    function setupImageDeleteLinks() {
        var imgLinks = $('.deleteImg')
        $.each(imgLinks, function (idx, val) {
            var anchor = $(val);
            anchor.click(function() {
                $('#dialog-confirm').show();
                showConfirm(anchor.attr("data-registryId"), anchor.attr("data-repoName"));
            });
        });
    }

    $(document).ready(function() {
        // initialise
        $('#dialog-confirm').hide();
        setupImageDeleteLinks();
    });

    function doDelete(registryId, repoName) {
        $.ajax({
            url: "${g.createLink(controller: 'image', action: 'delete')}" + "?repoName=" + repoName + "&registry=" + registryId,
            type: "DELETE"
        }).done(function () {
            showSuccess("Deleted. Bye bye...");
        }).fail(function () {
            showFail("That failed. Please try again.");
        });
    }

    function showConfirm(registryId, repoName) {
        $("#dialog-confirm").dialog({
            autoResize: true,
            resizable: true,
            height: 200,
            width: 450,
            modal: true,
            buttons: {
                "${g.message(code: 'ui.dialog.button.deleteImage')}": function () {
                    doDelete(registryId, repoName);
                    $(this).dialog("close");
                },
                Cancel: function () {
                    $(this).dialog("close");
                }
            }
        });
    }
</script>
<div id="dialog-confirm" title="Delete image?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This image will be permanently deleted and cannot be recovered.</p>
</div>
