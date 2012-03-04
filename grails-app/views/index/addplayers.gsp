<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<div class="main">
    <g:form>
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
        <g:actionSubmit value="Register Players" action="register"/>
    </g:form>
</div>
</body>
</html>