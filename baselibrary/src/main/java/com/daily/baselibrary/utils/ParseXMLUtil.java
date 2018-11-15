package com.daily.baselibrary.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvin on 2016/2/2 16:47.
 * XML解析模板
 */
@SuppressWarnings("all")
public class ParseXMLUtil {
    /**
     * 我们想要解析xml文件，我们可以先将xml以输入流的方式读取出来，然后再处理，这个输入流的xml可以通过类加载器来读取，由于xml文件是放在src文件下的，
     * 所以方法如下：InputStream xml = this.getClass().getClassLoader().getResourceAsStream("test.xml");
     */
    public static Map<Object, Object> getFaces(InputStream xml) throws Exception {
        Map<Object, Object> faces = null;
        XmlPullParser pullParser = XmlPullParserFactory.newInstance()
                .newPullParser();
        //XmlPullParser pullParser = Xml.newPullParser();// 或者通过这种方法得到pull解析器
        pullParser.setInput(xml, "UTF-8");// 为pull解析器设置要解析的xml数据
        int event = pullParser.getEventType();
        String key = "";
        String value = "";
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    faces = new HashMap<>();
                    break;
                case XmlPullParser.START_TAG://解析到开始节点时会触发该事件，例如<persons><person><name><age>节点
                    break;
                case XmlPullParser.END_TAG://解析到结束节点时会触发该事件，例如</persons></person></name></age>节点</span>
                    //当节点里边没有多余的内容如这里的tag节点，则不会调用该事件
                    break;
            }
            event = pullParser.next();
        }
        return faces;
    }
}
