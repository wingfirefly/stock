package vip.linhs.stock.model.po;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Date createTime;
    private Date updateTime;
    private boolean markForDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isMarkForDelete() {
        return markForDelete;
    }

    public void setMarkForDelete(boolean markForDelete) {
        this.markForDelete = markForDelete;
    }

    public void setBaiscModel(boolean insert) {
        updateTime = new Date();
        if (insert) {
            createTime = updateTime;
            markForDelete = false;
        }
    }

    @Override
    public String toString() {
        return "BaseModel [id=" + id + ", createTime=" + createTime + ", updateTime=" + updateTime + ", markForDelete="
                + markForDelete + "]";
    }

}
