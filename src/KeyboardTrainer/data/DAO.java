package KeyboardTrainer.data;


import java.util.List;


public interface DAO<T extends Entity> {
	T create(T newEntity);
	
	T get(int id);
	
	void set(T entity);
	
	void delete(int id);
	
	List<T> getAll();
}