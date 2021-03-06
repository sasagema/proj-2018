package com.example.project2018.server.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.server.dto.PasswordForgotDTO;
import com.example.project2018.server.model.Mail;
import com.example.project2018.server.model.Token;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.PasswordResetTokenRepository;
import com.example.project2018.server.service.EmailService;
import com.example.project2018.server.service.UserService;


@RestController
public class PasswordForgotController {
	

	@Autowired 
	private UserService userService;
    @Autowired 
    private PasswordResetTokenRepository tokenRepository;
    @Autowired 
    private EmailService emailService;
	
  
  
	@RequestMapping(value="/forgot-password",method=RequestMethod.POST)
    public ResponseEntity<User> procesForgotPasswordForm(HttpServletRequest request,@RequestBody PasswordForgotDTO passwordDTO){
    	
    	User user = userService.getByEmail(passwordDTO.getEmail());
    	
    	if(user==null){
    	
    		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    	//System.out.println("Usao u kontroler forgot PAsss");
    	Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);
       
        Mail mail = new Mail();
        mail.setFrom("timalek3@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");
    
        
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String send= url + "/reset-password?token="+ token.getToken();
        String mailString="<h3>Reset password!</h3><br>";
        mailString+="<div>If you want reset password click  <a href ="+send+ ">"
                + "<u>here</u></a>.</div>";

        
        emailService.sendMailForgot(mail,mailString);
    
    	return new ResponseEntity<User>(HttpStatus.OK);
    	
    }
    
    
    
}
