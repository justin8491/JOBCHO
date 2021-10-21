package jobcho.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
// 들어오는 URL마다 다 다르게 설정하는 번거로움과 위험성을 줄이기 위해
// 인터페이스로 하나의 매뉴얼을 만든다
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
