
#企业类别表
drop table if exists org_category;
create table org_category(
  id varchar(36) primary  key comment '主键',
  name varchar(20) comment '企业类别名称',
  note varchar(200) comment '类别描述',
  createDate timestamp default current_timestamp comment '创建日期'
) comment '企业类别表';


#企业信息表
drop table if exists org;
create table org(
  id varchar(36) primary key comment '主键',
  code varchar(50) not null comment '机构代码',
  name varchar(50) not null comment '机构名称',
  contact varchar(50) comment '联系人',
  tel varchar(20) comment '联系方式',
  addr varchar(200)  comment '企业地址',
  legalPerson varchar(30) comment '法人',
  province varchar(200) comment '所属省',
  city varchar(200) comment '所属市',
  region  varchar(200) comment '所属地区',
  createDate timestamp default current_timestamp comment '创建日期',
  status char(1) default '0' comment '状态，0：正常，1：禁用，2：删除',
  note varchar(2000) comment '企业描述'
) comment '企业信息表';

#权限表
drop table if exists functions;
create table functions(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '权限名称',
  url varchar(100) not null comment '访问路径',
  leaf bit default 1 comment '是否为叶子节点,0:否  1： 是',
  type varchar(10) default '功能' comment '类型：功能或菜单',
  priority int default 0 comment '优先级，值越大页面显示越靠前',
  pid varchar(36)   comment '父权限ID',
  status varchar(10) default 0 comment '状态，0：正常，1：禁用，2：删除',
  creator varchar(50) comment '创建人',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '备注',
	constraint fk_pid foreign key(pid) REFERENCES functions(id)
) comment '权限表';

#角色表
drop table if exists role;
create table role(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '角色名称',
  status varchar(10) default 0 comment '状态，0：正常，1：禁用，2：删除',
  creator varchar(50) comment '创建人',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '备注',
  org_id varchar(36) comment '所属企业',
  org_category_id varchar(36) comment '企业类别ID,企业管理员只能使用企业所在类别下的角色',
  allowedDelete bit default 1 comment '是否允许删除,0:不允许删除，1：允许删除',
  constraint fk_role_org foreign key(org_id) references org(id),
  constraint fk_role_org_category foreign key(org_category_id) references org_category(id)
) comment '角色表';

#用户表
drop table if exists t_user;
create table t_user(
  id varchar(36) primary key comment '主键',
  role_id varchar(36) comment '用户所属角色',
  username varchar(50) not null comment '用户名称',
  password varchar(100) unique default '123456' comment '密码',
  realname varchar(50) comment '真实姓名',
  tel varchar(20) comment '联系方式',
  status char(1) default '0' comment '状态，0：正常，1：禁用，2：删除',
  allowedDelete bit default 1 comment '是否允许删除,0:不允许删除，1：允许删除',
  creator varchar(50) comment '创建人',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '备注',
  org_id varchar(36) comment '所属企业',
  constraint fk_user_org foreign key(org_id) references org(id),
  constraint fk_user_role foreign key(role_id) references role(id)
) comment '用户表';


#区域管理员
drop table if exists area_manager;
create table area_manager(
  id varchar(36) primary key comment '主键',
  username varchar(50) not null comment '用户名称',
  password varchar(100) unique default '123456' comment '密码',
  province varchar(200) comment '所属省',
  city varchar(200) comment '所属市',
  region  varchar(200) comment '所属地区',
  createDate timestamp default current_timestamp comment '创建日期',
  org_category_id varchar(36) comment '企业类别id',
  constraint fk_org_category_area_manager foreign key(org_category_id) references org_category(id)
) comment '区域管理员';

#区域信息表
drop table if exists category;
create table category(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '区域名称',
  pid varchar(36) comment '父类ID',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(2000) comment '区域描述',
  constraint fk_category foreign key(pid) references category(id)
) comment '区域信息表';

#角色权限表
drop table if exists role_functions;
create table role_functions(
  id varchar(36) primary key comment '主键',
  role_id varchar(36) not null comment '角色ID',
  function_id varchar(36) not null comment '权限ID',
  constraint fk_function foreign key(function_id) references functions(id),
  constraint fk_role_id foreign key(role_id) references role(id)
) comment '角色权限表';

#企业类别权限表
drop table if exists org_category_functions;
create table org_category_functions(
  id varchar(36) primary key comment '主键',
  org_category_id varchar(36) not null comment '企业类别ID',
  function_id varchar(36) not null comment '权限ID',
  constraint fk_function_id foreign key(function_id) references functions(id),
  constraint fk_org_category_id foreign key(org_category_id) references org_category(id)
) comment '类别权限表';

#目录表，每个菜单可以存放在不同的目录下
drop table if exists directory;
create table directory(
  id varchar(36) primary key comment '主键',
  name varchar(20) unique not null comment '目录名称',
  priority int default 0 comment '优先级，值越大页面显示越靠前',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '说明'
) comment '目录表';

#目录菜单表
drop table if exists directory_functions;
create table directory_functions(
  id varchar(36) primary key comment '主键',
  directory_id varchar(36) not null comment '目录ID',
  function_id varchar(36) not null comment '权限ID',
  constraint fk_directory_functions_function_id foreign key(function_id) references functions(id),
  constraint fk_directory_id foreign key(directory_id) references directory(id)
) comment '目录菜单表';


