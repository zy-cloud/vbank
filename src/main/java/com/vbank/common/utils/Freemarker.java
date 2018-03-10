package com.vbank.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 创建人：FH 创建时间：2015年2月8日
 * @version
 */
public class Freemarker {

	/**
	 * 打印到控制台(测试用)
	 *  @param ftlName
	 */
//	public static void print(String ftlName, Map<String,Object> root, String ftlPath) throws Exception{
//		try {
//			Template temp = getTemplate(ftlName, ftlPath);		//通过Template可以将模板文件输出到相应的流
//			temp.process(root, new PrintWriter(System.out));
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 输出到输出到文件
	 * @param ftlName   ftl文件名
	 * @param root		传入的map
	 * @param outFile	输出后的文件全部路径
	 * @param filePath	输出前的文件上部路径
	 */
//	public static void printFile(String ftlName, Map<String,Object> root, String outFile, String filePath, String ftlPath) throws Exception{
//		try {
//			File file = new File(PathUtil.getClasspath() + filePath + outFile);
//			if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
//				file.getParentFile().mkdirs();				//不存在就全部创建
//			}
//			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
//			Template template = getTemplate(ftlName, ftlPath);
//			template.process(root, out);					//模版输出
//			out.flush();
//			out.close();
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 通过文件名加载模版
	 * @param ftlName
	 */
//	public static Template getTemplate(String ftlName, String ftlPath) throws Exception{
//		try {
//			Configuration cfg = new Configuration();  												//通过Freemaker的Configuration读取相应的ftl
//			cfg.setEncoding(Locale.CHINA, "utf-8");
//			cfg.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources()+"/ftl/"+ftlPath));		//设定去哪里读取相应的ftl模板文件
//			Template temp = cfg.getTemplate(ftlName);												//在模板文件目录中找到名称为name的文件
//			return temp;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	/**
	 * 根据模板文件生成文件
	 * @param dataMap		需要填充的数据
	 * @param templatePath	模板路径，不包含文件名
	 * @param templateName	模板名，包含文件名和后缀
	 * @param resultPath	文件保存的地址，全路径，包括文件名及后缀
	 * @return
	 */
	public static File fillFile(Map<?, ?> dataMap, String templatePath ,String templateName ,String resultPath) {
		File resultFile =new File(resultPath);
		try {
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("utf-8");								//设置编码
			configuration.setDirectoryForTemplateLoading(new File(templatePath));	//加载模板文件地址
			Template template = configuration.getTemplate(templateName);			//获取传入的模板信息
			Writer writer = new OutputStreamWriter(new FileOutputStream(resultFile), "utf-8");  //写出文档
			template.process(dataMap, writer);										//操作文档
			writer.close(); 
			//new File(resultFile).delete(); 										//删除文件
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return resultFile;
	}
}
