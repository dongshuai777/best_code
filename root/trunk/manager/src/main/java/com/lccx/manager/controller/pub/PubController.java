package com.lccx.manager.controller.pub;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.entity.SysFile;
import com.lccx.manager.entity.SysMenu;
import com.lccx.manager.frame.ConstantClass;
import com.lccx.manager.frame.DictData;
import com.lccx.manager.service.pub.Message;
import com.lccx.manager.service.pub.Status;
import com.lccx.manager.serviceI.menu.MenuServiceI;
import com.lccx.manager.serviceI.pub.PubServiceI;
import com.lccx.manager.util.CreateDir;
import com.lccx.manager.util.PrimaryId;
import com.lccx.manager.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Pub", produces = {"application/json;charset=UTF-8"})
public class PubController {


    private Logger logger= LoggerFactory.getLogger("PubController");

    @Value("${system.name}")
    private String systemName;

    @Value("${local.upload.path}")
    private String localUploadPath;

    @Value("${local.ip}")
    private String localIp;

    @Value("${local.file.server}")
    private String localFileServer;

    @Value("${local.file.port}")
    private String localFilePort;

    @Resource
    private PubServiceI service;

    @Resource
    private MenuServiceI menuService;

    @ResponseBody
    @RequestMapping(value = "/SystemName",method = RequestMethod.GET)
    public String getSystemName(){
        JSONObject json=new JSONObject();
        json.put("success",true);
        json.put("systemName",systemName);
        return json.toJSONString();
    }

    /**
     * 获取系统字典
     * @param key 字典key
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Dict",method = RequestMethod.GET)
    public String dict(String key){
        JSONObject json=new JSONObject();
        if(Util.isCon(key)){
            List<DictData> list= ConstantClass.DICT_MAP.get(key);
            JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
            json.put("success",true);
            json.put("dict",jsonArray);
        }else{
            json.put("success",false);
            json.put("message","输入参数为空");
        }
        return json.toString();
    }


    /**
     * 文件上传到同项目的服务器
     * @param files
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/Uploads", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Message uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) throws IOException {
        Message msg = new Message();
        ArrayList<Integer> arr = new ArrayList<>();
        JSONObject json=new JSONObject();
        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    String fileName=file.getOriginalFilename();
                    String path = localUploadPath + fileName;
                    CreateDir.createDir(localUploadPath);
                    File serverFile = new File(path);
                    in = file.getInputStream();
                    out = new FileOutputStream(serverFile);

                    long size = file.getSize();
                    byte[] b = new byte[(int) size];
                    int len = 0;
                    while ((len = in.read(b)) > 0) {
                        out.write(b, 0, len);
                    }
                    //上传成功，保存记录
                    SysFile data=new SysFile();
                    String id=PrimaryId.getId();
                    data.setId(id);
                    data.setCode(id);//code暂时跟id相同
                    data.setName(fileName);
                    data.setPath(localUploadPath);
                    data.setType(fileName.substring(fileName.lastIndexOf(".") + 1));
                    String url="http://"+localIp+":"+localFilePort+"/"+localFileServer;
                    data.setUrl(url);
                    data.setIp(localIp);
                    service.saveFile(data);
                    json.put(fileName,id);
                } catch (Exception e) {
                    arr.add(i);
                } finally {
                    out.close();
                    in.close();
                }
            } else {
                arr.add(i);
            }
        }

        if (arr.size() > 0) {
            msg.setStatus(Status.ERROR);
            msg.setError("文件上传失败");
            msg.setErrorKys(arr);
        } else {
            msg.setStatus(Status.SUCCESS);
            msg.setStatusMsg("文件上传成功");
            msg.setSuccessMessage(json.toJSONString());
        }
        return msg;
    }


    /**
     * 根据文件id，获得文件信息
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Download",method = RequestMethod.GET)
    public String download(SysFile data){
        JSONObject json=new JSONObject();
        List<SysFile> list=service.getFile(data);
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));
        json.put("success",true);
        json.put("files",array);
        return json.toString();
    }

    /**
     * 查询treegrid类型
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/MenuTree",method = RequestMethod.GET)
    public String getMenu(SysMenu data){
        if(Util.isCon(data.getId())){
            data.setParentId(data.getId());
            data.setId("");
        }else{
            data.setParentId("00");
        }
        List<SysMenu> list=menuService.getMenu(data);
        JSONArray arr=new JSONArray();
        for(int i=0;i<list.size();i++){
            JSONObject json=new JSONObject();
            json.put("id", list.get(i).getId());
            json.put("name", list.get(i).getName());
            json.put("url", list.get(i).getUrl());
            json.put("parentId", list.get(i).getParentId());
            json.put("orderNo", list.get(i).getOrderNo());
            json.put("type", list.get(i).getType());
            json.put("img", list.get(i).getImg());
            json.put("state", "closed");
            arr.add(json);
        }
        return arr.toString();
    }

}
