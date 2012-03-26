<html>
<body>
<div class="main">
    <g:form>
        <div class="results">
            <table>
                <thead>
                <tr>
                    <th colspan="3">
                        Player Information
                    </th>
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
                    <th class="names">
                        Name
                    </th>
                    <th class="names">
                        Opponents
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
                        <td class="names">
                            ${result.name}
                            <g:if test="${flash.title=="Current Standings"}">
                                <div class="drop">
                                    <g:link action="dropplayer" params="[dropped: result.name, getsbye: result.opponent]" onclick="return dropCheck('${result.name}')">
                                        <g:img dir="images" file="delete.png" alt="Drop Player"/>
                                    </g:link>
                                </div>
                            </g:if>
                        </td>
                        <td class="opponents">
                           <div class="title">View Opponents</div>
                           <div class="list">
                              <g:each in="${result.roundPairings}" var="pair">
                                 ${pair.key + ") " + pair.value}<br>
                              </g:each>
                           </div>
                        </td>
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