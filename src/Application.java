import executor.SimpleExecutor;
import model.Limit;
import model.OrderBy;
import model.SubWhere;
import model.Where;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static model.SubWhere.WhereTypeEnum.EQUALS;

public class Application {

    public static void main(String[] args) {
        // Build 查询数据
        List<Map<String, Object>> data = buildData();

        // 匹配条件构建
        Where where = new Where(new SubWhere("id", 2, EQUALS))
                .or(new SubWhere("id", 3, EQUALS))
                .or(new SubWhere("id", 4, EQUALS))
                .or(new SubWhere("id", 8, EQUALS));

        // 排序条件构建
        OrderBy orderBy = new OrderBy();
        orderBy.appendOrder("id", true);

        // Limit 条件构建
        Limit limit = new Limit(2);

        // Where 匹配
        List<Map<String, Object>> result = new SimpleExecutor().query(data, where, orderBy, limit);

        // Result
        System.out.println(result);
    }

    /**
     * 生成搜索数据
     */
    public static List<Map<String, Object>> buildData() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> row = Collections.singletonMap("id", i);
            data.add(row);
        }
        return data;
    }
}
