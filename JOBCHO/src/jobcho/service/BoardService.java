package jobcho.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kosta.model.Board;
import kosta.model.BoardDao2;
import kosta.model.ListModel;
import kosta.model.Reply;
import kosta.model.Search;

public class BoardService {
	private static BoardService service = new BoardService();
	private static BoardDao2 dao;
	private static final int PAGE_SIZE = 2;
	
	
	public static BoardService getInstance() {
		//BoardService 객체를 불러오면 BoardDao2 객체도 같이 불러오는
		//연관관계를 설정하는 싱글톤 패턴 작업
		dao = BoardDao2.getInstance();
		return service;
	}
	
	public int insertBoardService(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		
		//파일업로드 로직
		//경로, 파일크기 제한, 인코딩, 파일이름 중복정책
		
		String uploadPath = request.getRealPath("upload");
		int size = 20 * 1024 * 1024;// 20MB
		
		MultipartRequest multi =
				new MultipartRequest(request, uploadPath, size, "utf-8",
						new DefaultFileRenamePolicy());
		
		
		Board board = new Board();
		board.setTitle(multi.getParameter("title"));
		board.setWriter(multi.getParameter("writer"));
		board.setContents(multi.getParameter("contents"));
		board.setFname("");
		
		//파일 업로드일때
		if(multi.getFilesystemName("fname") != null) {
			String fname = (String)multi.getFilesystemName("fname");
			board.setFname(fname);
		}
		
		return dao.insertBoard(board);
		
	}
	
	public ListModel listBoardService(HttpServletRequest request) throws Exception{
		
		request.setCharacterEncoding("utf-8");
		Search search = new Search();
		
		HttpSession session = request.getSession();
		
		//새로운 검색을 할 경우
		if(request.getParameterValues("area")!= null) {
			session.removeAttribute("search");
			search.setArea(request.getParameterValues("area"));
			search.setSearchKey("%"+request.getParameter("searchKey")+"%");
			session.setAttribute("search", search);
		}//체크해제 후 검색버튼만 클릭(전체 목록으로 돌아가기)
		else if(request.getParameterValues("area") == null 
				&& request.getParameter("pageNum") == null) {
			session.removeAttribute("search");//세션에서 정보를 삭제
		}
		
		//세션에 검색 정보가 있는 경우
		if(session.getAttribute("search") != null){
			search = (Search)session.getAttribute("search");
		}
		
		//페이징 처리시 필요사항
		//페이지당 글갯수, 총글개수, 현재페이지, StartPagem endPagem startRow, endRow
		
		//총글갯수
		int totalCount = dao.countBoard(search);
		//총페이지수 
		int totalPageCount = totalCount/PAGE_SIZE;
		if(totalCount % PAGE_SIZE > 0) {
			totalPageCount++;
		}
		//현재페이지
		String pageNum = request.getParameter(("pageNum"));
		if(pageNum == null) {
			pageNum = "1";
		}
		int requestPage = Integer.parseInt(pageNum);
		
		//startPage = 현재페이지 -(현재페이지 - 1) % 5 공식임
		int startPage = requestPage - (requestPage - 1) % 5;
		
		//endPage
		int endPage = 0;
		if (totalPageCount < startPage + 4) {
			endPage = (int)totalPageCount;
		}else {
			endPage = startPage + 4;
		}
		
		//startRow = (현재페이지 - 1) * 페이지당 글개수 공식
		int startRow = (requestPage - 1) * PAGE_SIZE;
		
		
		List<Board> list = dao.listBoard(startRow, search);
	   	
		ListModel listModel = 
				new ListModel(list, requestPage, totalPageCount, startPage, endPage);
		
	   	return listModel;
	}
	
	public Board detailBoardService(int seq) throws Exception{
		return dao.detailBoard(seq);
	}
	
	public int updateBoardService(Board board) throws Exception {
		return dao.updateBoard(board);
	}
	
	public int deleteBoardService(int seq) throws Exception{
		return dao.deleteBoard(seq);
	}
	
	public int insertReplyService(Reply reply) throws Exception{		
		return dao.insertReply(reply);
		
	}
	
	public List<Reply> listReplyService(int seq) throws Exception{
		List<Reply> list = dao.listReply(seq);
		
		return list;
	}
}
