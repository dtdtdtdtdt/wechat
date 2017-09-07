<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wx.common.utils.*,java.util.*" %>

<%
	FileUpload fu =new FileUpload();
	Map<String,String> map =fu.uploadFiles(pageContext, request);
	
	//取回调函数名 ：jsonp技术
	response.setContentType("text/html; charset=UTF-8");
	
	//function show()  因为CKEditorFuncNum参数是在地址栏中  即以GET方式传到服务器中 所以使用HttpServletRequest
	String callback =request.getParameter("CKEditorFuncNum");

	//将结果以客户端指定的函数形式，以JavaScript代码写客户端去  这样客户端的浏览器js引擎就可以运行了

	out.println("<script type =\"text/javascript\">");
	out.println("window.parent.CKEDITOR.tools.callFunction("+callback+",'"+map.get("upload_weburl")+"','上传文件成功');"); //相对路径用于显示图片
	out.println("</script>");	
	out.flush();	

%>

