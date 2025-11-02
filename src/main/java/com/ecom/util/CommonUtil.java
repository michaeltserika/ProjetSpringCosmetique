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

		helper.setFrom("mitsetra@gmail.com", "ShopFandresena");
		helper.setTo(reciepentEmail);

		String content = "<!DOCTYPE html>"
				+ "<html lang='fr'>"
				+ "<head>"
				+ "<meta charset='UTF-8'>"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
				+ "<title>Réinitialisation du mot de passe - ShopFandresena</title>"
				+ "<style>"
				+ "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }"
				+ ".container { max-width: 600px; margin: 20px auto; background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }"
				+ ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px 20px; text-align: center; }"
				+ ".header h1 { margin: 0; font-size: 24px; font-weight: 300; }"
				+ ".content { padding: 30px 20px; }"
				+ ".btn { display: inline-block; padding: 15px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 25px; font-weight: bold; margin: 20px 0; }"
				+ ".btn:hover { background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%); }"
				+ ".footer { background: #343a40; color: white; padding: 20px; text-align: center; }"
				+ ".footer p { margin: 5px 0; }"
				+ ".warning { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 8px; padding: 15px; margin: 20px 0; }"
				+ ".warning strong { color: #856404; }"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div class='container'>"
				+ "<div class='header'>"
				+ "<h1>🔐 ShopFandresena</h1>"
				+ "<p>Réinitialisation de mot de passe</p>"
				+ "</div>"
				+ "<div class='content'>"
				+ "<p>Bonjour,</p>"
				+ "<p>Vous avez demandé à réinitialiser votre mot de passe.</p>"
				+ "<div class='warning'>"
				+ "<strong>⚠️ Sécurité :</strong> Ce lien est valide pendant une durée limitée. Ne partagez pas ce lien avec qui que ce soit."
				+ "</div>"
				+ "<div style='text-align: center;'>"
				+ "<a href='" + url + "' class='btn'>🔑 Changer mon mot de passe</a>"
				+ "</div>"
				+ "<p>Si le bouton ne fonctionne pas, copiez et collez ce lien dans votre navigateur :</p>"
				+ "<p style='word-break: break-all; background: #f8f9fa; padding: 10px; border-radius: 5px; font-family: monospace;'>" + url + "</p>"
				+ "<p>Si vous n'avez pas demandé cette réinitialisation, ignorez simplement cet email.</p>"
				+ "</div>"
				+ "<div class='footer'>"
				+ "<p><strong>ShopFandresena</strong></p>"
				+ "<p>✨ Votre beauté, notre passion ✨</p>"
				+ "<p>&copy; 2024 ShopFandresena. Tous droits réservés.</p>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
		helper.setSubject("Réinitialisation du mot de passe - ShopFandresena");
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

		msg = "<!DOCTYPE html>"
				+ "<html lang='fr'>"
				+ "<head>"
				+ "<meta charset='UTF-8'>"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
				+ "<title>Confirmation de commande - ShopFandresena</title>"
				+ "<style>"
				+ "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }"
				+ ".container { max-width: 600px; margin: 20px auto; background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }"
				+ ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px 20px; text-align: center; }"
				+ ".header h1 { margin: 0; font-size: 28px; font-weight: 300; }"
				+ ".content { padding: 30px 20px; }"
				+ ".status-badge { display: inline-block; padding: 8px 16px; border-radius: 20px; font-weight: bold; text-transform: uppercase; font-size: 12px; }"
				+ ".status-success { background: #d4edda; color: #155724; }"
				+ ".status-processing { background: #fff3cd; color: #856404; }"
				+ ".status-delivered { background: #d1ecf1; color: #0c5460; }"
				+ ".status-cancelled { background: #f8d7da; color: #721c24; }"
				+ ".product-card { background: #f8f9fa; border-radius: 10px; padding: 20px; margin: 20px 0; border-left: 4px solid #667eea; }"
				+ ".product-detail { margin: 10px 0; }"
				+ ".product-detail strong { color: #495057; }"
				+ ".price-highlight { font-size: 24px; font-weight: bold; color: #28a745; margin: 15px 0; }"
				+ ".footer { background: #343a40; color: white; padding: 20px; text-align: center; }"
				+ ".footer p { margin: 5px 0; }"
				+ ".btn { display: inline-block; padding: 12px 24px; background: #667eea; color: white; text-decoration: none; border-radius: 25px; margin: 10px 5px; }"
				+ ".btn:hover { background: #5a67d8; }"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div class='container'>"
				+ "<div class='header'>"
				+ "<h1>🛍️ ShopFandresena</h1>"
				+ "<p>Confirmation de votre commande</p>"
				+ "</div>"
				+ "<div class='content'>"
				+ "<p>Bonjour <strong>[[name]]</strong>,</p>"
				+ "<p>Nous vous remercions pour votre commande ! Voici le statut actuel :</p>"
				+ "<div class='status-badge [[statusClass]]'>[[orderStatus]]</div>"
				+ "<div class='product-card'>"
				+ "<h3 style='margin-top: 0; color: #667eea;'>📦 Détails de votre commande</h3>"
				+ "<div class='product-detail'><strong>Produit :</strong> [[productName]]</div>"
				+ "<div class='product-detail'><strong>Catégorie :</strong> [[category]]</div>"
				+ "<div class='product-detail'><strong>Quantité :</strong> [[quantity]]</div>"
				+ "<div class='product-detail'><strong>Type de paiement :</strong> [[paymentType]]</div>"
				+ "<div class='price-highlight'>Prix total : €[[price]]</div>"
				+ "</div>"
				+ "<p>Si vous avez des questions concernant votre commande, n'hésitez pas à nous contacter.</p>"
				+ "<div style='text-align: center; margin: 30px 0;'>"
				+ "<a href='#' class='btn'>📞 Nous contacter</a>"
				+ "<a href='#' class='btn'>📦 Suivre ma commande</a>"
				+ "</div>"
				+ "</div>"
				+ "<div class='footer'>"
				+ "<p><strong>ShopFandresena</strong></p>"
				+ "<p>✨ Votre beauté, notre passion ✨</p>"
				+ "<p>&copy; 2024 ShopFandresena. Tous droits réservés.</p>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("mitsetra@gmail.com", "Panier d'achat");
		helper.setTo(order.getOrderAddress().getEmail());

		String statusClass = switch (status.toLowerCase()) {
			case "livré", "delivered" -> "status-delivered";
			case "en cours", "processing" -> "status-processing";
			case "annulé", "cancelled" -> "status-cancelled";
			default -> "status-success";
		};

		msg = msg.replace("[[name]]", order.getOrderAddress().getFirstName());
		msg = msg.replace("[[orderStatus]]", status);
		msg = msg.replace("[[statusClass]]", statusClass);
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
