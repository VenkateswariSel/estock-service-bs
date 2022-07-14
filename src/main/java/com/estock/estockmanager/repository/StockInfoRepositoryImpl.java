package com.estock.estockmanager.repository;

import com.estock.estockmanager.entity.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class StockInfoRepositoryImpl implements StockInfoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public StockInfo findLatestStock(String companyCode) {
        Criteria criteria = Criteria.where("companyCode").in(companyCode);
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "timestamp"));
        return mongoTemplate.findOne(query, StockInfo.class);
    }

    @Override
    public List<StockInfo> retrieveStockDetails(String companyCode, Instant startDate, Instant endDate) {
        Criteria criteria = Criteria.where("companyCode").in(companyCode).andOperator(Criteria.where("timestamp").gte(startDate).lte(endDate));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, StockInfo.class);
    }

    @Override
    public void deleteAllByCompanyCode(String companyCode) {
        Criteria criteria = Criteria.where("companyCode").in(companyCode);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, StockInfo.class);

    }

    @Override
    public StockInfo save(StockInfo stockInfo) {
        return mongoTemplate.save(stockInfo);
    }

}
