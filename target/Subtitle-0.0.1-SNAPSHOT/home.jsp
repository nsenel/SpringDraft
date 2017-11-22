<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Welcome</title>
</head>
<body>
    <%    //boolean user=false;
    if (session.getAttribute("id") != null){%>
    <b>Hosgeldin ${firstname} </b></br></br>
    <c:forEach var = "i" begin = "1" end = "${unite_Sayisi}">
        <button ><a href="<c:url value = "/uniteogren/${i}"/>" class="btn btn-primary"  style='margin-top:2%'> <c:out value = '${i}'/>.ci Unite </a></button><br />
    </c:forEach>
        <button ><a href="/tekrar/" class="btn btn-primary"  style='margin-top:2%'> Tekrar${tekrar_Sayisi} </a></button><br /><br />
        <button ><a href="/logout" class="btn btn-primary"  style='margin-top:2%'> Cikis </a></button><br /><br />
    <%}else{%>
	<form:form id="loginForm" modelAttribute="login" action="loginProcess" method="post">
            <div class="panel panel-default">
                <div class="panel-heading">Giriş Yap</div>
                    <div class="panel-body">
                            <!--<input type="hidden" name="do" value="login" />-->
                            <form:label path="username">E-Posta Adresiniz</form:label>
                            <form:input path="username" name="username" id="username" class="form-control input"/>
                            <br />
                            <form:label path="password">Şifreniz</form:label>
                            <form:password path="password" name="password" class="form-control input"/>
                            <br />
                            <div id="mesaj" class="alert alert-danger" role="alert" style="display:none"></div>
                    </div>
                <div class="panel-footer text-right">
                    <form:button id="login" class="btn btn-primary">Giriş Yap</form:button>
                </div>
            </div>
        </form:form>
    <%}%>
</body>
</html>

<script>
    if (${message!=null}){
        $("#mesaj").html("<center><b>${message}</b></center>");
        $("#mesaj").slideDown(500);
    }
</script>