package com.oner365.hadoop.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 用户实体类
 *
 * @author zhaoyong
 */
public class User implements Writable {

    private String username;

    private Integer age;

    private String address;

    public User() {
        super();
    }

    public User(String username, Integer age, String address) {
        super();
        this.username = username;
        this.age = age;
        this.address = address;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeChars(username);
        output.writeInt(age);
        output.writeChars(address);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        username = input.readUTF();
        age = input.readInt();
        address = input.readUTF();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", age=" + age + ", address=" + address + "]";
    }

}