<html>
<body>
<div class="main">
    <g:form name="settings">
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
                        <g:select name="howManyPlayers" from="${4..16}" onchange="addRounds(this.value)"/>
                    </td>
                    <td>
                        <g:select name="howManyRounds" from="${[3]}"/>
                    </td>
                    <td>
                        <g:select name="bestOf" from="${[3, 5]}"/>
                    </td>
                    <td>
                        <g:select name="whichFormat" from="${["Swiss"]}"/>
                    </td>
                </tr>
            </table>
        </div>
        <g:actionSubmit value="Create Tournament" action="create"/>
    </g:form>
</div>
</body>
</html>