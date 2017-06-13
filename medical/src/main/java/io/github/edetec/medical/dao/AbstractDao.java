package io.github.edetec.medical.dao;

import org.hibernate.Session;

import io.github.edetec.medical.util.JpaUtil;

abstract class AbstractDao {
	
	public Session openSession(){
		return JpaUtil.getSession();
	}

}
