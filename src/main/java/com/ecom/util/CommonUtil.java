package com.ecom.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserService userService;

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("mitsetra@gmail.com", "Panier d'achat");
		helper.setTo(reciepentEmail);

		String content = "<p>Bonjour,</p>" + "<p>Vous avez demandé à réinitialiser votre mot de passe.</p>"
				+ "<p>Cliquez sur le lien ci-dessous pour changer votre mot de passe :</p>" + "<p><a href=\"" + url
				+ "\">Changer mon mot de passe</a></p>";
		helper.setSubject("Réinitialisation du mot de passe");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {

		// http://localhost:8080/forgot-password
		String siteUrl = request.getRequestURL().toString();

		return siteUrl.replace(request.getServletPath(), "");
	}

	String msg = null;

	public Boolean sendMailForProductOrder(ProductOrder order, String status) throws Exception {

		msg = "<p>Bonjour [[name]],</p>"
				+ "<p>Merci pour votre commande, statut : <b>[[orderStatus]]</b>.</p>"
				+ "<p><b>Détails du produit :</b></p>"
				+ "<p>Nom : [[productName]]</p>"
				+ "<p>Catégorie : [[category]]</p>"
				+ "<p>Quantité : [[quantity]]</p>"
				+ "<p>Prix : [[price]]</p>"
				+ "<p>Type de paiement : [[paymentType]]</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("mitsetra@gmail.com", "Panier d'achat");
		helper.setTo(order.getOrderAddress().getEmail());

		msg = msg.replace("[[name]]", order.getOrderAddress().getFirstName());
		msg = msg.replace("[[orderStatus]]", status);
		msg = msg.replace("[[productName]]", order.getProduct().getTitle());
		msg = msg.replace("[[category]]", order.getProduct().getCategory());
		msg = msg.replace("[[quantity]]", order.getQuantity().toString());
		msg = msg.replace("[[price]]", order.getPrice().toString());
		msg = msg.replace("[[paymentType]]", order.getPaymentType());

		helper.setSubject("Statut de la commande de produit");
		helper.setText(msg, true);
		mailSender.send(message);
		return true;
	}

	public UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}
}
