package com.sad.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sad.web.security.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class CreateOrUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createBy",String.class, SecurityUtil.getLoginUser().getUsername());
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateBy",String.class, SecurityUtil.getLoginUser().getUsername());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

    }
}
