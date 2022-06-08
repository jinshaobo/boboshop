package com.jinshaobo.controller;

import com.github.pagehelper.PageInfo;
import com.jinshaobo.pojo.ProductInfo;
import com.jinshaobo.pojo.vo.ProductInfoVo;
import com.jinshaobo.service.ProductInfoService;
import com.jinshaobo.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    String saveFileName = "";
    public static final int PAGE_SIZE = 5;

    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第一页的5条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        ProductInfoVo vo = (ProductInfoVo) request.getSession().getAttribute("prodVo");
        if (vo != null) {
            info = productInfoService.splitPageVo(vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else {
            info = productInfoService.splitPage(1,PAGE_SIZE);
        }

        request.setAttribute("info",info);
        return "product";
    }

    //ajax翻页处理,不返回数据，只更新PageInfo中的数据
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //获取当前page页面的数据
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);

    }

    //ajax文件上传,用到MultipartFile
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //生成随机名UUID+原图片后缀
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //将图片存储到哪里,设置一个路径
        String path = request.getServletContext().getRealPath("/image_big");

        //转存。到这个路径
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将图片名以json对象形式返回页面，使用json依赖
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();//toString才能转为json格式

    }

    //将用户新增的商品信息放到数据库
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request) {
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int num = -1;
        try {
            num = productInfoService.save(info);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        if (num > 0){
            //保存成功
            request.setAttribute("msg","新增商品成功");
        }else {
            //失败
            request.setAttribute("msg","新增商品失败");
        }
        //清空saveFileName变量中的内容，为了下次增加或修改的异步ajax的上传处理
        saveFileName = "";
        //增加商品成功后应该重新访问数据库，所以跳转到分页显示的action
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo,Model model,HttpSession session){
        System.out.println("one.action后台1");
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod",info);
        //将多条件查询及页码放入session中，更新处理后分页时读取条件和页码进行处理
        session.setAttribute("prodVo",vo);
        System.out.println("one.action后台2");
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        //完成更新处理
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(num > 0){
            //更新成功
            request.setAttribute("msg","更新成功");
        }else {
            //更新失败
            request.setAttribute("msg","更新失败");
        }
        //清空saveFileName，为下次更新做准备
        saveFileName = "";
        return "forward:/prod/split.action";

    }

    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            //删除成功
            request.setAttribute("msg","删除成功！");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else {
            //删除失败
            request.setAttribute("msg","删除失败！");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        //取得第一页的数据
        PageInfo info = null;
        ProductInfoVo vo = (ProductInfoVo)request.getSession().getAttribute("deleteProdVo");
        if (vo != null) {
            info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        }else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }

        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");

    }

    //批量删除商品
    @RequestMapping("/deleteBatch")

    public String deleteBatch(String pids,HttpServletRequest request){
        //pids="1,2,3",需要将pids字符串转换为数组才能调用service
        //转换为ps[] = [1,2,3]
        String[] ps = pids.split(",");

        try {
            int num = productInfoService.deleteBatch(ps);
            if (num > 0) {
                request.setAttribute("msg","批量删除成功！");
            }else {
                request.setAttribute("msg","批量删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除！");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selecCondition(vo);
        session.setAttribute("list",list);

    }


}
