package KeyboardTrainer.data;


public interface Repository<T extends Entity> {
	void create(T newEntity);
	
	T get(int id);
	
	void set(T entity);
	
	void delete(int id);
}
