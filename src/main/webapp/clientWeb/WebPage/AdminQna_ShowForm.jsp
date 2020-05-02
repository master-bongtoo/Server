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

<strong>QNA Detail</strong><hr><br>

<div class="page-left">
	Information<br>
 	<table> 	
    	<thead>
  			<tr>
				<th style="width:100px;">Type</th>
				<th style="width:200px;">Value</th>
			</tr>
 		</thead>
 	
    	<tbody>
    		<tr>
				<th><strong>QnaStatus</strong></th>
				<th>Completed</th>
			</tr>
			<tr>
				<th><strong>QnaType</strong></th>
				<th>1:1문의</th>
			</tr>
	  		<tr>
				<th><strong>RegisterDate</strong></th>
				<th>2020-03-30 11:17:55</th>
			</tr>
			<tr>
				<th><strong>CompelteDate</strong></th>
				<th>2020-03-31 20:09:34</th>
			</tr>
			<tr>
				<th><strong>UserId</strong></th>
				<th>eunwho</th>
			</tr>
			<tr>
				<th><strong>UserName</strong></th>
				<th>조현영</th>
			</tr>
			<tr>
				<th><strong>E-Mail</strong></th>
				<th>eunwho@naver.com</th>
			</tr>
			<tr>
				<th><strong>PhoneNo</strong></th>
				<th>010-1234-1234</th>
			</tr>
    	</tbody>
  	</table><br>
  
 	<table> 	
  	 	<thead>
  			<tr>
				<th style="width:300px;">Title</th>
			</tr>
 		</thead>
 	
    	<tbody>
	  		<tr>
				<th>앱이 자꾸 종료됩니다.</th>
			</tr>
    	</tbody>
  	</table><br>
  
  	<table> 	
    	<thead>
  			<tr>
				<th style="width:300px;">content</th>
			</tr>
 		</thead>
 	
    	<tbody>
	  		<tr>
				<th style="width:300px;">안녕하세요. 앱을 사용하다보면 원인을 알 수 없이 자꾸 멋대로 종료됩니다. 해결 부탁드립니다. 감사합니다.</th>
			</tr>
    	</tbody>
  	</table>
</div>

<div class="page-right">
	Answer<br>
  	<textarea id="answer" name="answer" class="input-big" spellcheck="false"></textarea><br>
  	<input type="button" id="save" value="Submit" class="btn btn-red">
  	<input type="button" id="cancel" value="Cancel" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminQna1_B2C.jsp'">
</div>

</body>
</html>