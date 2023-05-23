package com.spring.yeoreobab;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component	//�� ��� �Ƴ����̼�
@Slf4j
public class MailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	private int authNum;
	
	//���� �߻�
	public int makeRandomNumber() {
		//���� ����: 111111 ~ 999999
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		log.info("������ȣ: " + checkNum);
		return checkNum;
	}
	
	//ȸ�� ���Խ� ����� �̸��� ���
	public String joinEmail(String email) {
		authNum = makeRandomNumber();
		String setFrom = "yeoreobab@gmail.com";	//email-config���� ������ �߽ſ� �̸��� �ּ�
		String toMail = email;	//���Ź��� �̸���(�����ϰ��� �ϴ� ����� �̸���)
		String title = "�����信 �������ּż� �����մϴ�!";	//�̸��� ����
		String content = "Ȩ�������� �湮�� �ּż� �����մϴ�." 
				+ "<br><br>"
				+ "���� ��ȣ�� <strong>" + authNum + "</strong> �Դϴ�." 
				+ "<br>"
				+ "�ش� ���� ��ȣ�� ������ȣ Ȯ�ζ��� ������ �ּ���."; //�̸��Ͽ� ������ ����.
		
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNum);	//������ ���ڿ��� ����
	}
	
	//�̸��� ���� �޼���
	private void mailSend(String setFrom, String toMail, String title, String content) {
		try {
			MimeMessage mmm = mailSender.createMimeMessage();
			//��Ÿ �������� ����� MimeMessageHelper ��ü�� ����.
	        //�������� �Ű������δ� MimeMessage ��ü, boolean, ���� ���ڵ� ����
	        //true �Ű����� �����ϸ� MultiPart ������ �޼��� ������ ����. (÷�� ����)
	        //���� �������� �ʴ´ٸ� �ܼ� �ؽ�Ʈ�� ���.
			
			MimeMessageHelper helper = new MimeMessageHelper(mmm, true, "utf-8");
			
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			//true : html �������� ����, ���� ���ָ� �ܼ� �ؽ�Ʈ�� ����
			
			//���� ����
			mailSender.send(mmm);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}


