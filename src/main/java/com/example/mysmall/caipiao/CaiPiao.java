package com.example.mysmall.caipiao;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by danding on 2016/9/16.
 */
public class CaiPiao implements Serializable ,Comparable<CaiPiao> {
    private static final long serialVersionUID = 12532453L;

    private Integer date;
    private List<Integer> reds;
    private int blue;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public List<Integer> getReds() {
        return reds;
    }

    public void setReds(List<Integer> reds) {
        this.reds = reds;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public CaiPiao(String date, List<String> reds, int blue) {
        this.date = new Integer(date);
        this.reds = new ArrayList<>();
        for (String red : reds) {
            this.reds.add(new Integer(red));
        }
        Collections.sort(this.reds);

        this.blue = blue;
    }

    @Override
    public String toString() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (Integer inte : getReds()) {
            String source = inte + "";
            SpannableString msp = new SpannableString(source);
            msp.setSpan(new ForegroundColorSpan(Color.RED), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(msp);
            spannableStringBuilder.append("    ");
        }

        String blue1 = getBlue() + "";
        SpannableString msp = new SpannableString(blue1);
        msp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, blue1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(msp);

        return spannableStringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaiPiao caiPiao = (CaiPiao) o;

        if (blue != caiPiao.blue) return false;

        return reds != null ? reds.equals(caiPiao.reds) : caiPiao.reds == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (reds != null ? reds.hashCode() : 0);
        result = 31 * result + blue;
        return result;
    }

    @Override
    public int compareTo(CaiPiao another) {
        return date - another.date;
    }
}
