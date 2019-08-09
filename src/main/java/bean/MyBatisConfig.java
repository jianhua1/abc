package bean;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBatisConfig {
    String username;
    String password;
    String url;
    String driverClassName;

    public Connection getConnection(){
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("mybatis-config.xml");
        Connection conn=null;
        try {
            Document doc = saxReader.read(resourceAsStream);
            Element rootElement = doc.getRootElement();
            if(!rootElement.getName().equals("database")){
                throw new RuntimeException("读取到错误的配置信息");
            }
            List<Element> property = rootElement.elements("property");
            for (Element o : property) {
                String text=o.getText();
                String value = o.attributeValue("name");
                if("driverClassName".equals(value)){
                    driverClassName=text;
                }else if("username".equals(value)){
                    username=text;
                }else if("password".equals(value)){
                    password=text;
                }else if ("url".equals(value)){
                    url=text;
                }else{
                    throw new RuntimeException("读取配置文件出错");
                }
            }
            try {
                Class.forName(driverClassName);
                try {
                     conn = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public MapperBean readMapper(String path){
        MapperBean mapperBean = new MapperBean();

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(path);
        ArrayList<Function> list = new ArrayList<>();
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                Function function = new Function();
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String parameterType = element.attributeValue("parameterType");
                String text = element.getText();
                function.setSql(text);
                function.setFunctionName(id);
                function.setParameterType(parameterType);
                function.setResultType(resultType);
                list.add(function);
            }
            mapperBean.setList(list);
            mapperBean.setInterfaceName(namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapperBean;
    }

    public static void main(String[] args) {
        MyBatisConfig myBatisConfig = new MyBatisConfig();
        Connection connection = myBatisConfig.getConnection();
        MapperBean mapperBean = myBatisConfig.readMapper("UserMapper.xml");
    }
}
