<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome ${username}!</title>
    <style>
        .header {
            text-align: center;
        }

        .nav-bar {
            width: 30%;
            margin: 0 auto;
        }

        ul {
            display: flex;
            padding: 0;
            justify-content: space-between;
        }

        li {
            padding: 10px;
            list-style: none;
            border-bottom: 1px solid black;
        }

        a {
            font-size: 18px;
            font-weight: 600;
            text-decoration: none;
        }

        a:hover {
            color: blue;
        }
    </style>
</head>
<body>
<h3 class="header">Welcome ${username}!</h3>
<div class="nav-bar">
    <ul>
        <li><a href="<c:url value="/user_list"/>">User list</a></li>
        <li><a href="<c:url value="/logout"/>">Logout</a></li>
    </ul>
</div>

</body>
</html>