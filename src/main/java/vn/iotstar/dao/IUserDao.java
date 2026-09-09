package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.User;

public interface IUserDao {

    void insert(User user);

    void update(User user);

    void delete(int id) throws Exception;

    User findById(int id);

    User findByEmail(String email);

    List<User> findAll();

    List<User> searchByKeyword(String keyword);

    int count();
}
