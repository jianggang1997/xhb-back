/*注册信息表*/
CREATE TABLE xhb_register(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,#注册表id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  account VARCHAR(50) NOT NULL UNIQUE ,     #注册账号
  pwd_md5 VARCHAR(50) NOT NULL ,           #注册密码密文
  reg_date DATETIME NOT NULL DEFAULT current_timestamp,  #注册日期
  reg_ip VARCHAR(255) NOT NULL,             #注册ip
  reg_type TINYINT UNSIGNED NOT NULL,       #注册类型 1邮箱 2手机号
  info_status TINYINT UNSIGNED NOT NULL DEFAULT 0, #信息完善状态 0未完成 1完成
  gmt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  gmt_modify DATETIME NOT NULL DEFAULT current_timestamp
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户信息表*/
CREATE TABLE xhb_user(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,#用户id
  reg_id INT UNSIGNED,
  sid VARCHAR(50) NOT NULL UNIQUE,           #sid
  nickname VARCHAR(10) NOT NULL UNIQUE ,     #用户别名
  name VARCHAR(10) NOT NULL,                 #用户姓名
  tel INT ,                                  #用户电话
  addr varchar(50),                          #家庭地址
  now_addr VARCHAR(50),                      #现住址
  birthday DATE ,                            #用户出生日期
  emil VARCHAR(20),                          #用户Emil
  qq  VARCHAR(20),                           #用户qq
  weixin VARCHAR(20) ,                       #用户微信
  weibo varchar(20),                         #用户微博
  school_name varchar(50),                   #用户学校名称  ----冗余列
  school_class VARCHAR(50),                  #学校班级     -----冗余列
  CONSTRAINT fk_xu_reg FOREIGN KEY (reg_id) references xhb_register(id),
  gmt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  gmt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;



/*登录记录表*/
CREATE TABLE xhb_user_login_record(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,        #登录记录id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  in_date DATE NOT NULL,                    #登录时间
  out_date DATE,                            #登出时间
  ip INT NOT NULL,                           #登入ip
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户图片库*/
CREATE TABLE xhb_user_img(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,        #图片id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  reg_id INT UNSIGNED,                               #用户注册id
  name VARCHAR(200) NOT NULL ,               #图片名称
  size int not null ,                       #图片大小
  type VARCHAR(10) NOT NULL ,               #图片格式
  add_date TIMESTAMP NOT NULL ,             #图片上传时间
  url varchar(300) NOT NULL UNIQUE,         #图片地址
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modift DATETIME NOT NULL DEFAULT current_timestamp ,
  CONSTRAINT fk_xui_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*系统图片库*/
CREATE TABLE xhb_xys_img(
  id INT UNSIGNED  PRIMARY KEY AUTO_INCREMENT,       #图片id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  name VARCHAR(200) NOT NULL ,               #图片名称
  size INT NOT NULL ,                       #图片大小
  type VARCHAR(10) NOT NULL ,               #图片类型
  func_desc varchar(255),                   #功能描述
  add_date TIMESTAMP NOT NULL,              #图片上传时间
  url varchar(300) NOT NULL UNIQUE,         #图片地址
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*学校*/
CREATE TABLE xhb_school(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,        #学校id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  name VARCHAR(50) NOT NULL UNIQUE ,        #学校名称
  alias VARCHAR(50) ,                       #学校别名-多个用；号隔开
  addr VARCHAR(50),                         #学校地址
  intro MEDIUMTEXT,                         #学校简介
  type VARCHAR(10),                          #学校类型
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*院级*/
CREATE TABLE xhb_school_academy(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,      #学院id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  name VARCHAR(30) NOT NULL,      #学院名称
  alias VARCHAR(50),                      #学院别名
  intro MEDIUMTEXT,                       #学院简介
  sch_id INT UNSIGNED,                             #学校id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xsa_sch FOREIGN KEY (sch_id) REFERENCES xhb_school(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*系级*/
CREATE TABLE xhb_academy_department(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,     #系id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  name VARCHAR(30) NOT NULL ,     #系名称
  alias VARCHAR(50),                     #系别称
  intro MEDIUMTEXT,                      #系简介
  aca_id INT UNSIGNED,                            #院id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xad_aca FOREIGN KEY (aca_id) REFERENCES xhb_school_academy(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*班级*/
CREATE TABLE xhb_department_class(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,    #班级id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  name VARCHAR(30) NOT NULL,            #班级姓名
  alias VARCHAR(50),                    #班级别名
  intro MEDIUMTEXT,                     #班级简介
  domain_name VARCHAR(30) NOT NULL ,    #专业名称
  start_date DATE NOT NULL ,            #开班日期
  end_date DATE NOT NULL ,              #结业日期
  dep_id INT UNSIGNED,                           #系id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xdc_dep FOREIGN KEY (dep_id) REFERENCES xhb_academy_department(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*用户班级信息*/
CREATE TABLE xhb_user_class(
  reg_id INT UNSIGNED,                           #用户注册id
  cla_id INT UNSIGNED,                           #班级id
  primary key (reg_id,cla_id),
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xuc_reg FOREIGN KEY (reg_id) references xhb_register(id),
  CONSTRAINT fk_xuc_cla FOREIGN KEY (cla_id) references xhb_department_class(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



/*用户文章表*/
CREATE TABLE xhb_user_article(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,    #文章id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  title VARCHAR(100) NOT NULL ,         #文章标题
  content MEDIUMTEXT,                   #文章内容
  add_date TIMESTAMP NOT NULL,           #保存时间
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*用户文章发布*/
CREATE TABLE xhb_user_article_pub(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,   #发布文章id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  status INT NOT NULL ,                #发布文章状态
  art_id INT UNSIGNED,                          #文章id
  reg_id INT UNSIGNED,                          #用户id
  save_time TIMESTAMP NOT NULL ,       #发布时间
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  KEY (art_id,reg_id),
  CONSTRAINT fk_xuap_art FOREIGN KEY (art_id) REFERENCES xhb_user_article(id),
  CONSTRAINT fk_xuap_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*用户动态*/
CREATE TABLE xhb_user_dynamic(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,  #动态id
  sid VARCHAR(50) NOT NULL UNIQUE,             #sid
  content VARCHAR(255),                        #动态内容
  pub_date TIMESTAMP NOT NULL ,                #动态发布时间
  imgs VARCHAR(200) ,                          #动态图片集合用;分隔
  reg_id INT UNSIGNED,                         #用户id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xud_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*可见性*/
CREATE TABLE xhb_user_visibility(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,    #可见性id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  visi VARCHAR(15) NOT NULL ,           #可见性名称
  grade int not null,                    #可见性等级
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*发布动态的可见性*/
CREATE TABLE xhb_user_dyn_vis(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,    #动态可见性id
  sid VARCHAR(50) NOT NULL UNIQUE,          #sid
  dyn_id INT UNSIGNED,                           #动态id
  vis_id INT UNSIGNED,                           #可见性id
  KEY (dyn_id,vis_id),
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xudv_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xudv_vis FOREIGN KEY (vis_id) REFERENCES xhb_user_visibility(id)
)ENGINE=INNODB DEFAULT  CHARSET=utf8;


/*动态转发*/
CREATE TABLE xhb_user_dyn_share(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,    #转发d
  sid VARCHAR(50) NOT NULL UNIQUE,               #sid
  dyn_id INT UNSIGNED,                           #动态id
  reg_id INT UNSIGNED,                           #用户id
  content VARCHAR(255),                          #转发内容
  date TIMESTAMP,                       #点赞时间
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  KEY(dyn_id,reg_id),
  CONSTRAINT fk_xudl_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xudl_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*踩动态*/
CREATE TABLE xhb_user_dyn_stamp(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,  #踩动态id
  sid VARCHAR(50) NOT NULL UNIQUE,             #sid
  dyn_id INT UNSIGNED,                         #动态id
  reg_id INT UNSIGNED,                         #用户id
  date TIMESTAMP,                              #踩时间
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  KEY (dyn_id,reg_id),
  CONSTRAINT fk_xuds_dyn FOREIGN KEY (dyn_id) REFERENCES xhb_user_dynamic(id),
  CONSTRAINT fk_xuds_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*动态评论*/
CREATE TABLE xhb_user_dyn_comment(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,  #评论id
  sid VARCHAR(50) NOT NULL UNIQUE,             #sid
  content VARCHAR(50),                         #评论内容
  date TIMESTAMP NOT NULL ,                    #评论时间
  reg_id INT UNSIGNED,                         #评论人id
  dyn_id INT UNSIGNED,                         #评论动态id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xudc_reg FOREIGN KEY (reg_id) REFERENCES xhb_register(id),
  CONSTRAINT fk_xudc_dyn FOREIGN KEY (dyn_id) references xhb_user_dynamic(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*动态评论回复表*/
CREATE TABLE xhb_user_dyn_comm_reply(
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,      #评论回复id
  sid VARCHAR(50) NOT NULL UNIQUE,                 #sid
  reply_type int NOT NULL ,                        #回复类型 如果为1 则reply_id=comm_id,为2则为回复的回复
  content VARCHAR(50),                             #回复内容
  date TIMESTAMP NOT NULL ,                        #评论时间
  reply_id INT  not null ,                         #父回复id
  comm_id INT UNSIGNED,                            #根评论id
  from_id INT UNSIGNED,                            #回复者id
  to_id INT UNSIGNED,                              #回复目标id
  mgt_create DATETIME NOT NULL DEFAULT current_timestamp ,
  mgt_modify DATETIME NOT NULL DEFAULT current_timestamp,
  CONSTRAINT fk_xudcr_comm FOREIGN KEY (comm_id) REFERENCES xhb_user_dyn_comment(id),
  CONSTRAINT fk_xudcr_from FOREIGN KEY (from_id) REFERENCES xhb_register(id),
  CONSTRAINT fk_xudce_to  FOREIGN KEY (to_id) REFERENCES xhb_register(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;