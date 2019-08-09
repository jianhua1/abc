package bean;

import java.io.File;
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
             resultSet.next();
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
                                method.invoke(o,resultSet.getObject(i));
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                 /*   user.setUserId(resultSet.getInt("userid"));
                    user.setUserName(resultSet.getString("username"));
                    user.setUserAge(resultSet.getString("userage"));*/
                }

                System.out.println("ggggggggggggggggg");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return (T)o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
