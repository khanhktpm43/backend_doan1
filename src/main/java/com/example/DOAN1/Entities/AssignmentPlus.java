package com.example.DOAN1.Entities;

import lombok.Data;

@Data
public class AssignmentPlus extends Assignment{
    protected Long lecturerId;
    protected String time;
    protected  String semesterName;
}
