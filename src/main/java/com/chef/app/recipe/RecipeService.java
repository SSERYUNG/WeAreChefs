package com.chef.app.recipe;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chef.app.file.FileManager;
import com.chef.app.member.MemberDTO;

@Service
public class RecipeService {

	@Autowired
	private RecipeDAO recipeDAO;
	@Autowired
	private FileManager fileManager;

	public String uploadContentImage(MultipartFile upload, HttpSession session) throws Exception {

		ServletContext servletContext = session.getServletContext();
		String path = servletContext.getRealPath("resources/upload/recipecontents");

		String fileName = fileManager.fileSave(path, upload);
		return fileName;

	}

	public List<RecipeDTO> recipeList(RecipePager recipePager) throws Exception {
//		1.rownum 계산
		Long perBlock = 5L; // 페이지 버튼 몇개 보여줄건지
		// 한페이지에 게시글 몇개 보여줄건지
		Long totalRow = recipeDAO.getTotalCount(recipePager);
		System.out.println("total " + totalRow);
		if (totalRow == 0) {
			totalRow = 1L;
		}

		// pager.makeNum(recipeDAO.getTotalCount(pager),9L,5L);
		// Long totalCount = recipeDAO.getTotalCount(pager);
		recipePager.makeRow(9L);
		recipePager.makeNum(totalRow, 9L, perBlock);

		System.out.println("Start Row: " + recipePager.getStartRow());
		System.out.println("Last Row: " + recipePager.getLastRow());
		List<RecipeDTO> ar = recipeDAO.recipeList(recipePager);

		System.out.println("recipePager.getPage() " + recipePager.getPage());

		return ar;
	}

	public RecipeDTO recipeDetail(RecipeDTO recipeDTO) {
		int num = recipeDAO.hit(recipeDTO);
		return recipeDAO.recipeDetail(recipeDTO);

	}

	@Transactional
	public int recipeAdd(RecipeDTO recipeDTO, MultipartFile attach, HttpSession session) throws Exception {
		recipeDAO.recipeAdd(recipeDTO);
		// Long RecipeNnm = recipeDTO.getRecipe_num();
		Long RecipeNnm = recipeDTO.getRecipe_num();
		// recipeDTO.setRecipe_num(RecipeNnm);

		System.out.println("recipeDTO.getRecipe_num " + recipeDTO.getRecipe_num());
		System.out.println("RecipeNnm " + RecipeNnm);

		ServletContext servletContext = session.getServletContext();
		String path = servletContext.getRealPath("resources/upload/recipes");

		System.out.println(path);

		String fileName = fileManager.fileSave(path, attach);

		RecipeImgFileDTO recipeImgFileDTO = new RecipeImgFileDTO();

		recipeImgFileDTO.setFile_name(fileName);
		recipeImgFileDTO.setRecipe_num(RecipeNnm);

		System.out.println("setFile_name " + fileName);
		System.out.println("setRecipe_num " + RecipeNnm);

		int result = recipeDAO.mainImg(recipeImgFileDTO);

		return result;

	}

	public List<Map<String, Object>> categoryCount() throws Exception {

		return recipeDAO.categoryCount();
	}

	public int recipeReview(RecipeReviewDTO recipeReviewDTO) {
		return recipeDAO.recipeReview(recipeReviewDTO);
	}

	public List<RecipeReviewDTO> reviewList(RecipeReviewDTO recipeReviewDTO) {

		return recipeDAO.reviewList(recipeReviewDTO);
	}

	public int hit(RecipeDTO recipeDTO) {
		return recipeDAO.hit(recipeDTO);
	}

	// 댓글달기
	public int recipeReply(RecipeReplyDTO recipeReplyDTO) {
		if (recipeReplyDTO.getBoard_content().isEmpty()) {
			recipeReplyDTO.setBoard_content(" ");
		}

		return recipeDAO.recipeReply(recipeReplyDTO);
	}

	public int reviewUpdateInsert(RecipeReviewDTO recipeReviewDTO) throws Exception {
		System.out.println("service " + recipeReviewDTO.getBoard_content());
		System.out.println("service " + recipeReviewDTO.getReview_num());

		return recipeDAO.reviewUpdateInsert(recipeReviewDTO);
	}

	public List<RecipeReplyDTO> replyList(RecipeReplyDTO recipeReplyDTO) {

		return recipeDAO.replyList(recipeReplyDTO);
	}

	@Transactional
	public int recipeComment(RecipeReplyDTO recipeReplyDTO) {
		// RecipeDTO recipeDTO;
		Long ref = recipeReplyDTO.getRecipe_reply_num();

		recipeReplyDTO.setRef(ref);

		Long maxStep = 0L;
		Long maxDepth = 0L;
		// List<RecipeReplyDTO> list = RecipeDTO.getRef();

		List<RecipeReplyDTO> parents = recipeDAO.findParent(recipeReplyDTO);
		for (RecipeReplyDTO p : parents) {

			if (p.getStep() > maxStep) {
				maxStep = p.getStep();
			}
			if (p.getDepth() > maxDepth) {
				maxDepth = p.getDepth();
			}

			recipeDAO.stepUpdate(p);
		}

		recipeReplyDTO.setStep(maxStep + 1);
		recipeReplyDTO.setDepth(maxDepth + 1);

		return recipeDAO.adminReplySubmit(recipeReplyDTO);
	}

	// 부모글의 모든 자식글 찾기
	public List<RecipeReplyDTO> getReplies(Long recipe_reply_num) {
		RecipeReplyDTO recipeReplyDTO = new RecipeReplyDTO();
		recipeReplyDTO.setRecipe_reply_num(recipe_reply_num);

		return recipeDAO.findParent(recipeReplyDTO);
	}

	public int recipeUpdate(RecipeDTO recipeDTO, MultipartFile attach, HttpSession session) throws Exception {

		int result = recipeDAO.recipeUpdate(recipeDTO);
		Long recipeNum = recipeDTO.getRecipe_num();

		if (!attach.isEmpty()) {

			ServletContext servletContext = session.getServletContext();
			String path = servletContext.getRealPath("resources/upload/recipes");
			System.out.println(path);

			String fileName = fileManager.fileSave(path, attach);
			RecipeImgFileDTO recipeImgFileDTO = new RecipeImgFileDTO();
			recipeImgFileDTO.setFile_name(fileName);
			recipeImgFileDTO.setRecipe_num(recipeNum);

			result = recipeDAO.updateRecipeImg(recipeImgFileDTO);

			return result;
		}
		String notChange = recipeDTO.getRecipeImgFileDTO().getFile_name();

		RecipeImgFileDTO recipeImgFileDTO = new RecipeImgFileDTO();
		recipeImgFileDTO.setFile_name(notChange);
		recipeImgFileDTO.setRecipe_num(recipeNum);

		result = recipeDAO.updateRecipeImg(recipeImgFileDTO);

		return result;

		// return recipeDAO.recipeUpdate(recipeDTO);
	}

	public int recipeDelete(RecipeDTO recipeDTO) {
		return recipeDAO.recipeDelete(recipeDTO);
	}

	public int reviewDelete(RecipeReviewDTO recipeReviewDTO) {
		return recipeDAO.reviewDelete(recipeReviewDTO);
	}

	public int replyDelete(RecipeReplyDTO recipeReplyDTO) {
		return recipeDAO.replyDelete(recipeReplyDTO);
	}

	public int replyUpdateInsert(RecipeReplyDTO recipeReplyDTO) {
		return recipeDAO.replyUpdateInsert(recipeReplyDTO);
	}

	public List<RecipeDTO> wishList(MemberDTO memberDTO) {
		return recipeDAO.wishList(memberDTO);
	}

	public int addWish(RecipeDTO recipeDTO) {
		return recipeDAO.addWish(recipeDTO);

	}

	public int wishUpdate(RecipeDTO recipeDTO) {

		return recipeDAO.wishUpdate(recipeDTO);
	}

	public Long bookMark(RecipeDTO recipeDTO) {
		return recipeDAO.bookMark(recipeDTO);
	}

	public Double ratingTotal(RecipeReviewDTO recipeReviewDTO) {
		return recipeDAO.ratingTotal(recipeReviewDTO);
	}

}
