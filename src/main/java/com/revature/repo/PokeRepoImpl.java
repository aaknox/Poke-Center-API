package com.revature.repo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.QueryException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.Pokemon;
import com.revature.model.Trainer;

@Repository("pokeRepo")
@Transactional
public class PokeRepoImpl implements PokeRepo {
	
	private static Logger log = Logger.getLogger(PokeRepoImpl.class);

	@Autowired
	private SessionFactory sf;
	
	public PokeRepoImpl() {
		System.out.println("WORKED 2");
	}

	@Override
	public void save(Pokemon pokemon) {
		sf.getCurrentSession().save(pokemon);	
	}

	@Override
	public void save(Trainer trainer) {
		sf.getCurrentSession().save(trainer);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean login(String username, String password) {
		try {
			Criteria crit = sf.getCurrentSession().createCriteria(Trainer.class);
			crit.add(Restrictions.ilike("username", username, MatchMode.EXACT))
				.add(Restrictions.like("password", password, MatchMode.EXACT));
			
			List<Trainer> trainer = crit.list();
			System.out.println(trainer);
			if(trainer.get(0) != null) {
				return true;
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("FAIL 1");
			return false;
		}catch(QueryException e) {
			System.out.println("FAIL 3");
			return false;
		}
		return false;
	}

}