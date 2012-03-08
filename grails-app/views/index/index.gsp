<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 3:15 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
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
                        <g:select name="howManyPlayers" from="${[4, 5, 6, 7, 8]}"/><br>
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
        <g:actionSubmit value="Create Tournament" action="addsettings"/>
    </g:form>
</div>
</body>
</html>