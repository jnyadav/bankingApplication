package com.example.banking.service;

import java.util.List;

import com.example.banking.dto.AccountDto;

public interface AccountService {
	
	AccountDto createAccount(AccountDto accountDto);
	
	AccountDto getAccountById(Long id);
	
	AccountDto deposit(Long id, double amount);
	
	AccountDto withDraw(Long id, double amount);
	
	List<AccountDto> getAllAccount();
	
	void deleteAccountById(Long id);

}
