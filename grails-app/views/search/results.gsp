<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image Search</title>
    <asset:javascript src="jquery/jquery-1.10.2.js"/>
    <script language="JavaScript">
        function confirmDelete(anchor) {
            if (showConfirm()) {

            }
        }
    </script>
</head>

<body>

<h1>Results</h1>

<g:if test="${!results.isEmpty()}">

    <table id="resultsTbl" class="table table-striped table-hover">
        <thead>
            <tr>
                <th>Registry</th>
                <th>Name</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${results.entrySet()}" var="entry">
                <g:each in="${entry.value}" var="img" status="it">
                    <tr>
                        <td>${entry.key.url}</td>
                        <td>${img.name}</td>
                        <td>${img.description}</td>
                        <td>
                            <a href="#" onclick="confirmDelete('${img.id}', '${href}');">Delete</a>
                        </td>
                    </tr>
                </g:each>
            </g:each>
        </tbody>
    </table>

<div id="dialog-confirm" title="Delete image?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These items will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
</g:if>
<g:else>
    <p>No results. Sorry :(</p>
</g:else>

</body>
</html>