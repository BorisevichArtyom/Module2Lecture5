package by.itacademy.javaenterprise.web.entity;

import java.util.ArrayList;
import java.util.List;

public class Woman {
    private int id;
    private String name;
    private String age;
    private static List<Woman> list = new ArrayList<>();


    public Woman(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = this.age;
    }

    public static List<Woman> getList() {
        return list;
    }

    {
        Woman whore = new Woman(1, "Susanne", "22");
        list.add(whore);
    }

    public int getId() {
        return id;
    }

    public String getName(Integer id) {
        if (id == this.id) {
            return name;
        } else return "nothing";
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
