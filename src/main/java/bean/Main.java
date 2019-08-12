package bean;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        MySqlSession mySqlSession=new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(2);
        System.out.println(user.toString());
    }
}
