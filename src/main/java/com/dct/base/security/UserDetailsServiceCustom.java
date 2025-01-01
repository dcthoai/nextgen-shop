package com.dct.base.security;

import com.dct.base.entity.Account;
import com.dct.base.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceCustom.class);
    private final AccountRepository accountRepository;

    public UserDetailsServiceCustom(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);

        return new User(account.getUsername(), account.getPassword(), Collections.emptyList());
    }
}
