package cn.edu.guet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.edu.guet.ioc.BeanFactory;

/**
 * Application Lifecycle Listener implementation class BeanInjectListener
 *
 */
public class BeanInjectListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BeanInjectListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	BeanFactory.getInstance();
    }
	
}
