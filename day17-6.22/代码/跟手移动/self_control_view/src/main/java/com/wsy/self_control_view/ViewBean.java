package com.wsy.self_control_view;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class ViewBean {

        @Id    //表示是表中的主键
        private Long id; //一定是Long型
        private float dateX;
        private float dateY;
        @Generated(hash = 1330518756)
        public ViewBean(Long id, float dateX, float dateY) {
            this.id = id;
            this.dateX = dateX;
            this.dateY = dateY;
        }
        @Generated(hash = 1786715694)
        public ViewBean() {
        }
        public Long getId() {
            return this.id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public float getDateX() {
            return this.dateX;
        }
        public void setDateX(float dateX) {
            this.dateX = dateX;
        }
        public float getDateY() {
            return this.dateY;
        }
        public void setDateY(float dateY) {
            this.dateY = dateY;
        }
    

      

}
