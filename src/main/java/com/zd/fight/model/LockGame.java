package com.zd.fight.model;



public class LockGame {
    private Integer id;

    private Integer recordId;

    private Integer userId;

    private boolean flag;

    public LockGame() {
    }

    public LockGame(Integer id, Integer recordId, Integer userId, boolean flag) {
        this.id = id;
        this.recordId = recordId;
        this.userId = userId;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
