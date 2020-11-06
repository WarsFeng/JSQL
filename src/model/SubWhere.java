package model;

public class SubWhere {

  /** 匹配条件 Key */
  public String whereKey;

  /** 匹配条件 Value */
  public Object whereValue;

  /** 匹配类型, equals... */
  public WhereTypeEnum whereType;

  /** 追加匹配, AND 或 OR */
  public AndOrOr andOrOr;

  /** 如果存在, 则开启嵌套匹配, 类似于 SQL 中的括号作用 */
  public SubWhere subWhere;

  public SubWhere(String whereKey, Object whereValue, WhereTypeEnum whereType) {
    this.whereKey = whereKey;
    this.whereValue = whereValue;
    this.whereType = whereType;
  }

  public SubWhere(String whereKey, Object whereValue, SubWhere subWhere, WhereTypeEnum whereType) {
    this.whereKey = whereKey;
    this.whereValue = whereValue;
    this.subWhere = subWhere;
    this.whereType = whereType;
  }

  public enum WhereTypeEnum {
    EQUALS("equals");
    private String whereType;

    WhereTypeEnum(String whereType) {}
  }

  public enum AndOrOr {
    AND,
    OR;

    AndOrOr() {}
  }
}
