package Controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.MemberDTO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	// ID체크
	public int memberIDCheck(String member_id) {
		return sqlSession.selectOne("mybatis.member.memberIDCheck", member_id);
	}
	// 회원가입
	public int memberWriteJson(MemberDTO memberDTO) {
		return sqlSession.insert("mybatis.member.memberWriteJson", memberDTO);
	}
	// 로그인
	public MemberDTO memberLoginJson(MemberDTO memberDTO) {
		return sqlSession.selectOne("mybatis.member.memberLoginJson", memberDTO);
	}
	// 상세 보기
	public MemberDTO memberViewJson(int member_num) {
		return sqlSession.selectOne("mybatis.member.memberViewJson", member_num);
	}
	// 개인정보 수정(암호제외)
	public int memberUpdateJson(MemberDTO memberDTO) {
		return sqlSession.update("mybatis.member.memberUpdateJson", memberDTO);
	}
	// 개인정보 수정(암호포함)
	public int memberUpdatePWJson(MemberDTO memberDTO) {
		return sqlSession.update("mybatis.member.memberUpdatePWJson", memberDTO);
	}
	// 금액 수정
	public int memberCreditJson(MemberDTO memberDTO) {
		return sqlSession.update("mybatis.member.memberCreditJson", memberDTO);
	}
	// 삭제
	public int memberDeleteJson(int member_num) {
		return sqlSession.delete("mybatis.member.memberDeleteJson", member_num);
	}
}
