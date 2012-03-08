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
        <div class="results">
            <table>
                <thead>
                <tr>
                    <th colspan="2">
                        Player Information
                    </th>
                    %{--<th>--}%
                    %{--Points--}%
                    %{--</th>--}%
                    <th colspan="3">
                        Round Stats
                    </th>
                    <th colspan="2">
                        Individual Stats
                    </th>
                </tr>
                <tr>
                    <th class="value">
                        Rank
                    </th>
                    <th>
                        Name
                    </th>
                    <th class="value">
                        Wins
                    </th>
                    <th class="value">
                        Byes
                    </th>
                    <th class="value">
                        Losses
                    </th>
                    <th class="value">
                        Wins
                    </th>
                    <th class="value">
                        Losses
                    </th>
                </tr>
                </thead>
                <g:each in="${results}" var="result">
                    <tr>
                        <td class="value">
                            ${result.rank}
                        </td>
                        <td>
                            ${result.name}
                        </td>
                        %{--<td>--}%
                        %{--${result.points}--}%
                        %{--</td>--}%
                        <td class="value">
                            ${result.roundWins}
                        </td>
                        <td class="value">
                            ${result.roundByes}
                        </td>
                        <td class="value">
                            ${result.roundLosses}
                        </td>
                        <td class="value">
                            ${result.individualWins}
                        </td>
                        <td class="value">
                            ${result.individualLosses}
                        </td>
                    </tr>
                </g:each>
            </table>
        </div>
        <g:if test="${flash.title != 'Final Results'}">
            <g:actionSubmit value="Start Next Round" action="show"/>
        </g:if>
    </g:form>
</div>
</body>
</html>