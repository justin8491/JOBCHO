package jobcho.action;

public class ActionForward {
//뷰 경로 이동방식 결정하는 클래스
	
	private boolean isRedirect;
	//redirect 할건지 despatcher 할건지 정하는 변수
	private String path;
	//경로 적는 변수
	
	public ActionForward() {}
	
	public ActionForward(boolean isRedirect, String path) {
		super();
		this.isRedirect = isRedirect;
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
