//package com.bool.AssetManagement.configuration;
//
////import com.bool.AssetManagement.domain.KakfaObject;
//import com.bool.AssetManagement.domain.Vehicle;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
////
////    @Bean
////    public DefaultKafkaProducerFactory producerFactory(){
////        Map<String,Object> config = new HashMap<>();
////        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
////        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
////        DefaultKafkaProducerFactory defaultKafkaProducerFactory = new DefaultKafkaProducerFactory(config);
////        return defaultKafkaProducerFactory;
////    }
////
////    @Bean
////    public KafkaTemplate<String, KakfaObject> kafkaTemplate(){
////        return new KafkaTemplate<String, KakfaObject>(producerFactory());
////    }
////
////
//    @Bean
//    public ConsumerFactory<String,Vehicle> consumerFactory(){
//        Map<String,Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG,"sample-group");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//       return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),
//                new JsonDeserializer<>(Vehicle.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Vehicle> kafkaListener(){
//        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}


package com.bool.AssetManagement.configuration;
import com.bool.AssetManagement.domain.BookingObject;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig  {


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.104:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        System.out.println("1");
        return new DefaultKafkaConsumerFactory<>(config);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        factory.setMissingTopicsFatal(false);
        System.out.println("2");
        return factory;
    }


    @Bean
    public ConsumerFactory<String, BookingObject > userConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.23.239.104:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        System.out.println("3");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(BookingObject .class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,BookingObject > userKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        factory.setMissingTopicsFatal(false);
        System.out.println("4");
        return factory;
    }



}
