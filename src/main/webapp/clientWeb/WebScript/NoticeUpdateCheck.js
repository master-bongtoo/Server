$(document).ready(function(){
	/**입력 검사**/
	$("#modifyForm").submit(function(){
		/*제목 입력 검사*/
		var title = $("textarea[name='event_subject1']").val();
		var subtitle = $("textarea[name='event_subject2']").val();
		var content = $("textarea[name='event_content']").val();
		
		if(title=='') {
			alert("제목을 입력하세요.");
			$("textarea[name='event_subject1']").focus();
			return false;
		}
		/*부제목 입력 검사*/
		if(!subtitle) {
			alert("부제목을 입력하세요.");
			$("textarea[name='event_subject2']").focus();
			return false;
		}
		/*내용 입력 검사*/
		if(!content) {
			alert("내용을 입력하세요.");
			$("textarea[name='event_content']").focus();
			return false;
		}
	});
	
	/**삭제 확인**/
	$("#btndelete").click(function(){
		if (confirm("정말 삭제하시겠습니까??") == true){    //확인
			var event_index = getParam("event_index");
			location.href="../../event/eventDeleteJson.a?code=1&event_index="+event_index;
		 }else{   //취소
		     return false;
		 }
	});
	
	// url 에서 parameter 추출
	function getParam(sname) {
	    var params = location.search.substr(location.search.indexOf("?") + 1);
	    var sval = "";
	    params = params.split("&");
	    for (var i = 0; i < params.length; i++) {
	        temp = params[i].split("=");
	        if ([temp[0]] == sname) { sval = temp[1]; }
	    }
	    return sval;
	}
});
