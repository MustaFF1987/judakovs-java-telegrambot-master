package ebe.P_Judakov.s.JAVABOT.repository.mysql;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class MySqlUserRepository implements UserRepository {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<JpaUser> findById(int userId) {
        JpaUser user = entityManager.find(JpaUser.class, userId);
        return Optional.ofNullable(user);
    }


    @Override
    @Transactional
    public void deleteById(int userId) {
        JpaUser user = entityManager.find(JpaUser.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public JpaUser findByChatId(Long chatId) {
        TypedQuery<JpaUser> query = entityManager.createQuery(
                "SELECT u FROM JpaUser u WHERE u.chatId = :chatId", JpaUser.class);

        query.setParameter("chatId", chatId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Если не найден пользователь с указанным chatId
        }
    }

    @Override
    @Transactional
    public JpaUser save(JpaUser user) {
        if (user.getId() == 0) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends JpaUser> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends JpaUser> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<JpaUser> entities) {
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public JpaUser getOne(Long aLong) {
        return null;
    }

    @Override
    public JpaUser getById(Long aLong) {
        return null;
    }

    @Override
    public JpaUser getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends JpaUser> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends JpaUser> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends JpaUser> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends JpaUser> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends JpaUser> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends JpaUser> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends JpaUser, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends JpaUser> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<JpaUser> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<JpaUser> findAll() {
        return null;
    }

    @Override
    public List<JpaUser> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(JpaUser entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends JpaUser> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<JpaUser> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<JpaUser> findAll(Pageable pageable) {
        return null;
    }
}

