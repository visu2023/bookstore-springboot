package com.visulabs.bookstore.service.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event implements Deserializer {

	String eventName;

	String eventParm;

	@Override
	public Object deserialize(String s, byte[] bytes) {
		return null;
	}

}
