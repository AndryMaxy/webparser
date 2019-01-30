<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Готово</title>
</head>
<body>
<table>
    <tr>
        <th>Название</th>
        <th>Производитель</th>
        <th>Группа</th>
        <th>Аналоги</th>
        <th>Версии</th>
    </tr>
    <c:forEach items="${drugs}" var="drug">
        <tr>
            <td><c:out value="${drug.name}"/></td>
            <td><c:out value="${drug.producer}"/></td>
            <td><c:out value="${drug.group.name}"/></td>
            <td><c:forEach items="${drug.analogs}" var="analog">
                <c:out value="${analog}"/>,
            </c:forEach> </td>
            <td><c:forEach items="${drug.versions}" var="version">
                <%--<c:out value="${version.name}"/>--%>
                <c:out value="${version.formType}"/>
                <c:out value="${version.certificate.namber}"/>
                <c:out value="${version.certificate.issueDate}"/>
                <c:out value="${version.certificate.expirationDate}"/>
                <c:out value="${version.certificate.register}"/>
            </c:forEach></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
