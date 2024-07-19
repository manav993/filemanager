// src/main/java/com/filemanager/model/Outcome.java
package com.filemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Outcome {
    private String name;
    private String transport;
    private double topSpeed;
}
