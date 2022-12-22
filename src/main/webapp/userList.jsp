<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome ${username}!</title>
    <style>
        .header {
            text-align: center;
        }

        .container {
            width: 40%;
            margin: 0 auto;
            text-align: center;
        }

        .container__title {
            font-size: 18px;
            font-weight: 600;
            padding-bottom: 5px;
            border-bottom: 1px solid black;
        }

        table {
            width: 100%;
            padding: 5px;
        }

        thead td {
            border-width: 2px;
            font-size: 18px;
            font-weight: 600;
        }

        td {
            padding: 5px;
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h3 class="header">Welcome ${username}!</h3>
<div class="container">
    <h4 class="container__title">User list</h4>
    <table>
        <thead>
        <tr>
            <td>Login</td>
            <td>Name</td>
        </tr>
        </thead>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.login}</td>
                <td>${user.name}</td>
            </tr>
        </c:forEach>
    </table>

    <a href="<c:url value="/welcome"/>">Back</a>
    <a href="<c:url value="/logout"/>">Logout</a>
</div>
</body>
</html>