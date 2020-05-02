<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/bongtoo/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){                                                     
	$.ajax({
		url: "../../question/questionListJson.a?question_type=1",
		type: "get",
		dataType: "json",
		cache: false,
		timeout: 30000,
		success : function(json){
			//배열에 접근
			var item = json.item;
			//배열의 길이만큼 반복문으로 처리
			for(var i=0; i<item.length; i++){
				var event_content = item[i].event_content;
				var event_type = item[i].event_type;
				var event_subject2 = item[i].event_subject2;
				var event_subject1 = item[i].event_subject1;
				var event_date = item[i].event_date;
				var event_img_path = item[i].event_img_path;
				var isnew = item[i].isnew;
				var event_index = item[i].event_index;
				var event_origin_img = item[i].event_origin_img;
				
				// 결과 출력
				var html='';
				html += '<tr id=notice'+event_index+'>';
				html += '<th>'+event_index+'</th>';
				html += '<th>'+'공지사항'+'</th>';
				html += '<th>'+'hong'+'</th>';
				html += '<th>'+'GM홍별이'+'</th>';
				html += '<th>'+'<a href="/bongtoo/clientWeb/WebPage/AdminNotice_ModifyForm.jsp?event_index='+event_index+'&event_type='+event_type+'">'+event_subject1+'</th>';
				html += '<th>'+event_subject2+'</th>';
				html += '<th>'+event_date+'</th>';
				html += '<th>'+'<input type="button" id="delete" value="Delete" class="btn btn-red">'+'</th>';
				$("#result").append(html);
			}
		},
		error: function(request, status, error){
			alert("code:"+request.status+"\n"+"error:"+error);
		}
	});
});
</script>
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
<input type="button" id="QnaPage1" value="1:1문의" class="btn btn-blue" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminQna1_B2C.jsp'">
<input type="button" id="QnaPage2" value="업체문의" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminQna2_B2B.jsp'">
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
			<th>2020-03-30 11:17:55</th>
			<th>1:1문의</th>
			<th>eunwho</th>
			<th>조현영</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">앱이 자꾸 종료됩니다.</a></th>
			<th>010-1234-1234</th>
			<th>eunwho@naver.com</th>
			<th>2020-03-31 20:09:34</th>
			<th>Completed</th>
		</tr>
		<tr>
			<th>2020-03-30 11:18:37</th>
			<th>1:1문의</th>
			<th>eunwho</th>
			<th>조현영</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">앱이 자꾸 종료됩니다.(2)</a></th>
			<th>010-1234-1234</th>
			<th>eunwho@naver.com</th>
			<th>2020-03-31 20:13:19</th>
			<th>Completed</th>
		</tr>
		<tr>
			<th>2020-03-30 19:22:02</th>
			<th>1:1문의</th>
			<th>gamza</th>
			<th>김지훈</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">빠른 답변 부탁드립니다.</a></th>
			<th>010-1111-2222</th>
			<th>gamza@gmail.com</th>
			<th></th>
			<th>Checking</th>
		</tr>
		<tr>
			<th>2020-03-31 01:46:44</th>
			<th>1:1문의</th>
			<th>gamza</th>
			<th>김지훈</th>
			<th><a href="/bongtoo/clientWeb/WebPage/AdminQna_ShowForm.jsp">답변을 아직 받지 못했습니다.</a></th>
			<th>010-1111-2222</th>
			<th>gamza@gmail.com</th>
			<th></th>
			<th>Receipt</th>
		</tr>
    </tbody>
  </table>

</body>
</html>