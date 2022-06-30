package vip.linhs.stock.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.ExecuteInfoDao;
import vip.linhs.stock.model.po.ExecuteInfo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.TaskVo;
import vip.linhs.stock.util.SqlCondition;

@Repository
public class ExecuteInfoDaoImpl extends BaseDao implements ExecuteInfoDao {

    @Override
    public List<ExecuteInfo> getByTaskIdAndState(int[] id, Integer state) {
        ArrayList<Integer> paramsList = new ArrayList<>(id.length);
        for (int i : id) {
            paramsList.add(i);
        }
        String whereCause = String.join(",", paramsList.stream().map(str -> "?").collect(Collectors.toList()));
        String sql = "select e.id, task_id as taskId, params_str as paramsStr from execute_info e, task t"
                + " where e.task_id = t.id and t.id in (" + whereCause + ")";
        if (state != null) {
            paramsList.add(state);
            sql += " and e.state = ?";
        }
        sql +=  " order by t.id";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ExecuteInfo.class), paramsList.toArray());
    }

    @Override
    public void update(ExecuteInfo executeInfo) {
        String sql = "update execute_info set start_time = ?, complete_time = ?, message = ? where id = ?";
        jdbcTemplate.update(sql, executeInfo.getStartTime(), executeInfo.getCompleteTime(), executeInfo.getMessage(),
                executeInfo.getId());
    }

    @Override
    public PageVo<TaskVo> get(PageParam pageParam) {
        SqlCondition dataSqlCondition = new SqlCondition(
                "select e.id, t.name, e.state, t.description, e.start_time as startTime, e.complete_time as completeTime from execute_info e, task t where e.task_id = t.id",
                pageParam.getCondition());

        int totalRecords = jdbcTemplate.queryForObject(dataSqlCondition.getCountSql(), Integer.class,
                dataSqlCondition.toArgs());

        dataSqlCondition.addSql(" limit ?, ?");
        dataSqlCondition.addPage(pageParam.getStart(), pageParam.getLength());

        List<TaskVo> list = jdbcTemplate.query(dataSqlCondition.toSql(), BeanPropertyRowMapper.newInstance(TaskVo.class),
                dataSqlCondition.toArgs());
        return new PageVo<>(list, totalRecords);
    }

    @Override
    public void updateState(int state, int id) {
        jdbcTemplate.update("update execute_info set state = ? where id = ?", state, id);
    }

}
