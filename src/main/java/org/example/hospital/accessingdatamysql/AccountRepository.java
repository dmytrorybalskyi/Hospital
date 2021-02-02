package org.example.hospital.accessingdatamysql;

import org.example.hospital.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
  Account findByLogin(String login);

  boolean existsByLogin(String login);
}
