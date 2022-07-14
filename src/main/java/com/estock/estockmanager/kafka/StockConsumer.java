package com.estock.estockmanager.kafka;

import com.estock.estockmanager.repository.StockInfoRepositoryImpl;
import com.estock.estockmanager.service.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockConsumer {

    private StockInfoService stockInfoService;

    private StockInfoRepositoryImpl stockInfoRepository;

    public StockConsumer(StockInfoService stockInfoService, StockInfoRepositoryImpl stockInfoRepository){
        this.stockInfoService = stockInfoService;
        this.stockInfoRepository = stockInfoRepository;

    }

    @KafkaListener(topics ="${kafka.stock.add.topic}",groupId = "${kafka.stock.add.groupid}")
    public void consumeEStockMessage(ConsumerRecord<String,String> consumerRecord){
        log.info("Received stock to add");
        stockInfoService.addstock(Double.parseDouble(consumerRecord.value()),consumerRecord.key());
    }

    @KafkaListener(topics ="${Kafka.stock.delete.topic}",groupId = "${kafka.stock.delete.groupid}")
    public void ConsumeDeleteEStockMessage(ConsumerRecord<String,String> consumerRecord){
        log.info("Received stock to delete");
        stockInfoRepository.deleteAllByCompanyCode(consumerRecord.value());
    }

}
