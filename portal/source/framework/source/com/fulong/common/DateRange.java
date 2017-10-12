package com.fulong.common;

public class DateRange {
    private String begin;
    private String end;
    public DateRange() {
    }

    public DateRange(String b, String e) {
        begin = b;
        end = e;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
