package com.example.Farming_App;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Role;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FarmingAppApplication implements CommandLineRunner {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FarmingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Account user=accountRepository.findByRoleName("USER");
//		if(user==null){
//			Account account=new Account();
//			account.setUsername("khanh");
//			account.setFirstname("nguyen");
//			account.setLastname("duy khanh");
//			account.setPassword(new BCryptPasswordEncoder().encode("123"));
//			account.setRole(roleRepository.findByName("USER"));
//			accountRepository.save(account);
//		}
	}
}
