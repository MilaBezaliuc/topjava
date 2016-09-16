<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        .tstyle {
            border-top: solid thick #CCCCCC;
            border-bottom: solid thick #CCCCCC;
        }

        tr {
            border-bottom: solid thin #CCCCCC;
        }

        .norm {color: green;}
        .exceed {color: red;}
    </style>
</head>
<body>
<h2 align="center">Meal List</h2>
<table width="70%" align="center" class="tstyle">
    <tr>
        <th width="20%" align="left">Date</th>
        <th width="20%" align="left">Description</th>
        <th width="20%" align="left">Calories</th>
        <th colspan="2"></th>
    </tr>
    <tbody>
    <c:forEach items="${mealList}" var="meal">
            <tr class="${meal.isExceed()? 'exceed' : 'norm'}">
                <td>${meal.parseDate(meal.getDateTime())}</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td>Edit</td>
                <td>Delete</td>
            </tr>

    </c:forEach>
    </tbody>
</table>
</body>
</html>
