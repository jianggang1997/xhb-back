/*注册信息表*/
CREATE TABLE xhb_register(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,#注册表id
  sid VARCHAR(32) NOT NULL UNIQUE,          #注册表sid
  account VARCHAR(20) NOT NULL UNIQUE ,     #注册账号
  pwd_hash VARCHAR(20) NOT NULL ,           #注册密码密文
  reg_date TIMESTAMP NOT NULL,              #注册日期
  reg_ip VARCHAR(255) NOT NULL,             #注册ip
  reg_type TINYINT UNSIGNED NOT NULL        #注册类型
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户信息表*/
CREATE TABLE xhb_user(
  id INT PRIMARY KEY AUTO_INCREMENT,        #用户id
  nickname VARCHAR(10) NOT NULL UNIQUE ,    #用户别名
  name VARCHAR(10) NOT NULL,                #用户姓名
  tel INT ,                                 #用户电话
  addr varchar(50),                         #用户地址
  birthday DATE ,                           #用户出生日期
  emil VARCHAR(20),                         #用户Emil
  qq  VARCHAR(20),                          #用户qq
  weixin VARCHAR(20) ,                      #用户微信
  weibo varchar(20),                        #用户微博
  school_name varchar(50)                   #用户学校名称  ----冗余列
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*系统管理员表*/
CREATE TABLE xhb_sys_admin(
  id INT PRIMARY KEY AUTO_INCREMENT,        #管理员id
  username VARCHAR(20) NOT NULL UNIQUE ,    #管理员账号
  pwd_hash VARCHAR(20)  NOT NULL,           #管理员密码密文
  emil VARCHAR(20) NOT NULL                 #管理员邮箱
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*后台权限表*/
CREATE TABLE xhb_sys_authority(
  id INT PRIMARY KEY AUTO_INCREMENT,        #权限id
  auth_name varchar(20) NOT NULL UNIQUE ,   #权限名称
  remark VARCHAR(50)                        #权限描述
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*权限分配表*/
CREATE TABLE xhb_sys_authority_admin(
  admin_id INT,                             #管理员id
  auth_id INT,                              #权限id
  primary key (admin_id,auth_id),
  CONSTRAINT fk_xsaa_admin FOREIGN KEY (admin_id) REFERENCES xhb_sys_admin(id),
  CONSTRAINT fk_xsaa_auth FOREIGN KEY (auth_id) REFERENCES xhb_sys_authority(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*登录记录表*/
CREATE TABLE xhb_user_login_record(
  id INT PRIMARY KEY AUTO_INCREMENT,        #登录记录id
  in_date DATE NOT NULL,                    #登录时间
  out_date DATE,                            #登出时间
  ip INT NOT NULL                           #登入ip
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户图片库*/
CREATE TABLE xhb_user_img(
  id INT PRIMARY KEY AUTO_INCREMENT,        #图片id
  reg_id INT,                               #用户注册id
  name VARCHAR(30) NOT NULL ,               #图片名称
  size int not null ,                       #图片大小
  type VARCHAR(10) NOT NULL ,               #图片格式
  add_date TIMESTAMP NOT NULL ,             #图片上传时间
  url varchar(100) NOT NULL UNIQUE,                           #图片地址
  CONSTRAINT fk_xui_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*系统图片库*/
CREATE TABLE xhb_xys_img(
  id INT  PRIMARY KEY AUTO_INCREMENT,       #图片id
  admin_id INT,                             #管理员id
  name VARCHAR(30) NOT NULL ,               #图片名称
  size INT NOT NULL ,                       #图片大小
  type VARCHAR(10) NOT NULL ,               #图片类型
  add_date TIMESTAMP NOT NULL,              #图片上传时间
  url varchar(100) NOT NULL UNIQUE,         #图片地址
  CONSTRAINT fk_xxi_admin FOREIGN KEY (admin_id) REFERENCES xhb_sys_admin(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*学校*/
CREATE TABLE xhb_school(
  id INT PRIMARY KEY AUTO_INCREMENT,        #学校id
  name VARCHAR(50) NOT NULL UNIQUE ,        #学校名称
  alias VARCHAR(50) ,                       #学校别名-多个用；号隔开
  addr VARCHAR(50),                         #学校地址
  intro MEDIUMTEXT,                         #学校简介
  type VARCHAR(10)                          #学校类型
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*院级*/
CREATE TABLE xhb_school_academy(
  id INT PRIMARY KEY AUTO_INCREMENT,      #学院id
  name VARCHAR(30) NOT NULL,      #学院名称
  alias VARCHAR(50),                      #学院别名
  intro MEDIUMTEXT,                       #学院简介
  sch_id INT,                             #学校id
  CONSTRAINT fk_xsa_sch FOREIGN KEY (sch_id) REFERENCES xhb_school(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*系级*/
CREATE TABLE xhb_academy_department(
  id INT PRIMARY KEY AUTO_INCREMENT,     #系id
  name VARCHAR(30) NOT NULL ,     #系名称
  alias VARCHAR(50),                     #系别称
  intro MEDIUMTEXT,                      #系简介
  aca_id INT,                            #院id
  CONSTRAINT fk_xad_aca FOREIGN KEY (aca_id) REFERENCES xhb_school_academy(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*班级*/
CREATE TABLE xhb_department_class(
  id INT PRIMARY KEY AUTO_INCREMENT,    #班级id
  name VARCHAR(30) NOT NULL,            #班级姓名
  alias VARCHAR(50),                    #班级别名
  intro MEDIUMTEXT,                     #班级简介
  domain_name VARCHAR(30) NOT NULL ,    #专业名称
  start_date DATE NOT NULL ,            #开班日期
  end_date DATE NOT NULL ,              #结业日期
  dep_id INT,                           #系id
  CONSTRAINT fk_xdc_dep FOREIGN KEY (dep_id) REFERENCES xhb_academy_department(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*用户班级信息*/
CREATE TABLE xhb_user_class(
  reg_id INT,                           #用户注册id
  cla_id INT,                           #班级id
  primary key (reg_id,cla_id),
  CONSTRAINT fk_xuc_reg FOREIGN KEY (reg_id) references xhb_register(id),
  CONSTRAINT fk_xuc_cla FOREIGN KEY (cla_id) references xhb_department_class(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*链接*/
CREATE TABLE xhb_link(
  id INT PRIMARY KEY AUTO_INCREMENT,    #链接id
  name VARCHAR(50) NOT NULL ,           #链接名称
  url  VARCHAR(100) NOT NULL ,          #链接地址
  remark VARCHAR(50)                    #备注
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*系统文章*/
CREATE TABLE xhb_sys_article(
  id INT PRIMARY KEY AUTO_INCREMENT,    #文章id
  title VARCHAR(100) NOT NULL ,         #文章标题
  content MEDIUMTEXT,                   #文章内容
  add_date TIMESTAMP NOT NULL           #上传时间
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*系统文章发布*/
CREATE TABLE xhb_sys_article_pub(
  id INT PRIMARY KEY AUTO_INCREMENT,    #发布文章id
  status INT NOT NULL ,                 #发布文章状态
  art_id INT,                           #文章id
  adm_id INT,                           #发布文章管理id
  save_date DATE NOT NULL ,             #保存时间
  KEY (art_id,adm_id),
  CONSTRAINT fk_xsap_art FOREIGN KEY (art_id) REFERENCES xhb_sys_article(id),
  CONSTRAINT fk_xsap_adm FOREIGN KEY (adm_id) REFERENCES xhb_sys_admin(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户文章表*/
CREATE TABLE xhb_user_article(
  id INT PRIMARY KEY AUTO_INCREMENT,    #文章id
  title VARCHAR(100) NOT NULL ,         #文章标题
  content MEDIUMTEXT,                   #文章内容
  add_date TIMESTAMP NOT NULL           #保存时间
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*用户文章发布*/
CREATE TABLE xhb_user_article_pub(
  id INT PRIMARY KEY AUTO_INCREMENT,   #发布文章id
  status INT NOT NULL ,                #发布文章状态
  art_id INT,                          #文章id
  reg_id INT,                          #用户id
  save_time TIMESTAMP NOT NULL ,       #发布时间
  KEY (art_id,reg_id),
  CONSTRAINT fk_xuap_art FOREIGN KEY (art_id) REFERENCES xhb_user_article(id),
  CONSTRAINT fk_xuap_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户动态*/
CREATE TABLE xhb_user_dynamic(
  id INT PRIMARY KEY AUTO_INCREMENT,   #动态id
  content VARCHAR(120),                #动态内容
  pub_date TIMESTAMP NOT NULL ,        #动态发布时间
  reg_id INT,                          #用户id
  CONSTRAINT fk_xud_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE xhb_user_dyn_imgs(
  id INT PRIMARY KEY AUTO_INCREMENT,    #动态附加图片
  dyn_id INT,                           #动态id
  img_id INT,                           #图片id
  KEY (dyn_id,img_id),
  CONSTRAINT fk_xudi_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xudi_img FOREIGN KEY (img_id) REFERENCES xhb_user_img(id)
);

/*可见性*/
CREATE TABLE xhb_user_visibility(
  id INT PRIMARY KEY AUTO_INCREMENT,    #可见性id
  visi VARCHAR(15) NOT NULL ,           #可见性名称
  grade int not null                    #可见性等级
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*发布动态的可见性*/
CREATE TABLE xhb_user_dyn_vis(
  id INT PRIMARY KEY AUTO_INCREMENT,    #动态可见性id
  dyn_id INT,                           #动态id
  vis_id INT,                           #可见性id
  KEY (dyn_id,vis_id),
  CONSTRAINT fk_xudv_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xudv_vis FOREIGN KEY (vis_id) REFERENCES xhb_user_visibility(id)
)ENGINE=INNODB DEFAULT  CHARSET=utf8;

/*发布文章的可见性*/
CREATE TABLE xhb_user_art_vis(
  id INT PRIMARY KEY  AUTO_INCREMENT,   #文章可见性id
  art_id INT,                           #文章id
  vis_id INT,                           #可见性id
  KEY (art_id,vis_id),
  CONSTRAINT fk_xuav_art FOREIGN KEY (art_id) REFERENCES xhb_user_article_pub(id),
  CONSTRAINT fk_xuav_vis FOREIGN KEY (vis_id) REFERENCES xhb_user_visibility(id)
)ENGINE=INNODB DEFAULT  CHARSET=utf8;

/*动态点赞*/
CREATE TABLE xhb_user_dyn_like(
  id INT PRIMARY KEY AUTO_INCREMENT,    #点赞id
  dyn_id INT,                           #动态id
  reg_id INT,                           #用户id
  date TIMESTAMP,                       #点赞时间
  KEY(dyn_id,reg_id),
  CONSTRAINT fk_xudl_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xudl_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*踩动态*/
CREATE TABLE xhb_user_dyn_stamp(
  id INT PRIMARY KEY AUTO_INCREMENT,  #踩动态id
  dyn_id INT,                         #动态id
  reg_id INT,                         #用户id
  date TIMESTAMP,                     #踩时间
  KEY (dyn_id,reg_id),
  CONSTRAINT fk_xuds_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xuds_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*动态评论*/
CREATE TABLE xhb_user_dyn_comment(
  id INT PRIMARY KEY AUTO_INCREMENT,  #评论id
  content VARCHAR(50),                #评论内容
  date TIMESTAMP NOT NULL ,           #评论时间
  reg_id INT,                         #评论人id
  dyn_id INT,                         #评论动态id
  CONSTRAINT fk_xudc_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id),
  CONSTRAINT fk_xudc_dyn FOREIGN KEY (dyn_id) references xhb_user_dynamic(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*动态评论回复表*/
CREATE TABLE xhb_user_dyn_comm_reply(
  id INT PRIMARY KEY AUTO_INCREMENT,      #评论回复id
  reply_type int NOT NULL ,               #回复类型
  content VARCHAR(50),                    #如果为1 则reply_id=comm_id,为2则为回复的回复
  date TIMESTAMP NOT NULL ,               #评论时间
  reply_id INT  not null ,                #父回复id
  comm_id INT,                            #根评论id
  from_id INT,                            #回复者id
  to_id INT,                              #回复目标id
  CONSTRAINT fk_xudcr_comm FOREIGN KEY (comm_id) REFERENCES xhb_user_dyn_comment(id),
  CONSTRAINT fk_xudcr_from FOREIGN KEY (from_id) REFERENCES xhb_register(id),
  CONSTRAINT fk_xudce_to  FOREIGN KEY (to_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;