package com.lccx.manager.frame;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Configuration
public class SystemLoad implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        /**
         * 加载系统下拉列表xml
         */
        String dict_xml=ConstantClass.DICT_PATH;
        SAXReader reader = new SAXReader();
        //spring boot 加载方式
        Document document = reader.read(ResourceUtils.getFile("classpath:"+dict_xml));
        Element root = document.getRootElement();
        Element foo;
        ConstantClass.DICT_MAP =new HashMap<String, List>();
        for (Iterator i = root.elementIterator("Code"); i.hasNext();) {
            foo = (Element) i.next();
            DictData data=new DictData();
            data.setId(foo.elementText("id"));
            data.setKey(foo.elementText("key"));
            data.setText(foo.elementText("text"));
            List<DictData> list;
            if(ConstantClass.DICT_MAP.containsKey(foo.elementText("key"))){
                //存在key
                list=ConstantClass.DICT_MAP.get(foo.elementText("key"));
                list.add(data);
            }else{
                list=new ArrayList<DictData>();
                list.add(data);
            }
            ConstantClass.DICT_MAP.put(foo.elementText("key"), list);
        }
    }
}
