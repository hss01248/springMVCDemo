package com.hss01248.springdemo.controller;

import com.hss01248.springdemo.bean.BaseNetBean;
import com.hss01248.springdemo.util.FileUtil;
import com.hss01248.springdemo.util.MyLog;
import com.hss01248.springdemo.util.thread.ThreadPoolFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8 0008.
 */
@Controller
@RequestMapping("/jsoup")
public class JsoupController {




    /**
     *

     大图地址:
     http://ci2.pornhub.phncdn.com/pics/albums/000/237/566/2360976/(m=e-yaaGqaa)original_2360976.jpg


     小图与大图地址:
     http://i1.cdn2b.image.pornhub.phncdn.com/pics/albums/000/237/566/2360980/(m=eiJ_8b)original_2360980.jpg
     http://i1.cdn2b.image.pornhub.phncdn.com/pics/albums/000/237/566/2360980/(m=e-yaaGqaa)original_2360980.jpg

     http://i0.cdn2b.image.pornhub.phncdn.com/pics/albums/006/764/452/99867672/(m=eiJ_8b)original_99867672.jpg
     http://i0.cdn2b.image.pornhub.phncdn.com/pics/albums/006/764/452/99867672/(m=e-yaaGqaa)original_99867672.jpg

     所以,只要拿到小图的url,并替括号内的字符串即可拿到原图的路径.
     然后下载即可.
     * @return
     * @throws Exception
     */
    @RequestMapping("/downloadPic")
    public @ResponseBody BaseNetBean downloadPic()throws Exception{


        

        return new BaseNetBean(0,"拿到"+0+"张图片",null);

    }

    public static void main(String[] args){
        int[] list = new int[]{
               // 1193001,1036491,453685,9117151,9117151,1798981,1215091,2838911,392921
                //14870692,15003911,14988021,15477592,15154512,15019462,15017532,15029072
                //15011622,15091412,15011212,15201362,15091082,15011162,427021,367466
               // 2732801,745851,333426,2284592,1153641,3060771,2838911,3742802,2628081,2222162,2415081,466571,233205,1241981
                //428950,83036,860271,786111,2762991,434061,1571422,1640991,312316,1677972,561811,2136732,607121,1153671,1997132
                //342283,51617,33646,35987,3260771,2808591,2337142,6710822,2012462,1622012,1767051,460055,8489031,467684
               7093312,3698792,1428971,1434951,228277,1141951,241009,8559531,2814471,1089721,874861,4703181,4677631,1265581,7151042,1434621 //asian
        };

        for(int id : list){
            getPicUrls(id+"");
        }


    }

    private static void getPicUrls(String id) {
         String picUrl = "http://www.pornhub.com/album/show_album";
        Document doc = null;
        try {
            doc = Jsoup.connect(picUrl)
                    .data("id",id)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = doc.title();


        Elements links = doc.select(".photoAlbumListBlock"); // 具有 href 属性的链接
        int size = links.size();
        if(size==0){
            //throw  new CustomException("没有解析到class为photoAlbumListBlock的节点");
        }

        List<String> pics = new ArrayList<String>(size);

        for(Element element: links){
            String style=  element.attr("style");
            //background-image: url('http://i1.cdn2b.image.pornhub.phncdn.com/pics/albums/000/237/566/2360976/(m=eiJ_8b)original_2360976.jpg')
            String pic = style.substring(style.indexOf("('")+2,style.indexOf("')"));
            pic = pic.replace("m=eiJ_8b","m=e-yaaGqaa").trim();
            MyLog.i("解析到一张图片地址:"+pic);
            pics.add(pic);

        }

        download(pics,title);
        MyLog.i("拿到的节点数目:"+size);
        MyLog.i("标题:"+title);
        MyLog.i("下载完成:");


    }

    private static void download(List<String> pics,String title) {

        String root = "F:\\pics\\00asian" + File.separator+title;
        File dir = new File(root);
        if(!dir.exists()){
            dir.mkdirs();
        }

        for( String pic0 : pics){
            String filename = pic0.substring(pic0.indexOf(")")+1);
             String  pic = "";
            try {
                pic = URLEncoder.encode(pic0,"UTF-8");
                pic=pic0.replace("(",URLEncoder.encode("(","UTF-8")).replace(")",URLEncoder.encode(")","UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            final String pic1 = pic;
            final File file = new File(dir,filename);
            MyLog.i("文件路径:"+file.getAbsolutePath());


                    //file.createNewFile();
                    ThreadPoolFactory.getDownLoadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            FileUtil.downloadFile(pic1,file.getAbsolutePath());
                        }
                    });




        }




    }
}
