package com.estock.estockmanager.repository;

import com.estock.estockmanager.entity.StockInfo;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface StockInfoRepository {

    StockInfo findLatestStock(String companyCode);

    List<StockInfo> retrieveStockDetails(String companyCode, Instant startDate, Instant endDate);

    void deleteAllByCompanyCode(String companyCode);

    StockInfo save(StockInfo stockInfo);
}
