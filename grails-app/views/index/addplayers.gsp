<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Magic Tournament Generator | Player Names</title>
</head>

<body>
<div class="header">
    <h2>Player Names</h2>
</div>

<div class="main">
    <g:form>
        <g:each in="${(1..howManyPlayers)}" var="num">
            Player ${num} Name: <g:textField name="${'player' + num}"/><br>
        </g:each>
        <g:actionSubmit value="Register Players" action="seeding"/>
    </g:form>
</div>
</body>
</html>