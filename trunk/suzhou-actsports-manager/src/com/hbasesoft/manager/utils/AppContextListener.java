package com.hbasesoft.manager.utils;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;


@WebListener
public class AppContextListener implements ServletContextListener{
	private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);
	
    public void contextDestroyed(ServletContextEvent event)  {
        logger.info("Destroying Context...");
     
      try {    
          WebApplicationContext context = (WebApplicationContext) event.getServletContext().getAttribute(
              WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
          
          Enumeration<String> attributes = event.getServletContext().getAttributeNames();
          while(attributes.hasMoreElements())
          {
          String attr = attributes.nextElement();
          Object prop = event.getServletContext().getAttribute(attr);
          logger.info("attribute.name: {},class:{}, value:{}",attr,prop.getClass().getName());
          }
          
          String[] beanNames = context.getBeanDefinitionNames();
          
          for(String beanName:beanNames)
          {
          Object bean = context.getBean(beanName);
          logger.info("found bean attribute in ServletContext,name:{},class:{},value:{}",
                  beanName,bean.getClass().getName());                      
          if(beanName.contains("quartz")&&beanName.contains("Scheduler")){
              StdScheduler scheduler = (StdScheduler)context.getBean("org.springframework.scheduling.quartz.SchedulerFactoryBean#0");
              logger.info("发现quartz定时任务");
                     logger.info("beanName:{},className:{}",scheduler,scheduler.getClass().getName());
              if(scheduler.isStarted())
              {
              logger.info("Quartz:waiting for job complete...");
              scheduler.shutdown(true);
              logger.info("Quartz:all threads are complete and exited...");
              }
          }
          }
          
      } catch (Exception e) {
          logger.error("Error Destroying Context", e);
      }
    }

    //https://stackoverflow.com/questions/23936162/register-shutdownhook-in-web-application
    public void contextInitialized(ServletContextEvent event)  { 
          //ServletContext context = event.getServletContext();        
          //System.setProperty("rootPath", context.getRealPath("/"));
          //LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
              //ctx.reconfigure();
          /*logger.info("global setting,rootPath:{}",rootPath);
           logger.info("deployed on architecture:{},operation System:{},version:{}",
                   System.getProperty("os.arch"), System.getProperty("os.name"),
                   System.getProperty("os.version"));
           Debugger.dump();
           logger.info("app startup completed....");*/
    }
}