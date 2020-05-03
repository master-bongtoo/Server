$(function(){
	$("#loginForm").submit(function(){
		//alert("진입");
		/*아이디 입력 검사*/
		var input_id = $("input[name='input_id']").val();
		if(!input_id) {
			alert("아이디를 입력하세요.");
			$("input[name='input_id']").focus();
			return false;
		}
		/** 비밀번호 입력검사 **/
		if(!$("input[name='input_pw']").val()) {
			alert("비밀번호를 입력하세요.");
			$("input[name='input_pw']").focus();
			return false;
		}
	});
});