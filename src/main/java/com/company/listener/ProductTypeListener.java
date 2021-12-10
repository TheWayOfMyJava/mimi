package com.company.listener;

import com.company.pojo.ProductType;
import com.company.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 笔记：
 * 这里为什么要用监听器？而不是在界面层处理
 *
 * 答：这里实现功能是在新增商品时，给下拉列表框绑定数据，展现商品类别，而更新商品时也要绑定一遍，
 * 可以使用监听器，当整个项目启动时，监听器启动，完成类别加载，在任何时候，跳转到当前页面，类别数据都是从数据库中读取并绑定好的
 * */

@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /**
         * 这里为什么要手动从Spring容器中取出对象，而不是像在congtroller中依赖注入（@Autowired）获取商品类型的列表
         *
         * 答：spring框架也是以监听器这种方式来注册的，并且ContextLoaderListener实现的也是ServletContextListener，
         * 我们在监听器也是如此，无法保证两个监听器谁先创建，因此不能采用依赖注入
         * */
        //手工从Spring容器中取出ProductTypeServiceImpl对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //放入全局应用作用域中,供新增页面,修改页面,前台的查询功能提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
