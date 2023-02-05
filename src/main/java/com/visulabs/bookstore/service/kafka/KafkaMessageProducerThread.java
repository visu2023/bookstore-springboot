package com.visulabs.bookstore.service.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class KafkaMessageProducerThread extends Thread {

	private Event eventObject;

	private String topic;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public KafkaMessageProducerThread(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(Event obj, String topicName) {
		this.kafkaTemplate.send(topicName, obj);
	}

	public void run() {
		sendMessage(this.eventObject, this.topic);
	}

}
