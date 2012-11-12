package com.andrewhebert.servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AndyServlet
 * 
 * this works too:
 * 
 * <servlet>
        <description>xxx</description>
        <servlet-name>xxx</servlet-name>
        <servlet-class>com.xxx.yyy</servlet-class>
        <async-supported>true</async-supported>
</servlet>
 * 
 */
@WebServlet(value="/SteveServlet", asyncSupported=true)
public class SteveServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SteveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
        AsyncContext aCtx = request.startAsync(request, response);
        
        //seems like if there is no listener attached then it
        //move on and let the request be written to by web thread
        //then also by listener thread
        aCtx.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				arg0.getSuppliedResponse().getOutputStream().write("HELLO FROM LISTENDER".getBytes());
				
			}
		});
        
        
        aCtx.start(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("DONE SLEEPING");
			
				
			}
		});
        response.getOutputStream().write("HELLO".getBytes(Charset.defaultCharset()));
        System.out.println("web thread moved on");
        
	}
	
	//reponse looks like:  HELLOHELLO FROM LISTENDER

}
