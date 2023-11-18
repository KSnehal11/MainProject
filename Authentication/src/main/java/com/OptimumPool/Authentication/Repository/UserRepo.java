package com.OptimumPool.Authentication.Repository;

import com.OptimumPool.Authentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByNameAndPassword(String name, String password);

    public User findByName(String name);
}
