<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 5:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
   <title>Magic Tournament Generator | Round ${roundNum}</title>
</head>

<body>
<div class="header">
   <h2>Round ${roundNum}</h2>
</div>

<div class="main">
   <g:form>
      <div class="roundPairing">
         <table>
            <thead>
            <tr>
               <th>
                  Player Name
               </th>
               <th>
                  Opponent Name
               </th>
               <th>
                  Player Wins
               </th>
               <th>
                  Player Losses
               </th>
            </tr>
            </thead>
            <g:each in="${roundPairs}" var="pair">
               <g:each in="${pair.value}" var="info">
                  <tr>
                     <td>
                        ${info.name}
                        <g:hiddenField name="player" value="${info.name}"/>
                     </td>
                     <td>
                        ${info.opponent}
                        <g:hiddenField name="opponent" value="${info.opponent}"/>
                     </td>
                     <td>
                        <g:textField name="wins" maxlength="1"
                                     style="width: 20px"/>
                     </td>
                     <td>
                        <g:textField name="losses" maxlength="1"
                                     style="width: 20px"/>
                     </td>
                  </tr>
               </g:each>
            </g:each>
         </table>
      </div>
      <g:actionSubmit value="Next Round" action="nextround"/>
   </g:form>
</div>
</body>
</html>