package com.example.banking.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.banking.dto.AccountDto;
import com.example.banking.entity.Account;
import com.example.banking.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ObjectMapper mapper;

	@Override
	public AccountDto createAccount(AccountDto accountDto) {

		Account account = mapper.convertValue(accountDto, Account.class);
		Account account1 = accountRepository.save(account);
		AccountDto accdto = mapper.convertValue(account1, AccountDto.class);

		return accdto;
	}

	@Override
	public AccountDto getAccountById(Long id) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("given id account does not exits"));
		return mapper.convertValue(account, AccountDto.class);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("given id account does not exits"));
		
		double total= account.getBalance() + amount;
		account.setBalance(total);
		Account saveAccount = accountRepository.save(account);
		AccountDto dto=mapper.convertValue(saveAccount, AccountDto.class);
		
		return dto;
	}

	@Override
	public AccountDto withDraw(Long id, double amount) {
		
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("given id account does not exits"));
		
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account saveAccount = accountRepository.save(account);
		AccountDto dto=mapper.convertValue(saveAccount, AccountDto.class);
		
		return dto;
		
	}

	@Override
	public List<AccountDto> getAllAccount() {
		List<Account> account= accountRepository.findAll();
		
		if(account!=null) {
			
		return account.stream().filter(s -> s.getAccountHolderName().equalsIgnoreCase("s"))
				.peek(s -> {String accountholdername = s.getAccountHolderName();
				System.out.println("printlin value of filter" + accountholdername);})
				
				.sorted(Comparator.comparing(Account::getAccountHolderName))
				.peek(s-> System.out.println("printing map value" + s))
				.map(a -> mapper.convertValue(a, AccountDto.class))
				.collect(Collectors.toList());
	}
		
	else {
		return Collections.emptyList();
	}

	}

	@Override
	public void deleteAccountById(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Give id doest not exits"));
		
		accountRepository.deleteById(id);
		
	}

}
