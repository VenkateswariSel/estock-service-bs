package com.estock.estockmanager.controller;

import com.estock.estockmanager.dto.StockDetails;
import com.estock.estockmanager.entity.StockInfo;
import com.estock.estockmanager.service.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockInfoController {

    @Autowired
    private StockInfoService stockInfoService;

    @GetMapping("get/{companycode}/{startdate}/{enddate}")
    public ResponseEntity<StockDetails> getStockDetails(@PathVariable("companycode") String companyCode, @PathVariable("startdate") String startDate, @PathVariable("enddate") String endDate){
        StockDetails stockDetails = stockInfoService.getStockDetails(companyCode,startDate,endDate);
        return new ResponseEntity<>(stockDetails,HttpStatus.OK);

    }

    @GetMapping("info/{companycode}/{startdate}/{enddate}")
    public ResponseEntity<List<StockInfo>> getAllStocks(@PathVariable("companycode") String companyCode, @PathVariable("startdate") String startDate, @PathVariable("enddate") String endDate){
        List<StockInfo> stocks = stockInfoService.getAllStocks(companyCode,startDate,endDate);
        return new ResponseEntity<>(stocks,HttpStatus.OK);

    }
}
