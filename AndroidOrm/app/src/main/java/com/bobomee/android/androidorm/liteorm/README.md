## LiteOrm 简单实用

### 两种操作:

* 单独操作: LiteOrm.newSingleInstance(config);
* 级联操作: LiteOrm.newCascadeInstance(config);

 // 与非级联交叉使用：
 // db.cascade().save(user);//级联操作：保存[当前对象]，以及该对象所有的[关联对象]以及它们的[映射关系]，超贱！
 // db.single().save(user);//非级联操作：仅保存[当前对象]，高效率。
 
 
### 简单使用

1. 保持单例,可以在Application中初始化

```java
sDb = LiteOrm.newCascadeInstance(config());


private DataBaseConfig config() {

    DataBaseConfig config = new DataBaseConfig(this);
    config.dbName = DB_NAME;//数据库名,可以制定路径
    config.dbVersion = 1; // version
    config.onUpdateListener = null; // set database update listener
    config.debugged = BuildConfig.DEBUG; //open the log

    return config;
 }
```

2. Model创建,利用注解

3. CURD操作,

4. 约束冲突

5.

 
### 级联关系

* @Mapping(Relation.ManyToMany) : 多对多
* @Mapping(Relation.OneToMany) : 一对多
* @Mapping(Relation.OneToOne) : 一对一
* @Mapping(Relation.ManyToOne)


参考:http://www.jianshu.com/p/0d72226ef434