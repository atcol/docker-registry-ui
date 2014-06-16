<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Docker Registry - Image Search</title>
    <script language="JavaScript" src="<g:resource dir='js' file='jquery/jquery-1.10.2.min.js'/>"/>
    <jqDT:resources/>
</head>

<body>

<h1>Results</h1>

<g:if test="${!results.isEmpty()}">

    <table id="resultsTbl">
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
                <g:each in="${entry.value}" var="img">
                    <tr>
                        <td>${entry.key.url}</td>
                        <td>${img.name}</td>
                        <td>${img.description}</td>
                        <td><g:link controller="Image" action="delete" params="[id: img.id]">Delete</g:link></td>
                    </tr>
                </g:each>
            </g:each>
        </tbody>
    </table>
    <script language="JavaScript">
        $(document).ready(function(){
            $('#resultsTbl').dataTable();
        });
    </script>
</g:if>
<g:else>
    <p>No results. Sorry :(</p>
</g:else>

</body>
</html>