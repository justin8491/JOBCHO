package jobcho.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import kosta.model.Board;
import kosta.model.Reply;
import kosta.model.Search;
// 이사하면서 : import 수정

public interface BoardMapper {
	int insertBoard(Board board);
	List<Board> listBoard(Search search,RowBounds row);
	Board detailBoard(int seq);
	int updateBoard(Board board);
	int deleteBoard(int seq);
	int countBoard(Search search);
	
	int insertReply(Reply reply);
	List<Reply> listReply(int seq);
}
