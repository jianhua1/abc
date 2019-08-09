package bean;

import java.util.List;

public class MapperBean {
    private String interfaceName;
    private List<Function> list;

    public MapperBean(){

    }

    public MapperBean(String interfaceName, List<Function> list) {
        this.interfaceName = interfaceName;
        this.list = list;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "interfaceName:"+interfaceName+" list:"+list.toString();
    }
}
