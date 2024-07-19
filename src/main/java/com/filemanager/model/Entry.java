// src/main/java/com/filemanager/model/Entry.java
package com.filemanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    private String uuid;
    private String id;
    private String name;
    private String likes;
    private String transport;
    private double avgSpeed;
    private double topSpeed;
}
