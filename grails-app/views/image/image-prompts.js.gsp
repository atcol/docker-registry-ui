<script language="JavaScript">
    $(document).ready(function() {
        // initialise
        $('#dialog-confirm-delete').hide();
        $('#dialog-confirm-pull').hide();
        setupImageDeleteLinks();
        setupImagePullLinks();
    });

    function setupImagePullLinks() {
        $(document).ready(function() {
            var pullLinks = $('.pullImg');
            $.each(pullLinks, function (idx, val) {
                val = $(val);
                val.click(function () {
                    var dialog = $('#dialog-confirm-pull');
                    var pre = $('<pre>');
                    var dockerCmd = val.attr("data-pullName");
                    pre.text("docker pull " + dockerCmd);
                    dialog.append(pre);
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
                showConfirm(anchor.attr("data-registryId"), anchor.attr("data-repoName"));
            });
        });
    }

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
        $("#dialog-confirm-delete").dialog({
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
<div id="dialog-confirm-pull" title="Pull an image">
    <p>
      <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>Copy &amp; paste the following into a shell to pull.</p>
</div>
<div id="dialog-confirm-delete" title="Delete image?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This image will be permanently deleted and cannot be recovered.</p>
</div>
