<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<%@ page contentType="text/html;charset=UTF-8" %>
<head>

    %{--TODO javascript to show round history, display drop option, and other verification features--}%
    <script type="text/javascript">
        function dropCheck(who) {
            return confirm("Do you really want to drop " + who + " from the tournament?");
        }
        function verifiyValue() {

        }
        function showHistory() {

        }
    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Magic Tournament Generator | ${flash.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>
<div id="grailsLogo" role="banner">
    <g:link action="newtournament" style="margin: 0">
        <img src="${resource(dir: 'images', file: 'mtg-header.png')}" alt="Magic Tournament Generator"/>
    </g:link>
</div>

<div class="header">
    ${flash.title}
</div>

<g:if test="${flash.message}">
    <div class="message">
        ${flash.message}
    </div>
</g:if>

<g:if test="${flash.error}">
    <div class="errors">
        ${flash.error}
    </div>
</g:if>

<g:layoutBody/>
<div class="footer" role="contentinfo">Copyright © 2012 Phillip Boksz</div>
<g:javascript library="application"/>
<r:layoutResources/>
</body>
</html>