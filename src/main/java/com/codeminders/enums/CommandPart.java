package com.codeminders.enums;

public enum CommandPart {
  PARTONE(0), PARTTWO(1);

  private Integer type;

  CommandPart(Integer type) {
    this.type = type;
  }

  public Integer getType() {
    return type;
  }

}
