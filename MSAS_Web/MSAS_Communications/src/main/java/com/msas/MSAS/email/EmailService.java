package com.msas.MSAS.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	
	@Autowired
    private JavaMailSender javaEmailSender;
 
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        
        this.sendSimpleMessage(message);
    }
    
    public synchronized void sendSimpleMessage(SimpleMailMessage simpleMailMessage){
    	(new Thread(new Runnable() {
			@Override
			public void run() { javaEmailSender.send(simpleMailMessage); }
		})).start();
    }
}
