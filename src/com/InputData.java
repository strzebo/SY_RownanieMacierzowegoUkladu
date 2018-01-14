package com;

public class InputData {
    public String name;
    public int node1;
    public int node2;
    public float value;
    public ElementTpe type;

    public InputData(String name, int node1, int node2, float value, ElementTpe type) {
        this.name = name;
        if (node1 < 0) {
            node1 = 0;
        }
        if (node2 < 0) {
            node2 = 0;
        }
        this.node1 = node1;
        this.node2 = node2;
        if (value == 0) {
            value = 1;
        }
        this.value = value;
        this.type = type;
    }
}
