package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.oner365.swagger.dto.server.Cpu;
import com.oner365.swagger.dto.server.Jvm;
import com.oner365.swagger.dto.server.Mem;
import com.oner365.swagger.dto.server.Sys;
import com.oner365.swagger.dto.server.SysFile;

/**
 * 服务器相关信息
 *
 * @author zhaoyong
 */
public class Server implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * CPU相关信息
   */
  private Cpu cpu = new Cpu();

  /**
   * 內存相关信息
   */
  private Mem mem = new Mem();

  /**
   * JVM相关信息
   */
  private Jvm jvm = new Jvm();

  /**
   * 服务器相关信息
   */
  private Sys sys = new Sys();

  /**
   * 磁盘相关信息
   */
  private List<SysFile> sysFiles = new LinkedList<>();

  public Cpu getCpu() {
    return cpu;
  }

  public void setCpu(Cpu cpu) {
    this.cpu = cpu;
  }

  public Mem getMem() {
    return mem;
  }

  public void setMem(Mem mem) {
    this.mem = mem;
  }

  public Jvm getJvm() {
    return jvm;
  }

  public void setJvm(Jvm jvm) {
    this.jvm = jvm;
  }

  public Sys getSys() {
    return sys;
  }

  public void setSys(Sys sys) {
    this.sys = sys;
  }

  public List<SysFile> getSysFiles() {
    return sysFiles;
  }

  public void setSysFiles(List<SysFile> sysFiles) {
    this.sysFiles = sysFiles;
  }

}
