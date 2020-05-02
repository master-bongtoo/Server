package Controller;

import Model.MemberDTO;


public interface MemberService {
	// CRUD 기능의 메소드
	int memberIDCheck(String member_id);
	int memberWriteJson(MemberDTO memberDTO);
	MemberDTO memberLoginJson(MemberDTO memberDTO);
	MemberDTO memberViewJson(int member_num);
	int memberUpdateJson(MemberDTO memberDTO);
	int memberUpdatePWJson(MemberDTO memberDTO);
	int memberCreditJson(MemberDTO memberDTO);
	int memberDeleteJson(int member_num);
}
