package org.djago.controller;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.djago.controller.request.OrderRequestObject;
import org.djago.controller.request.ProductCartItem;
import org.djago.controller.request.ShoppingCart;
import org.djago.model.Order;
import org.djago.service.OrderService;
import org.djago.service.ProductService;
import org.djago.service.ReturnService;
import org.djago.util.GenerateInvoice;
import org.djago.util.PrintAndConfirm;
import org.djago.util.Printing;
import org.djago.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private GenerateInvoice generateInvoice;

	@GetMapping("/allConfirmedOrders")
	public ResponseEntity<List<Order>> getAllPendingOrders() {
		List<Order> orderList = orderService.findConfirmedOrders();
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}

	@GetMapping("/allPendingOrders")
	public ResponseEntity<List<Order>> getAllConfirmedOrders() {
		List<Order> orderList = orderService.findPendingOrders();
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
		Order order = orderService.findOrderByOrderID(id);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
	
	@GetMapping("/find/confirmed/{id}")
	public ResponseEntity<Order> getConfirmedOrderById(@PathVariable("id") Long id) {
		Order order = orderService.findConfirmedOrderById(id);
		logger.info(order.toString());
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@GetMapping("/edit/{id}/{status}")
	public ResponseEntity<OrderRequestObject> edit(
			@PathVariable("id") Long id,
			@PathVariable("status") String status
	) 
	{
		Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
		
		logger.info(orderStatus.toString());
		
		
		Order order = 
				(orderStatus.equals(Order.OrderStatus.PENDING))? 
						orderService.findOrderByOrderID(id): orderService.findConfirmedOrderById(id);
		
//		Order order = orderService.findOrderByOrderID(id);

		OrderRequestObject orderRequest = new OrderRequestObject();

		orderRequest.setId(order.getId());
		orderRequest.setFirstName(order.getCustomerFirstName());
		orderRequest.setLastName(order.getCustomerLastName());
		orderRequest.setPhone(order.getCustomerPhoneNumber());
		orderRequest.setAddress(order.getCustomerAddress());

		List<ProductCartItem> cartItems = new ArrayList<>();

		ByteArrayInputStream bs = new ByteArrayInputStream(order.getInvoiceDOCX());

		XWPFDocument document = null;
		try {
			document = new XWPFDocument(bs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		XWPFTable table = document.getTables().get(0);

		for (int i = 1; i < table.getRows().size(); i++) {

			XWPFTableRow row = table.getRow(i);

			Integer quantity = Integer.valueOf(row.getCell(1).getText());
			Double unitPrice = Double.valueOf(row.getCell(2).getText());
			Double totalPrice = Double.valueOf(row.getCell(3).getText());
			String productName = row.getCell(0).getText();

			cartItems.add(new ProductCartItem(Long.valueOf(i), productName, BigDecimal.valueOf(unitPrice), null,
					BigDecimal.valueOf(quantity)));
		}

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setTaxRate(BigDecimal.ZERO);
		shoppingCart.setShipping(BigDecimal.ZERO);
		shoppingCart.setItems(cartItems);

		orderRequest.setCart(shoppingCart);

		logger.info(cartItems.toString());

		return new ResponseEntity<OrderRequestObject>(orderRequest, HttpStatus.OK);
	}

	@GetMapping(value = "/downloadPDF/{id}", produces = "application/pdf")
	public ResponseEntity<byte[]> getPDF(@PathVariable("id") Long id) {

		Order order = orderService.findOrderByOrderID(id);

		byte[] contents = order.getInvoicePDF();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		String filename = "test.pdf";

		headers.setContentDispositionFormData(filename, filename);

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
	}

	@PostMapping("/add")
	public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderRequestObject order)
			throws InvalidFormatException, IOException, URISyntaxException {
		logger.info(order.toString());

		Order orderToSave = new Order(order);

		Order savedOrder = orderService.addOrder(orderToSave);

		updateShoppingCartInfo(order, orderToSave, savedOrder);

		savedOrder = orderService.updateOrder(savedOrder);

		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	private void updateShoppingCartInfo(OrderRequestObject order, Order orderToSave, Order savedOrder)
			throws InvalidFormatException, IOException, URISyntaxException {
		double totalAmount = order.getCart().getTotal().doubleValue();

		orderToSave.setAmount(totalAmount);

		List<ProductCartItem> cartItems = order.getCart().getItems();

		ByteArrayOutputStream invoiceDOCX = generateInvoice.generate(cartItems, totalAmount, savedOrder,
				"template.docx");
		ByteArrayOutputStream invoiceAsPDF = Utils.generatePDF(invoiceDOCX);

		savedOrder.setInvoiceDOCX(invoiceDOCX.toByteArray());
		savedOrder.setInvoicePDF(invoiceAsPDF.toByteArray());
	}

	@GetMapping("/printOrder/{id}")
	public ResponseEntity<?> printOrderById(@PathVariable("id") Long id) {
		Order order = orderService.findOrderByOrderID(id);
		PrintAndConfirm<?> printConf = new PrintAndConfirm<Order>(order, orderService, productService);
		logger.info("Youssouf order test : " + order);
		return new ResponseEntity<>(printConf.myProduct, HttpStatus.OK);
	}

	@GetMapping("/print/{id}")
	public ResponseEntity<?> printOrder(@PathVariable("id") Long id) {
		Order order = orderService.findOrderByOrderID(id);
		ByteArrayInputStream bs = new ByteArrayInputStream(order.getInvoicePDF());
		PDDocument document = null;
		try {
			document = PDDocument.load(bs);
			Printing.print(document);
		} catch (IOException | PrinterException e) {
			logger.info("No printer were found");
			// If order is null on frontend, which mean we couldnt print it.
			order = null;
			e.printStackTrace();
		} finally {
			try {
				document.close();
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/findOrdersByDate/{date}")
	public ResponseEntity<List<Order>> findOrderByDate(@PathVariable("date") String date) {
		logger.info("Youssouf diarraa hhh: " + date);
		String[] orderDate = date.split("-");
		LocalDate d = LocalDate.of(Integer.parseInt(orderDate[0]), Integer.parseInt(orderDate[1]),
				Integer.parseInt(orderDate[2]));

		List<Order> order = orderService.findOrdersByDate(d);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/findOrdersByDateRange/{from}/{to}")
	public ResponseEntity<List<Order>> findOrderByDateRange(@PathVariable("from") String from,
			@PathVariable("to") String to) {
		logger.info("Youssouf diarraa yes yes from: " + from);
		logger.info("Youssouf diarraa yes yes to: " + to);
		String[] fromOrderDate = from.split("-");
		String[] toOrderDate = to.split("-");
		LocalDate fromDate = LocalDate.of(Integer.parseInt(fromOrderDate[0]), Integer.parseInt(fromOrderDate[1]),
				Integer.parseInt(fromOrderDate[2]));
		LocalDate toDate = LocalDate.of(Integer.parseInt(toOrderDate[0]), Integer.parseInt(toOrderDate[1]),
				Integer.parseInt(toOrderDate[2]));
		logger.info("Youssouf diarraa from : " + fromDate);
		logger.info("Youssouf diarraa to : " + toDate);
		List<Order> orders = orderService.findOrdersByDateRange(fromDate, toDate);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody OrderRequestObject order)
			throws InvalidFormatException, IOException, URISyntaxException {

		logger.info(order.toString());

		Order orderToSave = new Order(order);

		updateShoppingCartInfo(order, orderToSave, orderToSave);

		orderToSave = orderService.updateOrder(orderToSave);

		return new ResponseEntity<>(orderToSave, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
		orderService.deleteOrderById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

//https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration

//public class RestPreconditions {
//    public static <T> T checkFound(T resource) {
//        if (resource == null) {
//            throw new MyResourceNotFoundException();
//        }
//        return resource;
//    }
//}