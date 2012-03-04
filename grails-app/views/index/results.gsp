<%--
  Created by IntelliJ IDEA.
  User: phillip
  Date: 2/28/12
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>

<html>
<body>

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
               %{--<th>--}%
                  %{--Points--}%
               %{--</th>--}%
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
                     %{--<td>--}%
                        %{--${result.points}--}%
                     %{--</td>--}%
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
   </g:form>
</div>
</body>
</html>