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
    <title>Magic Tournament Generator | Add Players</title>
</head>

<body>
<div class="header">
    <h2>Player Names</h2>
</div>

<div class="main">
    <g:form>
        Please input each player's name (if left blank the name will be set as "playerX", respectively)
        <g:each in="${(1..howManyPlayers)}" var="num">
            Player ${num} Name: <g:textField name="${'player' + num}"/><br>
        </g:each>
        <g:actionSubmit value="Register Players" action="register"/>
    </g:form>
</div>
</body>
</html>