<html>
<body>
<div class="main">
    <g:form>
        <div class="settings">
            <table>
                <thead>
                <tr>
                    <th>
                        How Many Players?
                    </th>
                    <th>
                        How Many Rounds?
                    </th>
                    <th>
                        Play To Best Of?
                    </th>
                    <th>
                        Which Format?
                    </th>
                </tr>
                </thead>
                <tr>
                    <td>
                        <g:select name="howManyPlayers" from="${[4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]}" onchange="addRounds()"/><br>
                    </td>
                    <td>
                        <g:select name="howManyRounds" from="${[3]}"/><br>
                    </td>
                    <td>
                        <g:select name="bestOf" from="${[3, 5]}"/><br>
                    </td>
                    <td>
                        <g:select name="whichFormat" from="${["Swiss"]}"/><br>
                    </td>
                </tr>
            </table>
        </div>
        <g:actionSubmit value="Create Tournament" action="create"/>
    </g:form>
</div>
</body>
</html>