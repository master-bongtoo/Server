$(document).ready(function(){
	/**입력 검사**/
	$("#writeForm").submit(function(){
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
