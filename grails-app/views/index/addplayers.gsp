<html>
<body>
<div class="main">
    <g:form>
        <div class="players">
            <table>
                <thead>
                <th>
                    Player Number
                </th>
                <th>
                    Player Name
                </th>
                </thead>
                <g:each in="${(1..howManyPlayers)}" var="num">
                    <tr>
                        <td>
                            Player ${num}
                        </td>
                        <td>
                            <g:textField name="${'player' + num}"/>
                        </td>
                    </tr>
                </g:each>
            </table>
        </div>
        <g:actionSubmit value="Register Players" action="register"/>
    </g:form>
</div>
</body>
</html>