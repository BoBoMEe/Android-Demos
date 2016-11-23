package com.bobomee.android.androidorm.liteorm;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import com.bobomee.android.androidorm.liteorm.model.Book;
import com.bobomee.android.androidorm.liteorm.model.Classes;
import com.bobomee.android.androidorm.liteorm.model.School;
import com.bobomee.android.androidorm.liteorm.model.Student;
import com.bobomee.android.androidorm.liteorm.model.Teacher;
import com.bobomee.android.androidorm.liteorm.model.base.Person;
import com.bobomee.android.androidorm.liteorm.util.LiteOrmUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.litesuits.orm.log.OrmLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created on 16/6/13.上午8:03.
 *
 * @author bobomee
 */
public class LiteOrmTestActivity extends ListActivity {

  private static final String TAG = "LiteOrmTestActivity";

  String[] items = {
      "save", "insert", "update", "update column", "query all", "query by builder", "query by id",
      "query any u want", "mapping test", "delete", "delete by index", "delete all"
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Model Relation：school-(1,N)->classes-(1,1)->teacher-(N,N)->student-(1,N)->book
    // 数据持有关系：学校 -(1,N)-> 班级 -(1,1)-> 老师 -(N,N)-> 学生 -(1,N)-> 书籍
    // 模拟数据 (1,N)一对多；（N,N）多对多；(N,1)多对一；(1,1)一对一
    mockData();

    setListAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1,
        new ArrayList<String>(Arrays.asList(items))) {
      @Override protected void convert(BaseAdapterHelper helper, String item) {
        helper.setText(android.R.id.text1, item);
      }
    });
  }

  //////////////////////////////////////////////

  @Override protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    switch (position) {
      case 0:
        testSave();
        break;
      case 1:
        testInsert();
        break;
      case 2:
        testUpdate();
        break;
      case 3:
        testUpdateColumn();
        break;
      case 4:
        testQueryAll();
        break;
      case 5:
        testQueryByWhere();
        break;
      case 6:
        testQueryByID();
        break;
      case 7:
        testQueryAnyUwant();
        break;
      case 8:
        testMapping();
        break;
      case 9:
        testDelete();
        break;
      case 10:
        testDeleteByIndex();
        break;
      case 11:
        testDeleteByWhereBuilder();
        break;
      case 12:
        testDeleteAll();
        break;
    }
  }
  ////////////////////////////////////////////////

  private void testSave() {
    LiteOrmUtil.INSTANCE.getmLiteOrm().save(school);
  }

  private void testInsert() {
    LiteOrmUtil.INSTANCE.getmLiteOrm().insert(bookList);

    // 联合唯一测试
    Book book1 = new Book("书：year和author联合唯一");
    book1.setIndex(1988);
    book1.setAuthor("hehe");

    Book book2 = new Book("和上一本冲突：year和author联合唯一");
    book2.setIndex(1988);
    book2.setAuthor("hehe");

    LiteOrmUtil.INSTANCE.getmLiteOrm().insert(book1);
    // 注意会报警告
    LiteOrmUtil.INSTANCE.getmLiteOrm().insert(book2, ConflictAlgorithm.Abort);
  }

  private void testUpdate() {
    for (Book book : bookList) {
      int j = book.getIndex() % 3;
      if (j == 0) {
        book.setStudent(student2);
      } else if (j == 1) {
        book.setStudent(student1);
      } else if (j == 2) {
        book.setStudent(student0);
      }
      book.setIndex(book.getIndex() + 100);
    }
    LiteOrmUtil.INSTANCE.getmLiteOrm().update(bookList);
  }

  private void testUpdateColumn() {
    // 把所有书的author改为liter
    HashMap<String, Object> bookIdMap = new HashMap<String, Object>();
    bookIdMap.put(Book.COL_AUTHOR, "liter");
    LiteOrmUtil.INSTANCE.getmLiteOrm().update(bookList, new ColumnsValue(bookIdMap), ConflictAlgorithm.Fail);

    // 使用下面方式也可以
    //LiteOrmUtil.INSTANCE.getmLiteOrm().update(bookList, new ColumnsValue(new String[]{Book.COL_AUTHOR},
    //        new String[]{"liter"}), ConflictAlgorithm.Fail);

    // 仅 author 这一列更新为该对象的最新值。
    //LiteOrmUtil.INSTANCE.getmLiteOrm().update(bookList, new ColumnsValue(new String[]{Book.COL_AUTHOR}, null), ConflictAlgorithm.Fail);
  }

  private void testQueryAll() {
    queryAndPrintAll(Book.class);
    queryAndPrintAll(Student.class);
    queryAndPrintAll(Teacher.class);
    queryAndPrintAll(Classes.class);
    queryAndPrintAll(School.class);
  }

  private void testQueryByWhere() {
    List<Student> list = LiteOrmUtil.INSTANCE.getmLiteOrm().query(new QueryBuilder<Student>(Student.class)
        .where(Person.COL_NAME + " LIKE ?", new String[]{"%0"})
        .whereAppendAnd()
        .whereAppend(Person.COL_NAME + " LIKE ?", new String[]{"%s%"}));
    OrmLog.i(TAG, list);
  }

  private void testQueryByID() {
    Student student = LiteOrmUtil.INSTANCE.getmLiteOrm().queryById(student1.getId(), Student.class);
    OrmLog.i(TAG, student);
  }

  private void testQueryAnyUwant() {
    List<Book> books = LiteOrmUtil.INSTANCE.getmLiteOrm().query(new QueryBuilder<Book>(Book.class)
        .columns(new String[]{"id", "author", Book.COL_INDEX})
        .distinct(true)
        .whereGreaterThan("id", 0)
        .whereAppendAnd()
        .whereLessThan("id", 10000)
        .limit(6, 9)
        .appendOrderAscBy(Book.COL_INDEX));
    OrmLog.i(TAG, books);
  }

  private void testMapping() {
    // 级联实例本来就保存了关系映射
    queryAndPrintAll(School.class);
  }

  private void testDelete() {
    // 删除 student-0
    LiteOrmUtil.INSTANCE.getmLiteOrm().delete(student0);
  }

  private void testDeleteByIndex() {
    // 按id升序，删除[2, size-1]，结果：仅保留第一个和最后一个
    // 最后一个参数可为null，默认按 id 升序排列
    LiteOrmUtil.INSTANCE.getmLiteOrm().delete(Book.class, 2, bookList.size() - 1, "id");
  }

  private void testDeleteByWhereBuilder() {
    // 删除 student-1
    LiteOrmUtil.INSTANCE.getmLiteOrm().delete(new WhereBuilder(Student.class)
        .where(Person.COL_NAME + " LIKE ?", new String[]{"%1%"})
        .and()
        .greaterThan("id", 0)
        .and()
        .lessThan("id", 10000));
  }

  private void testDeleteAll() {
    // 连同其关联的classes，classes关联的其他对象一带删除
    LiteOrmUtil.INSTANCE.getmLiteOrm().deleteAll(School.class);
    //LiteOrmUtil.INSTANCE.getmLiteOrm().deleteAll(Book.class);


    // 顺带测试：连库文件一起删掉
    //LiteOrmUtil.INSTANCE.getmLiteOrm().deleteDatabase();
    // 顺带测试：然后重建一个新库
    //LiteOrmUtil.INSTANCE.getmLiteOrm().openOrCreateDatabase();
    // 满血复活
  }


  private void queryAndPrintAll(Class claxx) {
    List list = LiteOrmUtil.INSTANCE.getmLiteOrm().query(claxx);
    OrmLog.i(TAG, claxx.getSimpleName() + " : " + list);
  }


  protected static School school = null;
  protected static Classes classA;
  protected static Classes classB;
  protected static Teacher teacherA;
  protected static Teacher teacherB;
  protected static Student student0;
  protected static Student student1;
  protected static Student student2;
  protected static ArrayList<Book> bookList = new ArrayList<Book>();

  private void mockData() {
    if (school != null) {
      return;
    }
    school = new School("US MIT");
    classA = new Classes("class-a");
    classB = new Classes("class-b");

    //school:classes = 1:N
    school.classesList = new ArrayList<Classes>();
    school.classesList.add(classA);
    school.classesList.add(classB);

    teacherA = new Teacher("teacher-a", 19);
    teacherB = new Teacher("teacher-b", 28);

    //classes:teacher = 1:1
    classA.teacher = teacherA;
    classB.teacher = teacherB;

    student0 = new Student("student-0");
    student1 = new Student("student-1");
    student2 = new Student("student-2");

    //teacher:student = N:N
    teacherA.setStudentLinkedQueue(new ConcurrentLinkedQueue<Student>());
    teacherA.getStudentLinkedQueue().add(student0);
    teacherA.getStudentLinkedQueue().add(student1);
    teacherB.setStudentLinkedQueue(new ConcurrentLinkedQueue<Student>());
    teacherB.getStudentLinkedQueue().add(student0);
    teacherB.getStudentLinkedQueue().add(student2);
    student0.setTeachersArray(new Teacher[]{teacherA, teacherB});
    student1.setTeachersArray(new Teacher[]{teacherA});
    student2.setTeachersArray(new Teacher[]{teacherB});

    for (int i = 0; i < 30; i++) {
      Book book = new Book("book-" + i);
      book.setAuthor("autor" + i).setIndex(i);
      int j = i % 3;
      if (j == 0) {
        book.setStudent(student0);
      } else if (j == 1) {
        book.setStudent(student1);
      } else if (j == 2) {
        book.setStudent(student2);
      }
      bookList.add(book);
    }
  }
////////////////////////

  @Override protected void onDestroy() {
    LiteOrmUtil.INSTANCE.getmLiteOrm().close();
    super.onDestroy();
  }
}
