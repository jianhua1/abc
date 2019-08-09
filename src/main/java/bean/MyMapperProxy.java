package bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;
    private MyBatisConfig myBatisConfig;

    public MyMapperProxy(MySqlSession mySqlSession,MyBatisConfig myBatisConfig){
        this.mySqlSession=mySqlSession;
        this.myBatisConfig=myBatisConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = myBatisConfig.readMapper("UserMapper.xml");
        if(!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }
        List<Function> list = mapperBean.getList();
        if(list!=null && list.size()>0){
            for (Function function : list) {
                String resultType = function.getResultType();
                Class<?> aClass = Class.forName(resultType);
                if(method.getName().equals(function.getFunctionName()) && function.getFunctionName().equals("selectById")){
                    Object o = mySqlSession.selectOne(aClass, function.getSql(), String.valueOf(args[0]));
                    return o;
                }
            }
        }
        return null;
    }
}
