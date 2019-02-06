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
        <th rowspan="3">Код</th>
        <th rowspan="3">Название</th>
        <th rowspan="3">Производитель</th>
        <th rowspan="3">Группа</th>
        <th rowspan="3">Аналоги</th>
        <th colspan="10">Версии</th>

    </tr>
    <tr>
        <th rowspan="2">Форма</th>
        <th colspan="4">Сертификат</th>
        <th colspan="3">Упаковка</th>
        <th colspan="2">Дозировка</th>
    </tr>
    <tr>
        <th>Номер</th>
        <th>Дата получения разрешения</th>
        <th>Дата окончания разрешения</th>
        <th>Регистрационный орган</th>
        <th>Количество в упаковке</th>
        <th>Цена</th>
        <th>Тип упаковки</th>
        <th>Дозировка</th>
        <th>Период</th>
    </tr>

    <tbody align="center">
    <c:forEach items="${medicines}" var="medicine">
        <tr>
            <td rowspan="${medicine.versionsSize}"><c:out value="${medicine.code}"/></td>
            <td rowspan="${medicine.versionsSize}"><c:out value="${medicine.name}"/></td>
            <td rowspan="${medicine.versionsSize}"><c:out value="${medicine.producer}"/></td>
            <td rowspan="${medicine.versionsSize}"><c:out value="${medicine.group.name}"/></td>
            <td rowspan="${medicine.versionsSize}">
                <c:forEach items="${medicine.analogs}" var="analog">
                <c:out value="${analog}"/> <br>
                </c:forEach>
            </td>
            <td><c:out value="${medicine.versions[0].versionType.name}"/></td>
            <td><c:out value="${medicine.versions[0].certificate.number}"/></td>
            <td><c:out value="${medicine.versions[0].certificate.issueDate}"/></td>
            <td><c:out value="${medicine.versions[0].certificate.expirationDate}"/></td>
            <td><c:out value="${medicine.versions[0].certificate.register.name}"/></td>
            <td><c:out value="${medicine.versions[0].medicinePackage.count}"/></td>
            <td><c:out value="${medicine.versions[0].medicinePackage.price}"/></td>
            <td><c:out value="${medicine.versions[0].medicinePackage.type.name}"/></td>
            <td><c:out value="${medicine.versions[0].dosage.period}"/></td>
            <td><c:out value="${medicine.versions[0].dosage.value}"/></td>
        </tr>
        <c:forEach items="${medicine.versions}" begin="1" end="${medicine.versionsSize - 1}" var="version">
            <tr>
                <td><c:out value="${version.versionType.name}"/></td>
                <td><c:out value="${version.certificate.number}"/></td>
                <td><c:out value="${version.certificate.issueDate}"/></td>
                <td><c:out value="${version.certificate.expirationDate}"/></td>
                <td><c:out value="${version.certificate.register.name}"/></td>
                <td><c:out value="${version.medicinePackage.count}"/></td>
                <td><c:out value="${version.medicinePackage.price}"/></td>
                <td><c:out value="${version.medicinePackage.type.name}"/></td>
                <td><c:out value="${version.dosage.period}"/></td>
                <td><c:out value="${version.dosage.value}"/></td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
