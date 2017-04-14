package com.sunvsoft.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 解析生成xml
 * 
 * @author liuhy
 * 2016年10月14日 下午7:04:25
 */
public class XmlUtil {

	private static List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private static String lt = "<";
	private static String ltEnd = "</";
	private static String rt = ">";
	private static String quotes = "\"";
	private static String equal = "=";
	private static String blank = " ";
    
	/**
	 * 拼接xml
	 * @param xml	xml实体
	 * @return		拼接后的xml字符串
	 * 
	 * @see 调用示例：<br/>
	 * 		XmlModel xml = new XmlModel("node");<br/>
     *     	<br/>
	 *		XmlModel xml_1 = new XmlModel("node1");<br/>
	 *	    xml_1.addProperty("name", "zhangsan");<br/>
	 *		xml_1.addProperty("password", "123456");<br/>
	 *		xml_1.setNodeText("11111");<br/>
	 *      xml.addChild(xml_1);<br/>
	 *      <br/>
	 *      XmlModel xml_2 = new XmlModel("node2");<br/>
	 *      xml_2.addProperty("name", "lisi");<br/>
	 *      xml_2.addProperty("password", "3333");<br/>
	 *      xml_2.setNodeText("22222");<br/>
	 *      xml.addChild(xml_2);<br/>
	 *      System.out.println(XmlUtils.changeToXml(xml));<br/>
	 */
    public static String changeToXml(XmlModel xml){
        StringBuffer result = new StringBuffer();
        //元素开始
        result.append(lt).append(xml.getName());
        //判断是否有属性
        if(xml.getProperty() != null && xml.getProperty().size() > 0 ){
            Iterator<String> iter = xml.getProperty().keySet().iterator();
            while (iter.hasNext()) {
                String key = String.valueOf(iter.next());
                String value = xml.getProperty().get(key);
                result.append(blank).append(key).append(equal)
                	.append(quotes).append(value).append(quotes).append(blank);
            }
        }
        result.append(rt);//结束标记
        /*
         * 判断是否是叶子节点
         * 是叶子节点，添加节点内容
         * 不是叶子节点，循环添加子节点
         */
        if(xml.isIsleaf()){
            result.append(xml.getNodeText());
        }else{
            for(XmlModel xmlModel :xml.getChild()){
                result.append(changeToXml(xmlModel));
            }
        }
        //元素结束
        result.append(ltEnd).append(xml.getName()).append(rt);
        return result.toString();
    }

	/**
	 * 解析xml
	 * 		file和xmlData不能同时为null
	 * @param file		真实.xml文件
	 * @param xmlData	xml字符串
	 * @return			List<Map<String, String>>
	 */
	public static List<Map<String, String>> readXML(File file, String xmlData) {
		List<Map<String, String>> xmlList = new ArrayList<Map<String, String>>();
		// 获得DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// 获得DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// --获得文档对象--
			Document doc = null;
			if (file != null && StringUtils.isBlank(xmlData)){
				doc = builder.parse(file);
			} else if(file == null && StringUtils.isNotBlank(xmlData)){
				doc = builder.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("utf-8"))));
			} else{
				return null;
			}
			// 获得根元素
			Element element = doc.getDocumentElement();
			// 用方法遍历递归打印根元素下面所有的ElementNode(包括属性,TextNode非空的值),用空格分层次显示.
			if(list.size() > 0)
				list.clear();
			xmlList = listAllChildNodes(element, 0);// 参数0表示设定根节点层次为0,它的前面不打印空格.
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlList;
	}

	/**
	 * 递归遍历所有的ElementNode(包括节点的属性和文本节点的有效内容)
	 */
	private static List<Map<String, String>> listAllChildNodes(Node node, int level) {
		Map<String, String> map = new HashMap<String, String>();
		// 只处理ElementNode类型的节点,感觉这种类型的节点(还有有效的文本节点)才是真正有用的数据,其他注释节点,空白节点等都用不上.
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (node.hasAttributes()) {
				map.put("nodeName", node.getNodeName());
				NamedNodeMap nnmap = node.getAttributes();
				for (int i = 0; i < nnmap.getLength(); i++) {
					map.put(nnmap.item(i).getNodeName(), nnmap.item(i).getNodeValue());// 不是最后一个属性的话属性之间要留空隙
				}
			}
			// 【打印 - 子节点】该ElementNode包含子节点时候的处理
			if (node.hasChildNodes()) {
				level++;// 有下一级子节点,层次加1,新的层次表示的是这个子节点的层次(递归调用时传给了它)
				// 获得所有的子节点列表
				NodeList nodelist = node.getChildNodes();
				// 循环遍历取到所有的子节点
				map.put("nodeName", node.getNodeName());
				for (int i = 0; i < nodelist.getLength(); i++) {
					// 【有效文本子节点】子节点为TextNode类型,并且包含的文本内容有效
					if (nodelist.item(i).getNodeType() == Node.TEXT_NODE
							&& (!nodelist.item(i).getTextContent().matches("\\s+"))) {// 用正则选取内容包含非空格的有效字符的文本节点
						map.put("text", nodelist.item(i).getTextContent());// 在开始标签后面添加文本内容
						// 【ElementNode子节点】子节点是正常的ElementNode的处理
					} else if (nodelist.item(i).getNodeType() == Node.ELEMENT_NODE) {
						// 递归调用方法 - 以遍历该节点下面所有的子节点
						listAllChildNodes(nodelist.item(i), level);// level表示该节点处于第几个层次(相应空格)
					}
				}
				level--;// 遍历完所有的子节点,层次变量随子节点的层数,依次递减,回归到该节点本身的层次
			}
		}
		if (map.size() > 0)
			list.add(map);
		return list;
	}
}