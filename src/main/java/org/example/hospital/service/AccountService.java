package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService  implements UserDetailsService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account loadUserByUsername(String login) throws UsernameNotFoundException {
        return accountRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login + "not found"));
    }


    public boolean addAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }


}
