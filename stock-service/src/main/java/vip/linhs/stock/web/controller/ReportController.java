package vip.linhs.stock.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.service.StockService;

@RestController
@RequestMapping("report")
public class ReportController extends BaseController {

    @Autowired
    private StockService stockService;

    @RequestMapping("stockList")
    public PageVo<StockInfo> getStockList(PageParam pageParam) {
        return stockService.getStockList(pageParam);
    }

}
