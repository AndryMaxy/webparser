<%@ page import="java.util.List" %>
<%@ page import="by.epam.akulich.webparser.bean.Drug" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Andry
  Date: 27.01.2019
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Готово</title>
</head>
<body>
<table border="1px">
    <tr>
        <th>Название</th>
        <th>Производитель</th>
        <th>Группа</th>
    </tr>
    <%--<%List<Drug> drugs =(List<Drug>) request.getAttribute("drugs");%>--%>
    <%--<%--%>
        <%--out.println(request.getAttribute("hmmm"));%>--%>
    <%--<%--%>
        <%--for (Drug drug : drugs) {--%>
            <%--out.println(drug.getName());--%>
        <%--}--%>
        <%--%>--%>

    <c:forEach items="${requestScope.drugs}" var="item">
        <tr>
            <td>Текст <c:out value="${item}"/><br></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
