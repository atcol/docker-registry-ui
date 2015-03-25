<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Forbidden</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
        <h2>Forbidden</h2>
        <h3>${flash.message}</h3>
	</body>
</html>
