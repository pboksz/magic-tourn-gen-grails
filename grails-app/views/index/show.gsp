<%--
  Created by IntelliJ IDEA.
  User: Phill
  Date: 2/27/12
  Time: 5:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Magic Tournament Generator | Round ${roundNum}</title>
</head>

<body>
<div class="header">
    <h2>Round ${roundNum}</h2>
</div>

<div class="main">
    <g:each in="${roundPairs}" var="round">

        <div class="roundPairing">
            <table>
                <thead>
                <tr>
                    <th>
                        Match Up
                    </th>
                </tr>
                </thead>
                <g:each in="${round.value}" var="pair">
                    <tr>
                        <td>
                            ${pair.player1} vs ${pair.player2}
                        </td>
                    </tr>
                </g:each>
            </table>
        </div>
    </g:each>
</div>
</body>
</html>