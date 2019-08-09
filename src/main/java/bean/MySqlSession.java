package bean;

import java.lang.reflect.Proxy;

public class MySqlSession {
    MyExecutor myExecutor = new MyExecutor();
    MyBatisConfig myBatisConfig=new MyBatisConfig();

    public <T> T selectOne(Class<T> clazz,String sql, Object para){
        return myExecutor.query(clazz, sql, para);
    }

    public <T> T getMapper(Class<T> tClass){
        return (T)Proxy.newProxyInstance(
                tClass.getClassLoader(),new Class[]{tClass},new MyMapperProxy(this,myBatisConfig)
        );
    }
}
