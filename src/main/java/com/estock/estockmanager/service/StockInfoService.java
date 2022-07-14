package com.estock.estockmanager.service;

import com.estock.estockmanager.dto.StockDetails;
import com.estock.estockmanager.entity.StockInfo;
import com.estock.estockmanager.exception.StockNotFoundException;
import com.estock.estockmanager.repository.StockInfoRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockInfoService {

    @Autowired
    private StockInfoRepositoryImpl stockInfoRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StockInfo addstock(Double price, String companyCode){
        StockInfo stocInfo = new StockInfo();
        stocInfo.setStockPrice(price);
        stocInfo.setCompanyCode(companyCode);
        stocInfo.setTimestamp(Instant.now());
        return stockInfoRepository.save(stocInfo);

    }

    public List<StockInfo> getAllStocks(String companyCode, String startDate, String endDate){
        Instant start = LocalDate.parse(startDate, dateTimeFormatter).atStartOfDay(ZoneId.of("+0")).toInstant();
        Instant end = LocalDate.parse(endDate, dateTimeFormatter).atStartOfDay(ZoneId.of("+0")).toInstant();
        return stockInfoRepository.retrieveStockDetails(companyCode, start, end);

    }

    public StockDetails getStockDetails(String companyCode, String startDate, String endDate) {
        StockInfo latestStockInfo = stockInfoRepository.findLatestStock(companyCode);
        if(null!= latestStockInfo) {
            Instant start = LocalDate.parse(startDate, dateTimeFormatter).atStartOfDay(ZoneId.of("+0")).toInstant();
            Instant end = LocalDate.parse(endDate, dateTimeFormatter).atStartOfDay(ZoneId.of("+0")).toInstant();
            List<Double> stockPriceList = stockInfoRepository.retrieveStockDetails(companyCode, start, end).stream().map(stockInfo -> stockInfo.getStockPrice()).collect(Collectors.toList());
            stockPriceList.stream().forEach(s->log.info(s.toString()));
            Optional<Double> min = stockPriceList.stream().min(Double::compare);
            Optional<Double> max = stockPriceList.stream().max((a,b)-> a>b?1:-1);
            Double average = stockPriceList.stream().collect(Collectors.averagingDouble(d -> d));
            Double minValue = min.orElseGet(()->0.0);
            Double maxValue = max.orElseGet(()->0.0);
            StockDetails stockDetails = new StockDetails(companyCode, start, end, latestStockInfo.getStockPrice(), minValue, maxValue, average);
            return stockDetails;
        }
        else{
            throw new StockNotFoundException("Stock details not found Exception for the company");
        }
    }
}

