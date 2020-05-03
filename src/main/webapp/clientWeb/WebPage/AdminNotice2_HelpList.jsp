<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<%
	int pg = 1; //기본 페이지값 
	if (request.getParameter("pg") != null) { //받아온 pg값이 있을때, 다른페이지일때 
		pg = Integer.parseInt(request.getParameter("pg")); // pg값을 저장 
	}
	
	int pre = pg-1;
	int next = pg+1;
	if(pre<1){pre=1;}
%>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/bongtoo/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){                                                     
	$.ajax({
		url: "../../event/eventListJson.a?event_type=2",
		type: "get",
		dataType: "json",
		cache: false,
		timeout: 30000,
		success : function(json){
			//배열에 접근
			var item = json.item;
			//배열의 길이만큼 반복문으로 처리
			for(var i=0; i<item.length; i++){
				//i번째 요소는 JSON데이터 자체이다.
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
				html += '<th>'+'도움말'+'</th>';
				html += '<th>'+'hong'+'</th>';
				html += '<th>'+'GM홍별이'+'</th>';
				html += '<th>'+'<a href="/bongtoo/clientWeb/WebPage/AdminNotice_ModifyForm.jsp?event_index='+event_index+'&event_type='+event_type+'">'+event_subject1+'</th>';
				html += '<th>'+event_subject2+'</th>';
				html += '<th>'+event_date+'</th>';
				$("#result").append(html);
				
			}
		},
		error: function(){
			alert("통신 실패");
		}
	});
});
</script>
<title>Envelope</title>

<link rel="stylesheet" type="text/css" href="../../WebCSS/WebStyle.css">
</head>

<jsp:include page="/clientWeb/WebPage/WebHeader.jsp"></jsp:include>

<body>

<strong>Notice List</strong><hr><br>
<input type="button" id="QnaNotice1" value="공지사항" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice1_NoticeList.jsp'">
<input type="button" id="QnaNotice2" value="도움말" class="btn btn-blue" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice2_HelpList.jsp'">
<input type="button" id="QnaNotice3" value="자주 묻는 질문" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice3_FaqList.jsp'">
<input type="button" id="QnaNotice4" value="이벤트" class="btn" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice4_EventList.jsp'">
<br><br><br>
  
  <table>
  	<thead>
  		<tr>
			<th>No</th>
			<th>NoticeType</th>
			<th>AdminId</th>
			<th>AdminName</th>
			<th style="width: 500px;">Title</th>
			<th>SubTitle</th>
			<th>LastRenewalTime</th>
		</tr>
 	</thead>
 	
    <tbody id="result">
  		
    </tbody>
  </table><br>
  <input type="button" id="add" value="Add" class="btn btn-blue" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice_WriteForm.jsp'">
  <input type="button" value="◀" class="btn btn-red" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice2_HelpList.jsp?event_type=1&pg=<%=pre%>'">
  <input type="button" value="<%=pg %>" class="btn btn-red" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice2_HelpList.jsp?event_type=1&pg=<%=pg%>'">
  <input type="button" value="▶" class="btn btn-red" onclick="location.href='/bongtoo/clientWeb/WebPage/AdminNotice2_HelpList.jsp?event_type=1&pg=<%=next%>'">
  <br><br><br>
</body>
</html>