package bean;

public interface Executor {
    <T> T query(Class<T> clazz, String sql, Object parameter);
}
