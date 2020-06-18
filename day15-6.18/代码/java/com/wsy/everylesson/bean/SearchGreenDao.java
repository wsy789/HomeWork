package com.wsy.everylesson.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class SearchGreenDao {

        @Id    //表示是表中的主键
        private Long id; //一定是Long型
        private String date;
        @Generated(hash = 2146959775)
        public SearchGreenDao(Long id, String date) {
            this.id = id;
            this.date = date;
        }
        @Generated(hash = 1902099472)
        public SearchGreenDao() {
        }
        public Long getId() {
            return this.id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getDate() {
            return this.date;
        }
        public void setDate(String date) {
            this.date = date;
        }
      

}
