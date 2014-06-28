<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${g.resource(dir: 'css', file: 'bootstrap.journal.css')}">
    <link rel="stylesheet" href="${g.resource(dir: 'css', file: 'bootswatch.min.css')}">
    <asset:javascript src="bootstrap.min.js"/>
    <asset:stylesheet src="bootstrap.journal.css"/>
    <asset:stylesheet src="bootswatch.min.css"/>
    <asset:stylesheet src="general.css"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>

<body>

<g:include view="layouts/nav.gsp"/>

<div class="container">
    <div class="page-header" id="banner">
        <div class="row">

            <div class="col-lg-12">
                <g:layoutBody/>
            </div>

        </div>
    </div>
    <g:include view="layouts/footer.gsp"/>
</div>

</body>
</html>
