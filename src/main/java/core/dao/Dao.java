package core.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, I> {
    T add(T value);
    
    Optional<T> get(I id);
    
    List<T> getAll();
    
    T update(T value);
    
    boolean delete(I id);
}
