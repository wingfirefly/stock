package vip.linhs.stock.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class SqlCondition {

    private StringBuilder sqlString;
    private Map<String, Object> params;
    private List<Object> objList = new ArrayList<>();

    public SqlCondition(String sqlString, Map<String, Object> params) {
        this.sqlString = new StringBuilder(sqlString);
        this.params = params;
    }

    public SqlCondition(String sqlString, Map<String, Object> params, List<Object> objList) {
        this.sqlString = new StringBuilder(sqlString);
        this.params = params;
        this.objList = objList;
    }

    /**
     * 新增字符串查询条件(等价于 == )
     */
    public SqlCondition addString(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s = ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 新增字符串查询条件(等价于 != )
     */
    public SqlCondition addStringNotEquals(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s <> ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 新增SQL
     */
    public SqlCondition addSql(String sql, Object... args) {
        if (!StringUtils.isEmpty(sql)) {
            sqlString.append(sql);
            for (Object o : args) {
                objList.add(o);
            }
        }
        return this;
    }

    /**
     * 新增字符串查询条件(等价于 >= )
     */
    public SqlCondition addStringGE(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s >= ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 新增字符串查询条件(等价于 <= )
     */
    public SqlCondition addStringLE(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s <= ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 新增字符串查询条件(等价于 < )
     */
    public SqlCondition addStringLT(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s < ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 新增字符串查询条件(等价于 > )
     */
    public SqlCondition addStringGT(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s > ? ", column));
            objList.add(params.get(key).toString());
        }
        return this;
    }

    /**
     * 模糊查询
     */
    public SqlCondition addStringLike(String key, String column) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(" and %s like ?", column));
            objList.add("%" + params.get(key).toString() + "%");
        }
        return this;
    }

    /**
     * 添加排序
     */
    public void addSort(String key, String column, boolean first) {
        if (!StringUtils.isEmpty(params.get(key))) {
            sqlString.append(String.format(first ? " %s %s" : ", %s %s", column, params.get(key)));
        }
    }

    /**
     * 添加分页参数
     *
     * @param start
     * @param length
     */
    public void addPage(int start, int length) {
        objList.add(start);
        objList.add(length);
    }

    public Object[] toArgs() {
        return objList.toArray();
    }

    public String toSql() {
        return sqlString.toString();
    }

    public String getCountSql() {
        return String.format("select count(1) from (%s) _row", sqlString.toString());
    }

    public List<Object> getObjectList() {
        return objList;
    }

}
