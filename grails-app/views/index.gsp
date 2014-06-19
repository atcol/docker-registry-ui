<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to <g:message code="app.name" /></title>
	</head>
	<body>
        <p>Welcome to ${g.message(code: 'app.name')}. This application allows you to search, delete and view images
        for a given Docker Registry installation. Just head on over to <g:link controller="registry" action="index">Registries</g:link> and add your registry URLs (with 'v1' or similar for the API version) and you're good to go!</p>
	</body>
</html>
