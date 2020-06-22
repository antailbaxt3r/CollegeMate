package com.antailbaxt3r.collegemate.comparators;

import com.antailbaxt3r.collegemate.models.Classes;

import java.util.Comparator;

public class TimetableComparator implements Comparator<Classes> {
    @Override
    public int compare(Classes classes, Classes t1) {
        return classes.getStart().compareTo(t1.getStart());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
