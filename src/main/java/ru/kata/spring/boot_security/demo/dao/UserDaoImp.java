package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class UserDaoImp implements UserDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
//        logger.info(user.getEmail());
        entityManager.persist(user);
//        String email = user.getEmail();
//        User u = getUserByEmail(email);
//        if (u == null) {
//            try {
//                entityManager.persist(user);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

//    @Override
//    public void saveUsers(List<User> users) {
//        for (User user : users) {
//            entityManager.persist(user);
//        }
//    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByEmail(String email) {
//        String hql = "SELECT u FROM User u left join fetch u.roles WHERE u.email =:email";
//        String hql = "SELECT u FROM User u join fetch u.roles WHERE u.email =:email";
        String hql = "SELECT u FROM User u WHERE u.email =:email";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.warning("getUserByEmail: " + e.getMessage() + " " + email);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

//    @Override
//    public void removeAllUsers() {
//        String sql = "truncate table users";
//        Query query = entityManager.createNativeQuery(sql);
//        query.executeUpdate();
//    }

    @Override
    public long countUsers() {
        String hql = "SELECT count(u) FROM User u";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }
}
