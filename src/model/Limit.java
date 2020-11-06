package model;

public class Limit {

    /**
     * 从哪儿开始
     */
    private int start;

    /**
     * 从哪儿结束
     */
    private int end;

    public Limit(int limit) {
        start = 0;
        end = --limit;
    }

    public Limit(int start, int limit) {
        this.start = start;
        this.end = start + limit;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
