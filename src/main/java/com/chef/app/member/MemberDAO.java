package com.chef.app.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chef.app.food.StoreOrderDTO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String NAMESPACE = "com.chef.app.member.MemberDAO.";
	
	
	public int duplication(MemberDTO memberDTO)throws Exception {
		return sqlSession.selectOne(NAMESPACE+ "duplication" , memberDTO);
	}
	
	public int introducesDelete(MemberDTO memberDTO) throws Exception {
		return sqlSession.update(NAMESPACE + "introducesDelete", memberDTO);
	}
	
	public int mypageUpdate(MemberDTO memberDTO)throws Exception {
		return sqlSession.update(NAMESPACE + "mypageUpdate", memberDTO);
	}
	
	public MemberDTO mypage(MemberDTO memberDTO) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "mypage", memberDTO);
	}
	
	public int kakaoCheck(MemberDTO memberDTO) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "kakaoCheck", memberDTO);
	}
	
	public MemberDTO kakaologin2(MemberDTO memberDTO) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "kakaologin2", memberDTO);
	}
	
	public int kakaologin(MemberDTO memberDTO) throws Exception {
		System.out.println("== Kakao DAO ==");
		return sqlSession.insert(NAMESPACE + "kakaologin", memberDTO);
	}
	
	public MemberDTO login(MemberDTO memberDTO) throws Exception {
		System.out.println("== login DAO ==");	
		return sqlSession.selectOne(NAMESPACE + "login", memberDTO);
	}
	
	public int join(MemberDTO memberDTO) throws Exception {
		System.out.println("== Join DAO ==");
		return sqlSession.insert(NAMESPACE + "join", memberDTO);
	}
	
	public List<StoreOrderDTO> buyList(Map<String, Object> comeMap) throws Exception {
		return sqlSession.selectList(NAMESPACE+"buyList", comeMap);
	}
	
	public int cancleRequest (StoreOrderDTO storeOrderDTO) throws Exception{
		return sqlSession.update(NAMESPACE+"cancleRequest", storeOrderDTO);
	}
}
