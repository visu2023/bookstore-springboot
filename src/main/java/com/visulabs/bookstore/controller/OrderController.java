package com.visulabs.bookstore.controller;

import com.google.gson.Gson;
import com.visulabs.bookstore.exception.CartNotFoundException;
import com.visulabs.bookstore.exception.OrderNotCreatedException;
import com.visulabs.bookstore.service.BookStoreUser;
import com.visulabs.bookstore.service.BookStoreUserDetails;
import com.visulabs.bookstore.service.OrderDTO;
import com.visulabs.bookstore.service.OrderService;
import com.visulabs.bookstore.service.kafka.KafkaMessageProducerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/bookstore/v1/order/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public String save(@Valid @RequestBody OrderDTO vO) {
		return orderService.save(vO).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@Valid @NotNull @PathVariable("id") Long id) {
		orderService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@Valid @NotNull @PathVariable("id") Long id, @Valid @RequestBody OrderDTO vO) {
		orderService.update(id, vO);
	}

	@GetMapping("/{id}")
	public OrderDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
		return orderService.getById(id);
	}

	@GetMapping
	public Page<OrderDTO> query(@Valid OrderDTO vO) {
		return orderService.query(vO);
	}

	@ExceptionHandler(value = CartNotFoundException.class)
	public ResponseEntity handleCartNotFound(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@GetMapping
	@RequestMapping("/checkout")
	public ResponseEntity<OrderDTO> orderSummaryForCheckout(@Valid OrderDTO vO) throws CartNotFoundException {
		return ResponseEntity.ok().body(orderService.checkOutSummary());
	}

	@PostMapping
	@RequestMapping("/placeorder")
	public ResponseEntity createdOrder(@Valid @RequestBody OrderDTO order) throws OrderNotCreatedException {
		BookStoreUser user = (BookStoreUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String orderNum = orderService.createOrder(order, user.getCustomerId());
		Gson g = new Gson();
		return ResponseEntity.ok().body(g.toJson("Order sucessfully placed. Track Order in order history " + orderNum));

	}

	@ExceptionHandler(value = OrderNotCreatedException.class)
	public ResponseEntity handleOrderNotCreatedException(Exception e) {
		return ResponseEntity.internalServerError().body("Order Not Created");
	}

}
