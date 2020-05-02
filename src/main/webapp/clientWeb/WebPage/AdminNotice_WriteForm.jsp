<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Envelope</title>
<script type="text/javascript" src="/bongtoo/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/bongtoo/clientWeb/WebScript/NoticeAddCheck.js?v=1"></script>

<link rel="stylesheet" type="text/css" href="../WebCSS/WebStyle.css">
</head>

<jsp:include page="/clientWeb/WebPage/WebHeader.jsp"></jsp:include>

<body>

<strong>Notice Detail</strong><hr><br>

<form id="writeForm" action="../../event/eventWriteJson.a?code=1" enctype="Multipart/form-data" method="post">
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
				<th><strong>NoticeType</strong></th>
				<th>
				<input type="radio" name="event_type" value="1" checked="checked">공지사항<br>
				<input type="radio" name="event_type" value="2">도움말<br>
				<input type="radio" name="event_type" value="3">FAQ<br>
				<input type="radio" name="event_type" value="4">이벤트
				</th>
			</tr>
	  		<tr>
				<th><strong>isNew</strong></th>
				<th>
				<input type="radio" name="isnew" value="1" checked="checked">ON<br>
				<input type="radio" name="isnew" value="0">OFF<br>
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

<div class="page-right">
	Title<br>
  	<textarea id="event_subject1" name="event_subject1" class="input-big" style="height: 50px;" spellcheck="false"></textarea><br>
  	SubTitle<br>
  	<textarea id="event_subject2" name="event_subject2" class="input-big" style="height: 50px;" spellcheck="false"></textarea><br>
	Content<br>
  	<textarea id="event_content" name="event_content" class="input-big" spellcheck="false"></textarea><br>
  	<input type="submit" id="save" value="Save" class="btn btn-blue">
  	<input type="button" id="cancel" value="Cancel" class="btn" onclick="history.back();">
  	<br><br><br>
</div>
</form>


</body>
</html>