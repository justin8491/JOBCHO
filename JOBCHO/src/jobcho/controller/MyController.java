package jobcho.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.action.Action;
import kosta.action.ActionForward;
import kosta.action.DeleteAction;
import kosta.action.InsertAction;
import kosta.action.ListAction;
import kosta.action.UpdateAction;
import kosta.action.UpdateFormAction;
import kosta.action.DetailAction;
import kosta.action.InsertFormAction;
import kosta.action.InsertReplyAction;

@WebServlet("/board/*")
//board 이후에 어떤 경로가 오든 myController가 다 요청 받아 처리해라
public class MyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyController() {
        super();
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	//url 식별
    	String requestURI = request.getRequestURI();
    	// 		/MVC/board/insertForm.do 등의 URL이 들어옴
    	String contextPath = request.getContextPath();
    	String command = requestURI.substring(contextPath.length()+7);
    	// command는 /MVC/board/ 뒤의 URL을 추출한 변수
    	System.out.println(command);
    	//	insertForm.do만 추출하고 sysout에서 확인
    	
    	Action action = null;
    	//Action으로 선언하고 개별 클래스별로 형변환 할 수 있다
    	ActionForward forward = null;
    	 
    	 if(command.equals("insertForm.do")) {
    		 ///MVC/board/insertForm.do 주소로 들어왔을 때
    		 action = new InsertFormAction();
    		 //Action을 insertFormAction으로!
    		 try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	 }else if(command.equals("insertAction.do")) {
    		 action = new InsertAction();
    		 try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	 }else if(command.equals("listAction.do")) {
    		 action = new ListAction();
    		 try {
 				forward = action.execute(request, response);
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
    	 }else if(command.equals("detailAction.do")) {
    		 action = new DetailAction();
    		 try {
    			 forward = action.execute(request, response);
    		 } catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }else if(command.equals("updateForm.do")) {
    		 action = new UpdateFormAction();
    		 try {
    			 forward = action.execute(request, response);
    		 } catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }else if(command.equals("updateAction.do")) {
    		 action = new UpdateAction();
    		 try {
    			 forward = action.execute(request, response);
    		 } catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }else if(command.equals("deleteAction.do")) {
    		 action = new DeleteAction();
    		 try {
    			 forward = action.execute(request, response);
    		 } catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
    	 else if(command.equals("insertReplyAction.do")) {
    		 action = new InsertReplyAction();
    		 try {
    			 forward = action.execute(request, response);
    		 } catch (Exception e) {
    			 e.printStackTrace();
    		 }
    	 }
    	 
    	 
    	 
    	 if(forward != null) {
    		 //리다이렉트 vs 디스패처 결정하는 메서드
    		 if(forward.isRedirect()) {
    			 //isRedirect = true = redirect로 보내는 경우
    			 response.sendRedirect(forward.getPath());
    		 }else {
    			 //isRedirect = false = dispatcher로 요청하는 경우
    			 RequestDispatcher dispatcher = 
    					 request.getRequestDispatcher(forward.getPath());
    			 dispatcher.forward(request, response);
    		 }
    	 }
		/* 질문 : 어떤 경우에 Redirect로, Dispatcher로 하나요?
		- 요청이 다를 경우는 Redirect로, 같을 경우에는 Dispatcher로!
		- 해당하는 요청, 비즈니스 처리, 해당하는 jsp로 이동
		- 해당하는 요청, 비스지스 처리, 또 다른 .do로 해서 해당하는 jsp로 이동*/
    	 
    	 
    }//end doProcess

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
