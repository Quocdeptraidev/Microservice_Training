package com.vn.authservice.service;



import com.vn.authservice.entity.Account;
import com.vn.authservice.generic.IRepository;
import com.vn.authservice.generic.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account, Integer>, UserDetailsService {
    IRepository<Account, Integer> getRepository();

}
