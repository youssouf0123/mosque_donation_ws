package org.djago;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.djago.configuration.JpaConfiguration;
import org.djago.controller.request.ProductCartItem;
import org.djago.controller.request.ShoppingCart;
import org.djago.model.Order;
import org.djago.repositories.OrderRepository;
import org.djago.service.OrderService;
import org.djago.util.GenerateInvoice;
import org.djago.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = { "org.djago" })
public class MainClass {

	Logger logger = LoggerFactory.getLogger(MainClass.class);

	@Autowired
	Environment env;

	@Autowired
	private GenerateInvoice generateInvoice;

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(MainClass.class, args);
	}

	@Profile("default")
	@Bean
	public ApplicationRunner initializer(OrderRepository repository) throws URISyntaxException, IOException,
			InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

		List<ProductCartItem> items = new ArrayList<ProductCartItem>(
			Arrays.asList(
				new ProductCartItem(1L, "Sugar", BigDecimal.valueOf(19.99), null, BigDecimal.valueOf(1)),
				new ProductCartItem(2L, "Sugar", BigDecimal.valueOf(9.99), null, BigDecimal.valueOf(1)),
				new ProductCartItem(3L, "Sugar", BigDecimal.valueOf(29.99), null, BigDecimal.valueOf(1))
			)
		);

		ShoppingCart cart = new ShoppingCart();
		cart.setTaxRate(BigDecimal.valueOf(5));
		cart.setItems(items);
		
		double totalAmount = cart.getTotal().doubleValue();

		List<Order> orders = orderService.findAll();

		ByteArrayOutputStream invoiceDOCX = generateInvoice.generate(
				cart.getItems(),
				totalAmount,
				orders.get(0),
				"template.docx"
		);
		
		ByteArrayOutputStream invoiceAsPDF = Utils.generatePDF(invoiceDOCX);

		for (Order order : orders) {
			order.setInvoiceDOCX(invoiceDOCX.toByteArray());
			order.setInvoicePDF(invoiceAsPDF.toByteArray());
		}

		return args -> repository.saveAll(orders);
	}

}