<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Готово</title>
</head>
<body>
<table border="1" align="center" valign="center">
    <tr>
        <th rowspan="2">Код</th>
        <th rowspan="2">Название</th>
        <th rowspan="2">Производитель</th>
        <th rowspan="2">Группа</th>
        <th rowspan="2">Аналоги</th>
        <th colspan="10">Версии</th>

    </tr>
    <tr>
        <th>Тип</th>
        <th>Номер</th>
        <th>Дата получения разрешения</th>
        <th>Дата окончания разрешения</th>
        <th>Регистрационный орган</th>
        <th>Количество в упаковке</th>
        <th>Цена</th>
        <th>Тип упаковки</th>
        <th>Дозировка</th>
        <th>Тип дозировки</th>
    </tr>
    <c:forEach items="${drugs}" var="drug">
        <tr align="center">
            <td><c:out value="${drug.code}"/></td>
            <td><c:out value="${drug.name}"/></td>
            <td><c:out value="${drug.producer}"/></td>
            <td><c:out value="${drug.group.name}"/></td>
            <td><c:forEach items="${drug.analogs}" var="analog">
                <table>
                    <tr>
                        <td><c:out value="${analog}"/></td>
                    </tr>
                </table>
            </c:forEach></td>
            <c:forEach items="${drug.versions}" var="version">
                <td><c:out value="${version.versionType.name}"/></td>
                <td><c:out value="${version.certificate.number}"/></td>
                <td><c:out value="${version.certificate.issueDate}"/></td>
                <td><c:out value="${version.certificate.expirationDate}"/></td>
                <td><c:out value="${version.certificate.register.name}"/></td>
                <td><c:out value="${version.drugPackage.count}"/></td>
                <td><c:out value="${version.drugPackage.price}"/></td>
                <td><c:out value="${version.drugPackage.type.name}"/></td>
                <td><c:out value="${version.dosage.value}"/></td>
                <td><c:out value="${version.dosage.type.name}"/></td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>