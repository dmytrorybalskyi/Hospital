package org.example.hospital.service;

import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.config.WebSecurityConfig;
import org.example.hospital.entity.Account;

import org.example.hospital.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login);
        if (account == null)
            throw new UsernameNotFoundException(login + " not found");
        Role role = account.getRole();
        List<GrantedAuthority> roles =
                Arrays.asList(new SimpleGrantedAuthority(role.getName()));
        return new User(account.getLogin(), account.getPassword(),roles);
    }



    public boolean addAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }


    public Account findByLogin(String login){
        return accountRepository.findByLogin(login);
    }


    public boolean existsByLogin(String login){
        return accountRepository.existsByLogin(login);
    }


    public Account getCurrentAccount(){
        return accountRepository.findByLogin(webSecurityConfig.getCurrentUser().getUsername());
    }


}
