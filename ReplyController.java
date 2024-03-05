package com.beta.replyservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class ReplyController {

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {

		if(message == null || (message.matches("[a-z0-9]*") &&
				message.matches("\\d+-.*") )){
			return new ReplyMessage("Invalid input");
		}

		if(message.contains("-")) {
			String[] parts = message.split("-");
			String rule = parts[0].trim();
			String str = parts[1];

				switch (rule) {
					case "11":
						str = reverseString(str);
						break;
					case "12":
						str = md5Hash(str);
						break;
					default:
						return new ReplyMessage("invalid rule number");

			}
			return new ReplyMessage(str);
		}
		return new ReplyMessage(message);
	}

	private String reverseString(String str){

		return new StringBuilder(str).reverse().toString();
	}

	private String md5Hash(String str){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			StringBuilder hexString = new StringBuilder();

			for(byte b : digest){
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1){
					hexString.append('0');
					hexString.append(hex);
				}

			}
			return  hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}