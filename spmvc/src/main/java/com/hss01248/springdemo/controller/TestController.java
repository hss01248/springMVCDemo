package com.hss01248.springdemo.controller;

import com.hss01248.springdemo.bean.BaseNetBean;
import com.hss01248.springdemo.bean.Items;
import com.hss01248.springdemo.exception.CustomException;
import com.hss01248.springdemo.util.ContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

@Controller
@RequestMapping("/test")
public class TestController {
    private Items getItems(Integer id) throws Exception{
        Items items = new Items();
        items.setId(id);
        items.setName("英吉利海峡");
        items.setPrice(6897f);
        return items;
    }



    @RequestMapping("/getCommonJson")
    public @ResponseBody Items getCommonJson(Integer id)throws Exception{
        Items items = getItems(id);

        return items;

    }



    @RequestMapping("/getStandardJson")
    public @ResponseBody BaseNetBean getStandardJson(Integer id)throws Exception{
        Items items = getItems(id);

        return new BaseNetBean(items);

    }

    @RequestMapping(value = "/postCommonJson",method = RequestMethod.POST)
    public @ResponseBody Items postCommonJson(Integer id)throws Exception{
        Items items = getItems(id);

        if(id <0){
            throw new CustomException("id 不合法");
        }

        return items;

    }
    @RequestMapping(value = "/postStandardJson",method = RequestMethod.POST)
    public @ResponseBody BaseNetBean postStandardJson(Integer id) throws Exception{
        Items items = getItems(id);
        return new BaseNetBean(items);
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public @ResponseBody BaseNetBean upload(Integer id, MultipartFile picFile,MultipartFile picFile2) throws Exception{
        Items items = getItems(id);

        if(picFile != null){
            //String filePath = "D:\\";
            String filePath = ContextUtil.getImageDir();

            saveFile(picFile, filePath);

            saveFile(picFile2,filePath);
        }



        return new BaseNetBean(0,"上传成功",items);
    }

    private void saveFile(MultipartFile picFile, String filePath) throws IOException {
        String originalName = picFile.getOriginalFilename();
        System.out.print(originalName);
        String newFileName = UUID.randomUUID()+originalName.substring(originalName.lastIndexOf("."));
        File file = new File(filePath,newFileName);
        System.out.print(file.getAbsolutePath());
        file.createNewFile();
        picFile.transferTo(file);
    }


}
