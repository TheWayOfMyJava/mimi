package com.company.controller;

import com.company.pojo.ProductInfo;
import com.company.pojo.vo.ProductInfoVo;
import com.company.service.ProductInfoService;
import com.company.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //显示每页的记录时
    public static final int PAGE_SIZE = 5;

    //上传文件名
    String saveFileName = "";
    //切记：在界面层中，一定会有业务逻辑层的对象
    @Autowired
    ProductInfoService productInfoService;
    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第一页商品
    @RequestMapping("/split")
    public String splitPage(HttpServletRequest request,HttpSession session){
        PageInfo info = null;
        Object vo = session.getAttribute("vo");
        if (vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            //得到第一页数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "product";
    }
    //ajax分页翻页处理
    @ResponseBody//处理ajax请求，绕过视图解析器，将返回值处理成json数据
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo,HttpSession session){
        //取当前page参数的页面数据
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo, HttpSession session){
        List<ProductInfo> productInfos= productInfoService.selectCondition(vo);
        session.setAttribute("list",productInfos);
    }
    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        //提取生成文件名UUID+上传图片的后缀。jpg
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片储存的路径
        String path = request.getServletContext().getRealPath("/image_big");//存在这个文件夹中
        //转存 E:\SSM\project\mimi\image_big\78946130.15641231568465.jpg
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端json对象，封装图片的路径，为了在页面实现立即回显
        JSONObject Object = new JSONObject();
        Object.put("imgurl",saveFileName);

        return Object.toString();
    }

    //新增商品
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        int nums = -1;
        try {
            nums = productInfoService.save(info);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg","增加成功");
        }else {
            request.setAttribute("msg","增加失败");
        }
        //清空saveFileName变量中的内容，为了下次增加或修改的异步ajax的上传处理
        saveFileName = "";
        //增加成功后应该重新访问数据库，所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }

    //编辑商品
    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo info = productInfoService.getById(pid);

        model.addAttribute("prod",info);
        session.setAttribute("vo",vo);
        return "update";
    }
    //更新商品
    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request){
        int nums = -1;
        if (!"".equals(saveFileName)){
            info.setpImage(saveFileName);
        }
        try {
            nums = productInfoService.update(info);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg","修改成功");
        }else {
            request.setAttribute("msg","修改失败");
        }
        //清空saveFileName变量中的内容，为了下次修改的异步ajax的上传处理
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //删除商品
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request,int pid,ProductInfoVo vo,HttpSession session){
        int nums = -1;
        try {
            nums = productInfoService.delete(pid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (nums > 0){
            request.setAttribute("msg","删除成功");
        }else {
            request.setAttribute("msg","删除失败");
        }
        session.setAttribute("vo",vo);
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("vo");
        if (vo!= null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");

    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids, HttpServletRequest request){
        String []ps = pids.split(",");
        for (String p : ps) {
            System.out.println(p);
        }
        int nums = -1;
        nums = productInfoService.deleteBatch(ps);
        try {
            if (nums > 0){
                request.setAttribute("msg","批量删除成功");
            }else{
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception exception) {
            request.setAttribute("msg","商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
}
