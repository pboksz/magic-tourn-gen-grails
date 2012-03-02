<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 3:15 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
   <title>Magic Tournament Generator | How Many?</title>
</head>

<body>
<div class="header">
   <h2>Tournament Settings</h2>
</div>

<div class="main">
   <g:form>
      How Many Players?<br>
      <g:select name="howManyPlayers" from="${[4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]}"/><br>
      What Format?<br>
      <g:select name="whichFormat" from="${["Standard Swiss", "MTG Swiss"]}"/><br>
      How Many Rounds?<br>
      <g:select name="howManyRounds" from="${[3, 4, 5]}"/><br>
      <g:actionSubmit value="Create Tournament" action="addsettings"/>
   </g:form>
</div>
</body>
</html>