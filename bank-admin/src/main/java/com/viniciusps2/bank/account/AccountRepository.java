package com.viniciusps2.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findOneByAgencyAndNumber(Integer agency, Integer number);
}
