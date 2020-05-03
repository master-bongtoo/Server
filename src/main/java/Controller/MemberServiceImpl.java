package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.MemberDTO;


@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO memberDAO;

	@Override
	public int memberWriteJson(MemberDTO memberDTO) {
		return memberDAO.memberWriteJson(memberDTO);
	}

	@Override
	public MemberDTO memberViewJson(int member_num) {
		return memberDAO.memberViewJson(member_num);
	}

	@Override
	public int memberUpdateJson(MemberDTO memberDTO) {
		return memberDAO.memberUpdateJson(memberDTO);
	}

	@Override
	public int memberCreditJson(MemberDTO memberDTO) {
		return memberDAO.memberCreditJson(memberDTO);
	}

	@Override
	public int memberDeleteJson(int member_num) {
		return memberDAO.memberDeleteJson(member_num);
	}

	@Override
	public MemberDTO memberLoginJson(MemberDTO memberDTO) {
		return memberDAO.memberLoginJson(memberDTO);
	}

	@Override
	public int memberUpdatePWJson(MemberDTO memberDTO) {
		return memberDAO.memberUpdatePWJson(memberDTO);
	}

	@Override
	public int memberIDCheck(String member_id) {
		return memberDAO.memberIDCheck(member_id);
	}
}
