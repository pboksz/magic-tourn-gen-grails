<html>
<body>
<div class="main">
    <g:form>
        <div class="show">
            <table>
                <thead>
                <tr>
                    <th>
                        Player Name
                    </th>
                    <th>
                        Player Wins
                    </th>
                    <th class="versus">
                        VS
                    </th>
                    <th>
                        Opponent Name
                    </th>
                    <th>
                        Opponent Wins
                    </th>
                </tr>
                </thead>
                <g:each in="${listOfPairs}" var="pair">
                     <tr>
                         <td>
                             ${pair.name}
                             <g:hiddenField name="player" value="${pair.name}"/>
                             %{--<div class="drop">--}%
                                 %{--<g:link action="dropplayer" params="[dropped: pair.name, getsbye: pair.opponent]" onclick="return dropCheck('${pair.name}')">--}%
                                     %{--<g:img dir="images" file="delete.png" alt="Drop Player"/>--}%
                                 %{--</g:link>--}%
                             %{--</div>--}%
                         </td>
                         <td>
                             <g:if test="${pair.opponent != 'Bye'}">
                                 <g:textField name="wins" maxlength="1" style="width: 20px"/>
                             </g:if>
                             <g:else>
                                 <g:hiddenField name="wins" value="-1"/>
                             </g:else>
                         </td>
                         <td class="versus">
                             <strong>VS</strong>
                         </td>
                         <td onmouseover="dropOption">
                             ${pair.opponent}
                             <g:hiddenField name="opponent" value="${pair.opponent}"/>
                             %{--<div class="drop">--}%
                                 %{--<g:link action="dropplayer" params="[dropped: pair.opponent, getsbye: pair.name]" onclick="return dropCheck('${pair.opponent}')">--}%
                                     %{--<g:img dir="images" file="delete.png" alt="Drop Player"/>--}%
                                 %{--</g:link>--}%
                             %{--</div>--}%
                         </td>
                         <td>
                             <g:if test="${pair.opponent != 'Bye'}">
                                 <g:textField name="losses" maxlength="1" style="width: 20px"/>
                             </g:if>
                             <g:else>
                                 <g:hiddenField name="losses" value="-1"/>
                             </g:else>
                         </td>
                     </tr>
                    </g:each>
            </table>
        </div>

        <div class="options">
            <g:actionSubmit value="Submit Round Results" action="nextround"/>
        </div>
    </g:form>
</div>
</body>
</html>