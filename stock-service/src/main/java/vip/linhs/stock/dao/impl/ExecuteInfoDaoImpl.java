package vip.linhs.stock.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.ExecuteInfoDao;
import vip.linhs.stock.model.po.ExecuteInfo;

@Repository
public class ExecuteInfoDaoImpl extends BaseDao implements ExecuteInfoDao {

    @Override
    public List<ExecuteInfo> getByTaskIdAndState(int[] id, int state) {
        ArrayList<Integer> paramsList = new ArrayList<>(id.length);
        for (int i : id) {
            paramsList.add(i);
        }
        String whereCause = String.join(",",
                paramsList.stream().map(str -> "?").collect(Collectors.toList()));
        paramsList.add(state);
        String sql = "select e.id, task_id as taskId, params_str as paramsStr from execute_info e, task t"
                + " where e.task_id = t.id and t.id in (" + whereCause + ") and e.state = ? order by t.id";
        return jdbcTemplate.query(sql, paramsList.toArray(), new BeanPropertyRowMapper<>(ExecuteInfo.class));
    }

    @Override
    public void update(ExecuteInfo executeInfo) {
        String sql = "update execute_info set start_time = ?, complete_time = ?, message = ? where id = ?";
        jdbcTemplate.update(sql, executeInfo.getStartTime(), executeInfo.getCompleteTime(), executeInfo.getMessage(),
                executeInfo.getId());
    }

}
