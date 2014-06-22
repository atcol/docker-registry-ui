<script language="JavaScript">
    function setupImageDeleteLinks() {
        var imgLinks = $('.deleteImg')
        $.each(imgLinks, function (idx, val) {
            var anchor = $(val);
            anchor.click(function() {
                $('#dialog-confirm').show();
                showConfirm(anchor.attr("data-registryId"), anchor.attr("data-imgId"));
            });
        });
    }

    $(document).ready(function() {
        // initialise
        $('#dialog-confirm').hide();
        setupImageDeleteLinks();
    });

    function doDelete(registryId, imgId) {
        $.ajax({
            url: "${g.createLink(controller: 'image', action: 'delete')}" + "/" + imgId,
            type: "DELETE",
            data: "?registry=" + registryId
        }).done(function () {
            alert("Done!");
        }).fail(function () {
            alert("Sorry, that failed!")
        });
    }

    function showConfirm(registryId, imgId) {
        $("#dialog-confirm").dialog({
            autoResize: true,
            resizable: true,
            height: 200,
            width: 450,
            modal: true,
            buttons: {
                "${g.message(code: 'ui.dialog.button.deleteImage')}": function () {
                    doDelete(registryId, imgId);
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
