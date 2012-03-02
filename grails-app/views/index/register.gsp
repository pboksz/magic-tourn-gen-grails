<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 2/29/12
  Time: 10:50 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
   <title>Magic Tournament Generator | Registered Players</title>
</head>

<body>
<div class="header">
   <h2>Registered Players</h2>
</div>

<div class="main">
   <g:form>
      <div class="players">
         <table>
            <thead>
            <tr>
               <th>
                  Name
               </th>
               <th>
                  Seed
               </th>
            </tr>
            </thead>
            <g:each in="${players}" var="player">
               <tr>
                  <td>
                     ${player.name}
                  </td>
                  <td>
                     ${player.seed}
                  </td>
               </tr>
            </g:each>
         </table>
      </div>
      <g:actionSubmit value="Start First Round" action="firstround"/>
   </g:form>
</div>
</body>
</html>