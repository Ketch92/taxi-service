package core.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T add(T value);
    
    Optional<T> get(Long id);
    
    List<T> getAll();
    
    T update(T value);
    
    boolean delete(Long id);
    
    boolean delete(T value);
}
