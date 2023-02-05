package com.visulabs.bookstore.service;

import com.visulabs.bookstore.entity.*;
import com.visulabs.bookstore.exception.CartNotFoundException;
import com.visulabs.bookstore.exception.OrderNotCreatedException;
import com.visulabs.bookstore.service.kafka.Event;
import com.visulabs.bookstore.service.kafka.KafkaMessageProducerThread;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Controller
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BookStoreCartService cartService;

	@Autowired
	private BookStoreUserRepository userRepo;

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private KafkaMessageProducerThread producerThread;

	public Long save(OrderDTO vO) {
		Order bean = new Order();
		BeanUtils.copyProperties(vO, bean);
		bean = orderRepository.save(bean);
		return bean.getOrderId();
	}

	public void delete(Long id) {
		orderRepository.deleteById(id);
	}

	public void update(Long id, OrderDTO vO) {
		Order bean = requireOne(id);
		BeanUtils.copyProperties(vO, bean);
		orderRepository.save(bean);
	}

	public OrderDTO getById(Long id) {
		Order original = requireOne(id);
		return toDTO(original);
	}

	public Page<OrderDTO> query(OrderDTO vO) {
		throw new UnsupportedOperationException();
	}

	private OrderDTO toDTO(Order original) {
		OrderDTO bean = new OrderDTO();
		BeanUtils.copyProperties(original, bean);
		return bean;
	}

	private Order requireOne(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public OrderDTO checkOutSummary() throws CartNotFoundException {
		if (cartService.getShoppingCart() != null) {
			cartService.getShoppingCart().getCartTotal();
		}
		else {
			throw new CartNotFoundException("Cart not found or is empty");
		}
		Optional<Integer> totalQty = cartService.getShoppingCart().getCartItemList().stream()
				.map(cartItem -> cartItem.getQuantity()).reduce(Integer::sum);
		return new OrderDTO(cartService.getShoppingCart().getCartTotal(), totalQty.orElse(0),
				cartService.getShoppingCart().getTotalCartDiscount());
	}

	@Transactional
	public String createOrder(OrderDTO order, Long customerId) throws OrderNotCreatedException {
		try {
			Long newCustmomerId = saveOrUpdateCustomer(customerId, order);
			Long paymentId = savePaymentIfNew(order, customerId);
			Long orderId = saveOrderDTO(order, customerId, paymentId);
			emptyShoppingCart();
			sendEventToKafka("order", orderId);
			return orderId + Instant.now().toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new OrderNotCreatedException("Order Cannot be created");
		}
	}

	private void sendEventToKafka(String topicName, Long orderId) {

		producerThread.setTopic(topicName);
		Event event = new Event("OrderCreated", orderId.toString());
		producerThread.setEventObject(event);

		// producerThread.sendMessage(event,"order");
		producerThread.start();
	}

	private void emptyShoppingCart() {
		cartService.emptyCart();
	}

	private Long saveOrderDTO(OrderDTO orderDTO, Long customerId, Long paymentId) {
		java.util.List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		cartService.getShoppingCart().getCartItemList().forEach((cartItem) -> {
			;
			orderDetails.add(new OrderDetails(null, 1L, cartItem.getABook().getBookId(), cartItem.getQuantity(),
					cartItem.getABook().getCost()));
		});
		Order order = new Order(null, Instant.now().toString() + "", Instant.now(), customerId, "admin", paymentId,
				orderDTO.getOrderTotal(), orderDTO.getDiscountApplied(), "NEW", orderDetails);
		return this.orderRepository.save(order).getOrderId(); // orderId;
	}

	private Long saveOrUpdateCustomer(Long customerId, OrderDTO order) {
		Customer c = new Customer(customerId, null, order.getEmail(), order.getAddress1(), order.getAddress2(),
				order.getCity(), order.getCountry(), order.getZip());
		return custRepo.save(c).getCustomerId();
	}

	private Long savePaymentIfNew(OrderDTO order, Long customerId) {
		Optional<Payment> payment = paymentRepo.findFirstByCardNumber(order.getPaymentcard());
		return payment.orElseGet(() -> paymentRepo.save(
				new Payment(null, order.getPaymentcard(), order.getExpirydate(), order.getCvv(), true, customerId)))
				.getPaymentId();
	}

	public void handleOrderCreatedEvent(Event event) {

	}

	public void handleOrderDeliveredEvent(Event event) {
	}

}
