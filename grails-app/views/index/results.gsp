<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 2/28/12
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
   <title>Magic Tournament Generator | Final Results</title>
</head>

<body>
<div class="header">
   <h2>Final Results</h2>
</div>

<div class="main">
   <g:form>
      <div class="roundPairing">
         <table>
            <thead>
            <tr>
               <th>
                  Place
               </th>
               <th>
                  Player Name
               </th>
               <th>
                  Round Wins
               </th>
               <th>
                  Round Byes
               </th>
               <th>
                  Round Losses
               </th>
               <th>
                  Individual Wins
               </th>
               <th>
                  Individual Losses
               </th>
            </tr>
            </thead>
            <g:each in="${results}" var="result">
                  <tr>
                     <td>
                        ${result.rank}
                     </td>
                     <td>
                        ${result.name}
                     </td>
                     <td>
                        ${result.roundWins}
                     </td>
                     <td>
                        ${result.roundByes}
                     </td>
                     <td>
                        ${result.roundLosses}
                     </td>
                     <td>
                        ${result.individualWins}
                     </td>
                     <td>
                        ${result.individualLosses}
                     </td>
                  </tr>
            </g:each>
         </table>
      </div>
      <g:actionSubmit value="Next Round" action="nextround"/>
   </g:form>
</div>
</body>
</html>