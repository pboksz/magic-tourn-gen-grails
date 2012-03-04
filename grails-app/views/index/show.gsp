<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 5:16 PM
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
                        Player Name
                    </th>
                    <th>
                        Player Wins
                    </th>
                    <th>
                        Opponent Name
                    </th>
                    <th>
                        Opponent Wins
                    </th>
                </tr>
                </thead>
                <g:each in="${roundPairs}" var="pair">
                    <g:each in="${pair.value}" var="info">
                        <tr>
                            <td>
                                ${info.name}
                                <g:hiddenField name="player" value="${info.name}"/>
                                <div class="drop">
                                    <g:link action="dropplayer" params="[dropped: info.name, getsbye: info.opponent]" onclick="return dropCheck('${info.name}')">
                                        <g:img dir="images" file="delete.png" alt="Drop Player"/>
                                    </g:link>
                                </div>
                            </td>
                            <td>
                                <g:if test="${info.opponent != 'Bye'}">
                                    <g:textField name="wins" maxlength="1" style="width: 20px"/>
                                </g:if>
                                <g:else>
                                    <g:hiddenField name="wins" value="100"/>
                                </g:else>
                            </td>
                            <td onmouseover="dropOption">
                                ${info.opponent}
                                <g:hiddenField name="opponent" value="${info.opponent}"/>
                                <div class="drop">
                                    <g:link action="dropplayer" params="[dropped: info.opponent, getsbye: info.name]" onclick="return dropCheck('${info.opponent}')">
                                        <g:img dir="images" file="delete.png" alt="Drop Player"/>
                                    </g:link>
                                </div>
                            </td>
                            <td>
                                <g:if test="${info.opponent != 'Bye'}">
                                    <g:textField name="losses" maxlength="1" style="width: 20px"/>
                                </g:if>
                                <g:else>
                                    <g:hiddenField name="losses" value="100"/>
                                </g:else>
                            </td>
                        </tr>
                    </g:each>
                </g:each>
            </table>
        </div>

        <div class="options">
            <g:actionSubmit value="Next Round" action="nextround"/>
        </div>
    </g:form>
</div>
</body>
</html>