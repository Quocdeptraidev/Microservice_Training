package com.vn.authservice.service.impl;



import com.vn.authservice.entity.Account;
import com.vn.authservice.exception.ErrorHandler;
import com.vn.authservice.generic.GeneralService;
import com.vn.authservice.generic.IRepository;
import com.vn.authservice.repository.AccountRepository;
import com.vn.authservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GeneralService generalService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(Account account) {
        try {
            // Kiểm tra username đã tồn tại
            if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
                throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Username already exists.");
            }

            // Validate mật khẩu
            generalService.validatePassword(account.getPassword());
            account.setPassword(passwordEncoder.encode(account.getPassword()));


            // Lưu tài khoản (cascade sẽ tự lưu User)
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Iterator<Account> findAll() {
        return null;
    }

    @Override
    public Account findOne(Integer integer) {
        return null;
    }


    @Override
    public IRepository<Account, Integer> getRepository() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElseThrow(() -> new ErrorHandler(HttpStatus.UNAUTHORIZED, "Account not exist"));
    }
}
