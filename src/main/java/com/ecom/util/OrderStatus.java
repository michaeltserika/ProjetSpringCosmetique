package com.ecom.util;

public enum OrderStatus {

	IN_PROGRESS(1, "En cours"), ORDER_RECEIVED(2, "Commande reçue"), PRODUCT_PACKED(3, "Produit emballé"),
	OUT_FOR_DELIVERY(4, "En route pour la livraison"), DELIVERED(5, "Livré"), CANCEL(6, "Annulé"), SUCCESS(7, "Succès");

	private Integer id;
	private String name;

	private OrderStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
