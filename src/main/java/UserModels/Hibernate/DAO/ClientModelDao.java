package UserModels.Hibernate.DAO;

import UserModels.Hibernate.UseridEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import UserModels.Hibernate.HibernateSessionFactory;
import java.util.List;

public class ClientModelDao {
     public UseridEntity findById(int id) {
         return HibernateSessionFactory.getSessionFactory().openSession().get(UseridEntity.class, id);
     }

     public void save(UseridEntity user) {
         Session session = HibernateSessionFactory.getSessionFactory().openSession();
         Transaction tx1 = session.beginTransaction();
         session.save(user);
         tx1.commit();
         session.close();
     }

     public void update(UseridEntity user) {
         Session session = HibernateSessionFactory.getSessionFactory().openSession();
         Transaction tx1 = session.beginTransaction();
         session.update(user);
         tx1.commit();
         session.close();
     }

     public void delete(UseridEntity user) {
         Session session = HibernateSessionFactory.getSessionFactory().openSession();
         Transaction tx1 = session.beginTransaction();
         session.delete(user);
         tx1.commit();
         session.close();
     }

     public List<UseridEntity> findAll() {
         List<UseridEntity> users = (List<UseridEntity>)  HibernateSessionFactory.getSessionFactory().openSession().createQuery("From UseridEntity").list();
         return users;
     }
}

