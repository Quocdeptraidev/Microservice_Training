package com.vn.authservice.repository;



import com.vn.authservice.entity.Account;
import com.vn.authservice.generic.IRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
}
