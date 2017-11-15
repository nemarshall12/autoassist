package com.sommers.demo.restcontrollers;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sommers.demo.beans.ContextOut;
import com.sommers.demo.beans.WebhookRequest;
import com.sommers.demo.beans.WebhookRequestResult;
import com.sommers.demo.beans.WebhookResponse;

@RestController
@RequestMapping(value = "/api/ai")
public class SommersWebhookRestController {

	@RequestMapping("/sommers-webhook")
	public WebhookResponse webhook(HttpServletRequest request, @RequestBody WebhookRequest input) {
		return runActions(input);
	}
	
	private WebhookResponse maintainInterestedBuyer(WebhookRequest input) {
		WebhookResponse response = new WebhookResponse();
		WebhookRequestResult result = input.getResult();
		ContextOut carProfileContext = result.getContext("car-profile");
		Map<String, String> carProfileParams = carProfileContext.getParameters();
		
		String carType = carProfileParams.get("Car-Type");
		String carModel = carProfileParams.get("Car-Model");
		String carMake = carProfileParams.get("Car-Make");
		String minimumPrice = carProfileParams.get("minimum-price");
		String maximumPrice = carProfileParams.get("maximum-price");
		
		if (carType == null || carType.isEmpty()) {
			if (! carProfileParams.containsKey("asked-type")) {
				carProfileParams.put("asked-type", "true");
				ContextOut askedTypeContext = new ContextOut();
				askedTypeContext.setLifespan(1);
				askedTypeContext.setName("asked-type");
				result.getContexts().add(askedTypeContext);
				response.setSpeech("Are you looking for a particular car type? i.e. sedan, coupe, SUV, etc");
				response.setDisplayText("Are you looking for a particular car type? i.e. sedan, coupe, SUV, etc");
				response.setContextOut(result.getContexts());
				return response;
			}
		}
		
		if ((carMake == null || carMake.isEmpty()) && (carModel == null || carModel.isEmpty())) {
			if (! carProfileParams.containsKey("asked-make-model")) {
				carProfileParams.put("asked-make-model", "true");
				ContextOut askedMakeModelContext = new ContextOut();
				askedMakeModelContext.setLifespan(1);
				askedMakeModelContext.setName("asked-make-model");
				result.getContexts().add(askedMakeModelContext);
				response.setSpeech("Do you have a specific make or model in mind?");
				response.setDisplayText("Do you have a specific make or model in mind?");
				response.setContextOut(result.getContexts());
				return response;
			}
		}
		
		if ((minimumPrice == null || minimumPrice.isEmpty()) && (maximumPrice == null || maximumPrice.isEmpty())) {
				if (! carProfileParams.containsKey("asked-price-range")) {
				carProfileParams.put("asked-price-range", "true");
				ContextOut askedTypeContext = new ContextOut();
				askedTypeContext.setLifespan(1);
				askedTypeContext.setName("asked-price-range");
				result.getContexts().add(askedTypeContext);
				response.setSpeech("Do you have a price range you are looking to stay within?");
				response.setDisplayText("Do you have a price range you are looking to stay within?");
				response.setContextOut(result.getContexts());
				return response;
			}
		}
		
		String url = createSommersSearchUrl(carMake, carModel, carType, minimumPrice, maximumPrice);
		response.setContextOut(result.getContexts());
		response.setSpeech("Great!  Here's a direct link to cars that fit what you're looking for: " 
				+ url);
		response.setDisplayText("Great!  Here's a direct link to cars that fit what you're looking for: " 
				+ url);
		
		String messageBody = "You have a new interested buyer who has interacted with Auto Assist."
				+ "\n\n"
				+ "Make: " + carMake
				+ "\nModel: " + carModel
				+ "\nType: " + carType
				+ "\nPrice Range: " + cleanupNumberString(minimumPrice) +  "-" + cleanupNumberString(maximumPrice)
				+ "\nUrl Sent: " + url;
		sendEmail(messageBody, "New Interested Buyer");
		return response;
		
	}
	
	private void sendAppointmentEmail(WebhookRequest input) {
		ContextOut appointment = input.getResult().getContext("Schedule-Appointment-followup");
		String serviceType = appointment.getParameters().get("Car-Service-Type");
		String carModel = appointment.getParameters().get("Car-Model");
		String carMake = appointment.getParameters().get("Car-Make");
		String date = appointment.getParameters().get("date");
		String time = appointment.getParameters().get("time");
		
		String messageBody = "You have a new interested buyer who has interacted with Auto Assist."
				+ "\n\n"
				+ "Service Type : " + serviceType
				+ "\nMake: " + carModel
				+ "\nModel: " + carMake
				+ "\nDate: " + date
				+ "\nTime: " + time;
		sendEmail(messageBody, "New Service Appointment Requested");
	}
	
	private WebhookResponse runActions(WebhookRequest input) {
		switch (input.getResult().getAction()) {
		case "interested-buyer": 
			return maintainInterestedBuyer(input);
		case "schedule-appointment":
			sendAppointmentEmail(input);
			//find best deal based on car profile
			break;
		case "find-price":
			//find best deal based on car profile 
			//return price
			break;
		case "car-price":
			//find price based on car passed in context
			break;
		case "car-miles": 
			//find miles for car passed in context
			break;
			
		}
		return new WebhookResponse(input.getResult().getFulfillment().getSpeech(), input.getResult().getFulfillment().getSpeech()
				, input.getResult().getContexts());
	}
	
	private Long findBestDeal() {
		return 1l;
	}
	
	private String createSommersSearchUrl(String make, String model, String type, String minPrice, String maxPrice) {
		String url = "http://www.sommerscars.com/all-inventory/index.htm";
		String concatChar = "?";
		
		if (make != null && ! make.equals("")) {
			url = url + concatChar + "make=" + make;
			concatChar = "&";
		}
		if (model != null && ! model.equals("")) {
			url = url + concatChar + "model=" + model;
			concatChar = "&";
		}
		if (type != null && ! type.equals("")) {
			url = url + concatChar + "bodyStyle=" + type;
			concatChar = "&";
		}
		if ((minPrice != null && ! minPrice.equals("")) || (maxPrice != null && ! maxPrice.equals(""))) {
			if (minPrice == null || minPrice.equals("")) {
				minPrice = "1";
			} else {
				minPrice = cleanupNumberString(minPrice);
			}
			if (maxPrice == null || maxPrice.equals("")) {
				maxPrice = "99999";
			} else {
				maxPrice = cleanupNumberString(maxPrice);
			}
			url = url + concatChar + "internetPrice=" + minPrice + "-" + maxPrice;
			concatChar = "&";
		}
		
		return url;
	}
	
	private String cleanupNumberString(String number) {
		number = number.trim();
		number = number.replaceAll(",", "");
		number = number.replaceAll(" ", "");
		number = number.replaceAll("k", "000");
		number = number.replaceAll("K", "000");
		if (number.length() <= 2) {
			number = number + "000";
		}
		return number;
	}

	private void sendEmail(String messageContent, String subject) {
		final String username = "nicholasemarshall@gmail.com";
		final String password = "bulldogs12!";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("autoassist@sommerscars.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("scottlorenz50@gmail.com"));
			message.setSubject(subject);
			message.setText(messageContent);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
