package model;

import java.util.LinkedList;

public class Where {

  private LinkedList<SubWhere> wheres = new LinkedList<>();

  /**
   * 添加条件
   *
   * @param subWhere 条件封装
   * @return 返回本对象便于链式调用
   */
  public Where(SubWhere subWhere) {
    wheres.addLast(subWhere);
  }

  /**
   * 追加 AND 条件
   *
   * @param subWhere 条件封装
   * @return 返回本对象便于链式调用
   */
  public Where and(SubWhere subWhere) {
    subWhere.andOrOr = SubWhere.AndOrOr.AND;
    wheres.addLast(subWhere);
    return this;
  }
  /**
   * 追加 OR 条件
   *
   * @param subWhere 条件封装
   * @return 返回本对象便于链式调用
   */
  public Where or(SubWhere subWhere) {
    subWhere.andOrOr = SubWhere.AndOrOr.OR;
    wheres.addLast(subWhere);
    return this;
  }

  public LinkedList<SubWhere> getWheres() {
    return wheres;
  }
}
