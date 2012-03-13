<html>
<body>
<div class="main">
    <g:form>
        <div class="players">
            <table>
                <thead>
                <tr>
                    <th>
                        Player Name
                    </th>
                    <th>
                        Player Seed
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