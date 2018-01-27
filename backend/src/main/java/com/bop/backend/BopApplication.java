package com.bop.backend;

import com.bop.backend.config.JwtFilter;
import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Loan;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BopApplication {
//	@Bean
//	public FilterRegistrationBean jwtFilter(){
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new JwtFilter());
//		registrationBean.addUrlPatterns("/rest/*");
//
//		return registrationBean;
//	}

	public static void main(String[] args) {

		ApplicationContext lol = SpringApplication.run(BopApplication.class, args);
//		checkPotCreation(lol);
//		checkPotSave(lol);
//
//		System.out.println(user.getFirstName());
//		service.investMoney(1,1,200);
//		UserPotService service = lol.getAutowireCapableBeanFactory().getBean(UserPotService.class);
//
//		UserService userService = lol.getAutowireCapableBeanFactory().getBean(UserService.class);
//		User user = userService.findByTelephone(999);
//		Iterable<UserPot> userPots = service.findAll();
//		for (UserPot userPot: userPots)
//		{
//			System.out.println(userPot.getUser().getTelephone());
//		}
//		service.investMoney(1,1,155);
//		System.out.println(service.findByUserIdPotId(1,1).getInvested());
	}
	private static void checkPotCreation(ApplicationContext lol){
		UserService userService = lol.getAutowireCapableBeanFactory().getBean(UserService.class);
		User user = userService.findByTelephone(999);
		PotService potService = lol.getAutowireCapableBeanFactory().getBean(PotService.class);
		Pot pot = potService.findByPotId(1);
		Loan loan = new Loan();
//		loan.setLoanId(2);
		loan.setPot(pot);
		loan.setUser(user);
		loan.setMoney(1000f);
		LoanService service = lol.getAutowireCapableBeanFactory().getBean(LoanService.class);
//		service.save(loan);
	}

	private static void checkPotSave(ApplicationContext lol)
	{

		LoanService service = lol.getAutowireCapableBeanFactory().getBean(LoanService.class);
//		service.save(new Loan(), new UserPotId());
	}

	//private static
}
