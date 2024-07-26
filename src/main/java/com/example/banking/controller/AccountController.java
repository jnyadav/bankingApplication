package com.example.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.dto.AccountDto;
import com.example.banking.service.AccountServiceImpl;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	AccountServiceImpl accountServiceImpl;

	@PostMapping("/save")
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {

		try {
			AccountDto accDto = accountServiceImpl.createAccount(accountDto);
			return new ResponseEntity<>(accDto, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> findAccById(@PathVariable Long id) {

		AccountDto accoudto = accountServiceImpl.getAccountById(id);
		return new ResponseEntity<>(accoudto, HttpStatus.OK);

	}

	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable long id, @RequestBody Map<String, Double> map) {
		Double amount = map.get("amount");
		AccountDto dto = accountServiceImpl.deposit(id, amount);
		return new ResponseEntity<AccountDto>(dto, HttpStatus.OK);
	}

	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withDraw(@PathVariable long id, @RequestBody Map<String, Double> map) {
		Double amount = map.get("amount");
		AccountDto dto = accountServiceImpl.withDraw(id, amount);
		return new ResponseEntity<AccountDto>(dto, HttpStatus.OK);
	}

	@GetMapping("/getallaccount")
	public ResponseEntity<List<AccountDto>> getAllAccounts() {

		List<AccountDto> dto = accountServiceImpl.getAllAccount();
		if (dto == null || dto.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<AccountDto>>(dto, HttpStatus.OK);

	}
	
	@DeleteMapping("/{id}/delete")
	public void deleteById(@PathVariable Long id) {
		
		accountServiceImpl.deleteAccountById(id);
	}
	

}
