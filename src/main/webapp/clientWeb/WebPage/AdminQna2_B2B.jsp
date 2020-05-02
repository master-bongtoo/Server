<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Envelope</title>

<link rel="stylesheet" type="text/css" href="../WebCSS/WebStyle.css">
</head>

<%
request.setCharacterEncoding("UTF-8");

String resultStartDate = request.getParameter("startdate");
String resultEndDate = request.getParameter("enddate");
%>

<jsp:include page="/clientWeb/WebPage/WebHeader.jsp"></jsp:include>

<body>

<strong>QNA List</strong><hr><br>
<input type="button" id="QnaPage1" value="1:1문의" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminQna1_B2C.jsp'">
<input type="button" id="QnaPage2" value="업체문의" class="btn btn-blue" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminQna2_B2B.jsp'">
<br><br><br>

  <form action="/bongtoo_server/WebPage/AdminQna1_B2C.jsp" method="post">
  	StartDate&nbsp;<input type="date" id="startdate" name="startdate" class="input-small" value="<%=resultStartDate%>">&emsp;
  	EndDate&nbsp;<input type="date" id="enddate" name="enddate" class="input-small" value="<%=resultEndDate%>">&emsp;
  	<script type="text/javascript">
  		var stdt = document.getElementById("startdate").valueAsDate;
  		var endt = document.getElementById("enddate").valueAsDate;
  	
		if ((stdt == null)||(endt == null)) {
			document.getElementById("startdate").valueAsDate = new Date();
			document.getElementById("enddate").valueAsDate = new Date();
		}
  	</script>

  	<input type="submit" id="submit" value="Search" class="btn">
  </form><br>
  
  <table>
  	<thead>
  		<tr>
			<th>RegisterDate</th>
			<th>QnaType</th>
			<th>UserId</th>
			<th>UserName</th>
			<th style="width:200px;">Title</th>
			<th style="width:100px;">PhoneNo</th>
			<th style="width:100px;">E-Mail</th>
			<th>CompleteDate</th>
			<th>QnaStatus</th>
		</tr>
 	</thead>
 	
    <tbody>
	  	<tr>
			<th>2020-03-30 07:56:02</th>
			<th>업체문의</th>
			<th>nyung1234</th>
			<th>(주)녕이</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">안녕하세요. (주)녕이 입니다.</a></th>
			<th>010-0000-0000</th>
			<th>asdf1234@nyung.com</th>
			<th>2020-03-31 21:15:11</th>
			<th>Completed</th>
		</tr>
		<tr>
			<th>2020-03-30 11:18:37</th>
			<th>업체문의</th>
			<th>gamza999</th>
			<th>감자회사</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">광고 계약 제안드립니다.</a></th>
			<th>010-9999-9999</th>
			<th>zxcv4321@gamza.com</th>
			<th></th>
			<th>Receipt</th>
		</tr>
    </tbody>
  </table>

</body>
</html>