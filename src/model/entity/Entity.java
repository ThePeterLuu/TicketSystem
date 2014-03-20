package model.entity;

/**
 * This class implements the Entity interface. All objects inherit from Entity, and thus all objects have an ID (to store database/persistent ID.)
 */
public interface Entity {
	public boolean  get_isProxy();
	public void     set_isProxy();

	public int  get_id();
	public void  set_id(int id);
};
