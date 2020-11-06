package executor;

import model.Limit;
import model.OrderBy;
import model.SubWhere;
import model.Where;

import java.text.Collator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SimpleExecutor {

    public List<Map<String, Object>> query(List<Map<String, Object>> data, Where where, OrderBy orderBy, Limit limit) {
        // Where 匹配
        List<Map<String, Object>> queryResult = execQuery(data, where);

        // 排序
        queryResult = execSort(queryResult, orderBy);

        // Limit
        queryResult = execLimit(queryResult, limit);

        return queryResult;
    }

    public List<Map<String, Object>> execQuery(List<Map<String, Object>> data, Where where) {
        if (null == where) return data;

        Map<String, Map<String, Object>> result = new HashMap<>(); // id : row
        Stream<Map<String, Object>> resultStream = null; // 用于实现 And 追加匹配

        LinkedList<SubWhere> wheres = where.getWheres();
        if (0 == wheres.size()) return data; // 无条件则返回原数据

        for (int i = 0, wheresSize = wheres.size(); i < wheresSize; i++) {
            SubWhere subWhere = wheres.get(i);
            SubWhere.AndOrOr andOrOr = subWhere.andOrOr;

            // 过滤
            if (0 == i) { // 第一轮过滤
                filter(subWhere, data.stream(), result);
                resultStream = result.values().stream();
            } else { // 后续过滤
                switch (andOrOr) {
                    case AND: // AND 则将 result 继续过滤
                        result.clear(); // 清理
                        filter(subWhere, resultStream, result);
                        resultStream = result.values().stream();
                        break;
                    case OR: // OR 则拿 data 过滤, 然后追加至 result 中
                        filter(subWhere, data.stream(), result);
                        resultStream = result.values().stream();
                }
            }
            // TODO 无嵌套(括号)匹配
            if (null != subWhere.subWhere) {
            }
        }
        return new ArrayList<>(result.values());
    }

    /**
     * 过滤方法
     *
     * @param subWhere     Where 条件
     * @param filterTarget 过滤基数据
     * @param outTarget    输出目标 Map, 将过滤好的数据 put 至目标 Map
     */
    public void filter(SubWhere subWhere, Stream<Map<String, Object>> filterTarget
            , Map<String, Map<String, Object>> outTarget) {
        switch (subWhere.whereType) { // 根据匹配类型选择实现
            case EQUALS: // Equals 匹配
                filterTarget.filter(o -> o.get(subWhere.whereKey).equals(subWhere.whereValue))
                        .forEach(
                                row -> { // 添加到 result 中
                                    outTarget.put(String.valueOf(row.get("id")), row);
                                });
        }
    }

    public List<Map<String, Object>> execSort(List<Map<String, Object>> data, OrderBy orderBy) {
        Map<String, Boolean> orders = orderBy.getOrders();


        orders.forEach((key, isDesc) -> {
            data.sort((o1, o2) -> {
                AtomicInteger sortResult = new AtomicInteger();
                sortResult.set(Collator.getInstance(Locale.CHINA).compare(
                        String.valueOf(o1.get(key)), String.valueOf(o2.get(key))
                ));

                return isDesc ? sortResult.get() * -1 : sortResult.get();
            });
        });

        return data;
    }

    public List<Map<String, Object>> execLimit(List<Map<String, Object>> data, Limit limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = limit.getStart(); i <= limit.getEnd(); i++) {
            result.add(data.get(i));
        }
        return result;
    }
}