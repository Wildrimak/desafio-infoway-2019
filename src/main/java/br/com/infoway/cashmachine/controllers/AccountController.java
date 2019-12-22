package br.com.infoway.cashmachine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.infoway.cashmachine.dto.ActionDto;
import br.com.infoway.cashmachine.models.Account;
import br.com.infoway.cashmachine.models.Movement;
import br.com.infoway.cashmachine.services.AccountService;

@RestController
@RequestMapping("accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping
	public List<Account> getAccounts() {
		return this.accountService.getAccounts();
	}

	@PostMapping("{idAccount}/withdrawal")
	public ResponseEntity<Account> postWithdrawal(@RequestBody ActionDto actionDto, @PathVariable Long idAccount) {

		Account account = accountService.getAccountById(idAccount);
		account.withdrawal(actionDto.getValue());
		account = accountService.save(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(account);

	}

	@PostMapping("{idAccount}/deposit")
	public ResponseEntity<Account> postDeposit(@RequestBody ActionDto actionDto, @PathVariable Long idAccount) {

		Account account = accountService.getAccountById(idAccount);
		account.deposit(actionDto.getValue());

		account = accountService.save(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(account);

	}

	@PostMapping("{myAccount}/transfer")
	public ResponseEntity<Account> postTranfer(@RequestBody ActionDto actionDto, @PathVariable Long myAccount) {

		Account account = accountService.getAccountById(myAccount);
		Account destination = accountService.getAccountById(actionDto.getIdAccount());
		account.transfer(actionDto.getValue(), destination);
		account = accountService.save(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(account);

	}

	@GetMapping("{idAccount}/movements")
	public List<Movement> getMovements(@PathVariable Long idAccount) {

		List<Movement> movements = accountService.getMovements(idAccount);

		return movements;

	}
}
