<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/bongtoo/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/bongtoo/clientWeb/WebScript/NoticeUpdateCheck.js?v=1"></script>
<script type="text/javascript">
$(document).ready(function(){
	var event_index = <%= request.getParameter("event_index") %>;
	var event_type = <%= request.getParameter("event_type") %>;
	var event_type_txt='';
	var isnew_txt='';
	if(event_type==1) {
		event_type_txt='공지사항';
		$("#event_type1").prop("checked", true)
	} else if(event_type==2) {
		event_type_txt='도움말';
		$("#event_type2").prop("checked", true)
	} else if(event_type==3) {
		event_type_txt='FAQ';
		$("#event_type3").prop("checked", true)
	} else if(event_type==4) {
		event_type_txt='이벤트';
		$("#event_type4").prop("checked", true)
	}
	console.log("event_type_txt: "+event_type_txt);
	console.log(event_index);
	console.log(event_type);
	$.ajax({
		url: "../../event/eventViewJson.a?event_index="+event_index,
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
				var event_subject2 = item[i].event_subject2;
				var event_subject1 = item[i].event_subject1;
				var event_date = item[i].event_date;
				var event_img_path = item[i].event_img_path;
				var isnew = item[i].isnew;
				var event_index = item[i].event_index;
				var event_origin_img = item[i].event_origin_img;
				
				if(isnew==1) {
					isnew_txt='ON';
					$("#isnew1").prop("checked", true)
				} else if(isnew==0) {
					isnew_txt='OFF';
					$("#isnew2").prop("checked", true)
				}
				// 결과 출력
				var html='';
				
				html += '<tr><th><strong>No</strong></th><th>'
				html += event_index;
				html += '</th></tr><tr><th><strong>NoticeType</strong></th><th>';
				html += event_type_txt;
				html += '</th></tr><tr><th><strong>RegisterDate</strong></th><th>';
				html += event_date;
				html += '</th></tr><tr><th><strong>isnew</strong></th><th>';
				html += isnew_txt;
				html += '</th></tr><tr><th><strong>AdminId</strong></th><th>';
				html += 'hong';
				html += '</th>></tr><tr><th><strong>AdminName</strong></th><th>';
				html += '홍별이';
				html += '</th></tr>';

				$("#result").append(html);	
				
				//결과 출력 - edittext
				$("#event_subject1").append(event_subject1);
				$("#event_subject2").append(event_subject2);
				$("#event_content").append(event_content);
			}
		},
		error: function(){
			alert("통신 실패");
		}
	});
});
</script>

<title>Envelope</title>

<link rel="stylesheet" type="text/css" href="../WebCSS/WebStyle.css">
</head>

<jsp:include page="/clientWeb/WebPage/WebHeader.jsp"></jsp:include>

<body>
<strong>Notice Detail</strong><hr><br>

<form id="modifyForm" action="../../event/eventUpdateJson.a?code=1&event_index=<%=request.getParameter("event_index")%>" enctype="Multipart/form-data" method="post">
<div class="page-left">
	Information<br>
 	<table> 	
    	<thead>
  			<tr>
				<th style="width:100px;">Type</th>
				<th style="width:250px;">Value</th>
			</tr>
 		</thead>
 	
    	<tbody id="result">
    	</tbody>
  	</table>
  	<br>
  	Update<br>
  	<table> 	
    	<thead>
  			<tr>
				<th style="width:100px;">Type</th>
				<th style="width:250px;">Value</th>
			</tr>
 		</thead>
    	<tbody>
			<tr>
				<th><strong>NoticeType</strong></th>
				<th>
				<input type="radio" name="event_type" value="1" id="event_type1">공지사항<br>
				<input type="radio" name="event_type" value="2" id="event_type2">도움말<br>
				<input type="radio" name="event_type" value="3" id="event_type3">FAQ<br>
				<input type="radio" name="event_type" value="4" id="event_type4">이벤트
				</th>
			</tr>
	  		<tr>
				<th><strong>isNew</strong></th>
				<th>
				<input type="radio" name="isnew" value="1" id="isnew1">ON<br>
				<input type="radio" name="isnew" value="0" id="isnew2">OFF<br>
				</th>
			</tr>
			<tr>
				<th><strong>ImageUpload</strong></th>
				<th>
					<input type="file" name="event_origin_img" />
				</th>
			</tr>
    	</tbody>
  	</table><br>
</div>
<div class="page-space">
</div>
<div class="page-right">
	Title<br>
  	<textarea id="event_subject1" name="event_subject1" class="input-big" style="height: 50px;" spellcheck="false"></textarea><br>
  	SubTitle<br>
  	<textarea id="event_subject2" name="event_subject2" class="input-big" style="height: 50px;" spellcheck="false"></textarea><br>
	Content<br>
  	<textarea id="event_content" name="event_content" class="input-big" spellcheck="false"></textarea><br>
  	<input type="submit" id="btnsave" value="Save" class="btn btn-blue">
  	<input type="button" id="btndelete" value="Delete" class="btn btn-red">
  	<input type="button" id="btncancel" value="Cancel" class="btn" onclick="history.back();">
  	<br><br><br>
</div>
</form>
<div class="page-space"></div>
</body>
</html>