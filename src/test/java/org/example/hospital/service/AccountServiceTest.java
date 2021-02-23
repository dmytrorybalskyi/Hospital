package org.example.hospital.service;

import com.mysql.cj.jdbc.exceptions.SQLError;
import org.example.hospital.accessingdatamysql.AccountRepository;
import org.example.hospital.entity.Account;
import org.example.hospital.entity.Roles;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-account-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-account-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    public void addAccount() {
        Account account = new Account("test2", "test2");
        account.setRole(Roles.admin);
        assertTrue(accountService.addAccount(account));
        assertNotNull(account.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addAccountFail() {
        Account account1 = new Account("test3", "test3");
        account1.setRole(Roles.admin);
        assertTrue(accountService.addAccount(account1));
    }

    @Test
    public void loadUserByUsername() {
        Account account1 = accountService.loadUserByUsername("test1");
        assertNotNull(account1.getLogin());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameFail() {
        Account account1 = accountService.loadUserByUsername("test100500");
        assertNotNull(account1.getLogin());
    }

}