<%--
  Created by IntelliJ IDEA.
  User: Andry
  Date: 27.01.2019
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<h3>Хай, здесь вы можете пропарсить XML файл</h3>
<p>Сперва загрузите файл. (файл medicine.xml в папке с ресурсами)</p>
<form action="parse" method="post" enctype="multipart/form-data">
    XML <input type="file" name="file" required="required"/>
    <br>
    <p>Теперь нажмите на нужный парсер</p>
    <input type="submit" name="parser" value="DOM"/>
    <input type="submit" name="parser" value="SAX"/>
    <input type="submit"  name="parser" value="StAX"/>
    <br>
</form>
<br>
<br>
<br>
<br>
<br>
<br>
<p>можно загрузить файл medicine2.xml и посмотреть что будет</p>
</body>
</html>
