package com.example.DOAN1.RequestResponse;

import com.example.DOAN1.Entities.Lecturer;
import com.example.DOAN1.Entities.SchoolYear;
import lombok.Data;

@Data
public class RequestObject {
    private Lecturer lecturer;
    private SchoolYear year;

    // getters and setters
}
