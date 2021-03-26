package vip.linhs.stock.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import vip.linhs.stock.api.response.GetDealDataResponse;

public class TradeUtil {

    private TradeUtil() {
    }

    /**
     * merge the partial-deal list
     */
    public static List<GetDealDataResponse> mergeDealList(List<GetDealDataResponse> data) {
        LinkedHashMap<String, GetDealDataResponse> map = new LinkedHashMap<>();
        for (GetDealDataResponse getDealDataResponse : data) {
            String wtbh = getDealDataResponse.getWtbh();
            GetDealDataResponse response = map.get(wtbh);
            if (response == null) {
                response = mergeDeal(null, getDealDataResponse);
                map.put(wtbh, response);
            } else {
                mergeDeal(response, getDealDataResponse);
            }
        }
        return map.values().stream().filter(v -> v.getCjsl().equals(v.getWtsl())).collect(Collectors.toList());
    }

    private static GetDealDataResponse mergeDeal(GetDealDataResponse response, GetDealDataResponse getDealDataResponse) {
        if (response == null) {
            response = new GetDealDataResponse();
            response.setCjbh(getDealDataResponse.getCjbh());
            response.setCjjg(getDealDataResponse.getCjjg());
            response.setCjsj(getDealDataResponse.getCjsj());
            response.setCjsl(getDealDataResponse.getCjsl());
            response.setMmlb(getDealDataResponse.getMmlb());
            response.setWtbh(getDealDataResponse.getWtbh());
            response.setWtsl(getDealDataResponse.getWtsl());
            response.setZqdm(getDealDataResponse.getZqdm());
            response.setZqmc(getDealDataResponse.getZqmc());
        } else {
            int cjsl = Integer.parseInt(response.getCjsl());
            int cjsl2 = Integer.parseInt(getDealDataResponse.getCjsl());
            response.setCjsl(String.valueOf(cjsl + cjsl2));
        }
        return response;
    }

}
