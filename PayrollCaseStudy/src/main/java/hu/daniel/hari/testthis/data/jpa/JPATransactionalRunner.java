package hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.interfaceadapters.database.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.usecasesboundary.database.TransactionalRunner;

public class JPATransactionalRunner implements TransactionalRunner {
	@Inject private EntityManager entityManager;

	@Override
	public void executeInTransaction(Runnable runnable) {
		EntityTransaction transaction = createTransaction();
		try {
			runnable.run();
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
	
	private EntityTransaction createTransaction() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		return transaction;
	}



}