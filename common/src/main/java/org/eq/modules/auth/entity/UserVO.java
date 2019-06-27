package org.eq.modules.auth.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户管理Entity
 * @author hobe
 * @version 2019-05-30
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private String nickname;

    private Integer sex;

    private String birthday;

    private String photoHead;

    private String intro;


}