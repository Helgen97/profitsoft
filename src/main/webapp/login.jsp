<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        .login-container {
            width: 30%;
            margin: 0 auto;
            text-align: center;
        }

        .login-form {
            width: 100%;
        }

        .label {
            margin: 30px 0;
            font-size: 18px;
            font-weight: 600;
        }

        .input {
            width: 100%;
            padding: 5px;
            border: 1px solid black;
            border-radius: 10px;
        }

        .error {
            color: red;
            font-weight: 600;
            font-style: italic;
        }

        .submit-button {
            width: 20%;
            padding: 5px 20px;
            border: 2px solid black;
            border-radius: 10px;
            background: white;
        }
    </style>
</head>
<body>

<div class="login-container">
    <form action="<c:url value="/login"/>" method="post" class="login-form">
        <label for="login" class="label">Login</label>
        <input id="login" type="text" name="login" class="input">
        <label for="password" class="label">Password</label>
        <input id="password" type="password" name="password" class="input">
        <p class="error">${error}</p>
        <button type="submit" class="submit-button">Login</button>
    </form>
</div>

</body>
</html>
