package com.visulabs.bookstore.service.kafka;

import com.visulabs.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class KafkaEventConsumer {

	@Autowired
	private EntityManager em;

	@Autowired
	OrderService service;

	@KafkaListener(topics = "order", groupId = "group-id")
	public void listen(Event message) {
		if (message != null) {
			if (message.getEventName().equals("ORDERCREATED")) {
				service.handleOrderCreatedEvent(message); // send sucess mail
				// deliveryService.handleOrderCreatedEvent();//change status to
				// READY_TO_SHIP..
			}
			else if (message.getEventName().equals("ORDERERROR")) {
				// service.handleOrderErrprEvent();//send failure mail.
			}
			else if (message.getEventName().equals("ORDERSHIIPED")) {
				service.handleOrderDeliveredEvent(message);// send mail and change status
															// to SHIPPED.
			}
		}
	}

}
