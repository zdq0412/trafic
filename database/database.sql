
#企业类别表
drop table if exists org_category;
create table org_category(
  id varchar(36) primary  key comment '主键',
  name varchar(20) comment '企业类别名称',
  note varchar(200) comment '类别描述',
  safetyCostRatio  float  comment '安全生产费用提取百分比',
  createDate timestamp default current_timestamp comment '创建日期'
) comment '企业类别表';

#区域信息表
drop table if exists category;
create table category(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '区域名称',
  pid varchar(36) comment '父类ID',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(2000) comment '区域描述',
  type varchar(50) comment '类型',
  constraint fk_category foreign key(pid) references category(id)
) comment '区域信息表';

#企业信息表
drop table if exists org;
create table org(
  id varchar(36) primary key comment '主键',
  code varchar(50)  comment '机构代码',
  name varchar(50) not null comment '机构名称',
  contact varchar(50) comment '联系人',
  tel varchar(20) comment '联系方式',
  addr varchar(200)  comment '企业地址',
  legalPerson varchar(30) comment '法人',
  province_id varchar(36) comment '所属省',
  city_id varchar(36) comment '所属市',
  region_id  varchar(36) comment '所属地区',
  createDate timestamp default current_timestamp comment '创建日期',
  status char(1) default '0' comment '状态，0：正常，1：禁用，2：删除',
  note varchar(2000) comment '企业描述',
  org_category_id varchar(36) comment '企业类别ID',

  established_time timestamp comment '成立日期',
  business_scope varchar(2000) comment '经营范围',
  email varchar(100) comment '邮箱',
  introduction text comment '企业介绍',
  report_tel varchar(20) comment '举报电话',

  fourColorPicUrl varchar(100) comment '四色图访问路径',
  fourColorPicRealPath varchar(100) comment '四色图磁盘路径',

  shortName varchar(50) comment '简称',
  constraint fk_org_category_province_id foreign key(province_id) references category(id),
  constraint fk_org_category_city_id foreign key(city_id) references category(id),
  constraint fk_org_category_region_id foreign key(region_id) references category(id),
  constraint fk_org_category_id_org foreign key(org_category_id) references org_category(id)
) comment '企业信息表';

#危险源清单
drop table if exists m037_hazard_sources_list;
create table m037_hazard_sources_list(
 id varchar(36) primary key comment 'ID主键',
  name varchar(50) not null comment '危险源名称',
  happen integer comment '发生可能性',
  consequence integer comment '后果严重性',
  criterion integer comment '判定准则',
  riskLevel varchar(100) comment '安全风险等级',
  fourColor varchar(50) comment '四色标识',
  measures varchar(2000) comment '应采取的行动/控制措施',
  timeLimit varchar(200) comment '实施期限',
  org_id varchar(36) not null comment '所属企业ID',
  constraint fkm037_hazard_sources_list_org_id foreign key(org_id) references org(id)
) comment '危险源清单';

#法律法规文件
drop table if exists m004_law;
create table m004_law(
 id varchar(36) primary key comment 'ID主键',
  name varchar(1000) not null comment '法律法规文件名称',
  content text comment '法律法规内容',
  publishDate timestamp comment '发布日期',
  implementDate timestamp comment '实施日期',
  publishDepartment varchar(100) comment '发文部门',
  note varchar(1000) comment '备注',
  province_id varchar(36) comment '所属省',
  city_id varchar(36) comment '所属市',
  region_id  varchar(36) comment '所属地区',
  orgCategory_id varchar(36) comment '企业类别',
  num varchar(500) comment '发文字号:企业简称+年+自增序号',
  org_id varchar(36) comment '所属企业ID',
  timeliness varchar(20) default '有效' comment '时效性，有效或无效',
  constraint fk_m004_law_category_province_id foreign key(province_id) references category(id),
  constraint fk_m004_law_category_city_id foreign key(city_id) references category(id),
  constraint fk_m004_law_category_region_id foreign key(region_id) references category(id),
  constraint fk_m004_law_org_id foreign key(org_id) references org(id),
  constraint fk_m004_law_orgCategory_id foreign key(orgCategory_id) references org_category(id)
) comment '法律法规文件';

#安全规章制度
drop table if exists m005_rules;
create table m005_rules(
 id varchar(36) primary key comment 'ID主键',
  name varchar(1000) not null comment '安全规章制度文件名称',
  content text comment '安全规章制度内容',
  publishDate timestamp comment '发布日期',
  publishDepartment varchar(100) comment '发文部门',
  note varchar(1000) comment '备注',
  province_id varchar(36) comment '所属省',
  city_id varchar(36) comment '所属市',
  region_id  varchar(36) comment '所属地区',
  orgCategory_id varchar(36) comment '企业类别',
  num varchar(500) comment '发文字号:企业简称+年+自增序号',
  org_id varchar(36)  comment '所属企业ID',
  timeliness varchar(20) default '有效' comment '时效性，有效或无效',
  constraint fk_m005_rules_category_province_id foreign key(province_id) references category(id),
  constraint fk_m005_rules_category_city_id foreign key(city_id) references category(id),
  constraint fk_m005_rules_category_region_id foreign key(region_id) references category(id),
  constraint fk_m005_rules_org_id foreign key(org_id) references org(id),
  constraint fk_m005_rules_orgCategory_id foreign key(orgCategory_id) references org_category(id)
) comment '安全规章制度';

#企业发文通知
drop table if exists m006_notice;
create table m006_notice(
 id varchar(36) primary key comment 'ID主键',
  name varchar(1000) not null comment '企业发文通知名称',
  content text comment '企业发文通知内容',
  publishDate timestamp comment '发布日期',
  publishDepartment varchar(100) comment '发文部门',
  note varchar(1000) comment '备注',
  province_id varchar(36) comment '所属省',
  city_id varchar(36) comment '所属市',
  region_id  varchar(36) comment '所属地区',
  orgCategory_id varchar(36) comment '企业类别',
  num varchar(500) comment '发文字号:企业简称+年+自增序号',
  org_id varchar(36)  comment '所属企业ID',
  law_id varchar(36) comment '法律法规id',
  rules_id varchar(36) comment '企业规章制度id',
  timeliness varchar(20) default '有效' comment '时效性，有效或无效',
  constraint fk_m006_notice_m005_rules_rules_id foreign key(rules_id) references m005_rules(id),
  constraint fk_m006_notice_m004_law_law_id foreign key(law_id) references m004_law(id),
  constraint fk_m006_notice_category_province_id foreign key(province_id) references category(id),
  constraint fk_m006_notice_category_city_id foreign key(city_id) references category(id),
  constraint fk_m006_notice_category_region_id foreign key(region_id) references category(id),
  constraint fk_m006_notice_org_id foreign key(org_id) references org(id),
  constraint fk_m006_notice_orgCategory_id foreign key(orgCategory_id) references org_category(id)
) comment '企业发文通知';


#企业图片
drop table if exists m001_org_img;
create table m001_org_img(
 id varchar(36) primary key comment 'ID主键',
  name varchar(50) not null comment '图片名称',
  url varchar(200) comment '资质文件路径',
  realPath varchar(200) comment '实际路径',
  uploadDate timestamp comment '上传日期',
  org_id varchar(36) not null comment '所属企业ID',
  constraint fk_m001_org_img_org_id foreign key(org_id) references org(id)
) comment '企业图片';

#模板管理
drop table if exists template;
create table template(
 id varchar(36) primary key comment 'ID主键',
  name varchar(50) not null comment '模板名称',
  content text comment '模板内容',
  createDate timestamp  comment '创建时间',
  creator varchar(50) comment '创建人用户名',
  note varchar(500) comment '备注'
) comment '模板管理';

#安全生产费用支出类别
drop table if exists m019_safety_investment_category;
create table m019_safety_investment_category(
 id varchar(36) primary key comment 'ID主键',
  serialNo varchar(10) not null comment '类别序号',
  name varchar(500) comment ' 类别名称'
) comment '安全生产费用支出类别';

#年度安全生产费用
drop table if exists m019_safety_production_cost;
create table m019_safety_production_cost(
 id varchar(36) primary key comment 'ID主键',
  safetyYear int  comment '年度',
  lastYearActualIncome decimal default 0.00  comment '上年度实际营业收入',
  currentYearCost decimal default 0.00   comment '本年度应提取的安全费用,计算：通过上年度营业收入计算，普货：1%，客运和威货：1.5%',
  lastYearCarryCost decimal default 0.00  comment '上年度结转安全费用',
  currentYearActualCost decimal default 0.00  comment '本年度实际可用安全费用，计算：本年度应提取+上年度结转',
  unit varchar(20) default '元' comment '费用单位',
  createDate TIMESTAMP comment '创建日期',
  org_id varchar(36) comment '所属企业ID',
  isFillIn bit default 0 comment '是否已填写安全生产费用使用计划',
  constraint fk_m019_safety_production_cost_org_org_id foreign  key(org_id) references org(id)
) comment '年度安全生产费用';

#年度安全生产费用使用计划
drop table if exists m019_safety_production_cost_plan;
create table m019_safety_production_cost_plan(
 id varchar(36) primary key comment 'ID主键',
 serialNo varchar(20) not null comment '序号',
 name varchar(500) comment '名称',
 planCost decimal default 0.00 comment '计划金额',
 safety_production_cost_id varchar(36),
 constraint fk_m019_safety_production_cost_plan_safety_production_cost_id foreign key(safety_production_cost_id) references m019_safety_production_cost(id)
) comment '年度安全生产费用使用计划';

#年度安全生产费用使用明细
drop table if exists m019_safety_production_cost_plan_detail;
create table m019_safety_production_cost_plan_detail(
 id varchar(36) primary key comment 'ID主键',
 billingDate timestamp comment '开票日期',
 createDate timestamp comment '创建日期',
 content varchar(2000) comment '内容摘要',
 sumOfMoney decimal default 0.00 comment '金额',
 billNo varchar(100) comment '票据号码',
 operator varchar(500) comment '经办人',
 note varchar(2000) comment '备注',
 org_id varchar(36) comment '所属企业ID',
 plan_id varchar(36) comment '年度安全生产费用使用计划id',
 constraint fk_m019_safety_production_cost_plan_plan_id foreign key(plan_id) references m019_safety_production_cost_plan(id),
  constraint fk_m019_safety_production_cost_plan_detail_org_org_id foreign  key(org_id) references org(id)
) comment '年度安全生产费用使用明细';

#企业资质文件
drop table if exists m001_org_doc;
create table m001_org_doc(
  id varchar(36) primary key comment 'ID主键',
  name varchar(50) not null comment '资质文件名称',
  doc_num varchar(100) comment '文件编号',
  beginDate timestamp comment '有效期起始时间',
  endDate timestamp comment '有效期终止时间',
  uploadDate timestamp comment '上传日期',
  url varchar(200) comment '资质文件路径',
  realPath varchar(200) comment '实际路径',
  org_id varchar(36) not null comment '所属企业ID',
  constraint fk_m001_org_doc_org_id foreign key(org_id) references org(id)
) comment '企业资质文件表';

#部门表
drop table if exists m002_department;
create table m002_department(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '部门名称',
  pid varchar(36)   comment '父部门ID',
  tel varchar(50) comment '部门电话',
  org_id varchar(36) comment '企业id',
  business varchar(2000) comment '部门职能',
  constraint fk_m002_department_pid foreign key(pid) REFERENCES m002_department(id),
  constraint fk_m002_department_org_id foreign key(org_id) references org(id)
)comment '部门表';

#职位表
drop table if exists m002_position;
create table m002_position(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '职位名称',
  note varchar(500) comment '职位描述',
 department_id varchar(36) comment '所在部门',
  org_id varchar(36) comment '企业id',
  constraint fk_m002_position_m002_department_id foreign key(department_id) REFERENCES m002_department(id),
  constraint fk_m002_department_m002_position_org_id foreign key(org_id) references org(id)
)comment '职位表';


#权限表
drop table if exists functions;
create table functions(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '权限名称',
  url varchar(100)  comment '访问路径',
  leaf bit default 1 comment '是否为叶子节点,0:否  1： 是',
  type varchar(10) default '功能' comment '类型：功能或菜单',
  priority int default 0 comment '优先级，值越大页面显示越靠前',
  pid varchar(36)   comment '父权限ID',
  status varchar(10) default 0 comment '状态，0：正常，1：禁用，2：删除',
  creator varchar(50) comment '创建人',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '备注',
  icon varchar(50) comment '图标',
  c_index varchar(50)  comment '唯一标识，要和前端路由的地址相同',
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
  province_id varchar(36) comment '所属省',
  city_id varchar(36) comment '所属市',
  region_id  varchar(36) comment '所属地区',
  createDate timestamp default current_timestamp comment '创建日期',
  org_category_id varchar(36) comment '企业类别id',
   constraint fk_area_manager_category_province_id foreign key(province_id) references category(id),
  constraint fk_area_manager_category_city_id foreign key(city_id) references category(id),
  constraint fk_area_manager_category_region_id foreign key(region_id) references category(id),
  constraint fk_org_category_area_manager foreign key(org_category_id) references org_category(id)
) comment '区域管理员';


#企业人员资料
drop table if exists m003_employee;
create table m003_employee(
  id varchar(36) primary key comment 'ID主键',
  name varchar(50) not null comment '员工名称',
  sex varchar(10) comment '性别',
  age int comment '年龄',
  tel varchar(20) comment '联系电话',
  idnum varchar(20) comment '身份证号',
  org_id varchar(36) comment '所属企业ID',
  note varchar(200) comment '备注',
  photo varchar(200) comment '头像',
  realPath varchar(200) comment '真实路径',
  user_id varchar(36) comment '人员对应用户ID',
  department_id varchar(36) comment '部门id',
  position_id varchar(36) comment '职位id',
  constraint fk_m003_employee_department_id foreign key(department_id) references m002_department(id),
  constraint fk_m003_employee_position_id foreign key(position_id) references m002_position(id),
  constraint fk_m003_employee_org_id foreign key(org_id) references org(id),
  constraint fk_m003_employee_user_id foreign key(user_id) references t_user(id)
) comment '企业人员资料表';


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

#模式表，模式表下关联一级菜单(directory表)
drop table if exists t_schema;
create table t_schema(
  id varchar(36) primary key comment '主键',
  name varchar(36) not null comment '模式名称',
  priority int comment '优先级',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '说明',
  selected bit(1) default 1 comment '是否选中，页面上显示选中的模式的菜单'
) comment '模式表';

#目录表，每个菜单可以存放在不同的目录下
drop table if exists directory;
create table directory(
  id varchar(36) primary key comment '主键',
  name varchar(50) not null comment '权限名称',
  priority int default 0 comment '优先级，值越大页面显示越靠前',
  schema_id varchar(36)   comment '所属模式ID',
  status varchar(10) default '0' comment '状态，0：正常，1：禁用，2：删除',
  creator varchar(50) comment '创建人',
  createDate timestamp default current_timestamp comment '创建日期',
  note varchar(200) comment '备注',
  icon varchar(50) comment '图标',
  c_index varchar(50)  comment '唯一标识，要和前端路由的地址相同',
	constraint fk_schema_id foreign key(schema_id) REFERENCES t_schema(id)
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



