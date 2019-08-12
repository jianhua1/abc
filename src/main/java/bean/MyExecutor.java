package bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

public class MyExecutor implements Executor {

    MyBatisConfig myBatisConfig=new MyBatisConfig();

    @Override
    public <T> T query(Class<T> clazz, String sql, Object parameter) {
        Connection conn= myBatisConfig.getConnection();
        Object o= null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,parameter.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                try {
                    o = clazz.newInstance();
                    Method[] methods = clazz.getMethods();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int count=metaData.getColumnCount();
                    for(int i=1;i<count;i++){
                        String columnName = metaData.getColumnName(i);
                        String methodName="set"+columnName;
                        for(Method method:methods){
                            if(method.getName().toLowerCase().equals(methodName)){
                                try {
                                    Object object = resultSet.getObject(i);
                                    System.out.println("***********************: "+object);
                                    method.invoke(o,object);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return (T)o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
