package UserManagementSystem;

import java.sql.SQLException;
import java.util.List;

public class UserMSService {
    public List<AlmostRecord>  findList(){//
        try(var connection = DbUtil.getConnection()) {//connectionにDbUtilのgetConnection(DBの中身)を入れる
            var userDao = new UserMSDao(connection);//インスタンス(実体)生成。UserMSDaoにDB情報入ったconnectionを与えてなんやかんやしてuserDaoに入れる
            return userDao.findList();//レコードの型に沿って値が返される。id company_name username scoreの４種類
        } catch (SQLException e){//SQLのエラーと表示
            e.printStackTrace();
            return List.of();//空のリストを送ることで処理を継続させることができ、変更不可になる。
        }
    }

    public static List<CompanyRecord> findCompany() {
        try(var connection = DbUtil.getConnection()) {
            var userDao = new UserMSDao(connection);//Daoのクラスのメソッドが使えるように
            return userDao.findCompany();//findCompanyの結果をuserDaoに渡す
        } catch (SQLException e){
            e.printStackTrace();
            return List.of();
        }
    }

    public int insert(UserRecord p) {//入力された値をUMSDaoに追加させる
        try(var connection = DbUtil.getConnection()) {
            var insertDao = new UserMSDao(connection);
            return insertDao.insert(p);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public static List<AlmostRecord> findInsertDisplay(){
//        try(var connection = DbUtil.getConnection()) {
//            var userDao = new UserMSDao(connection);
//            return userDao.findInsertDisplay();
//        } catch (SQLException e){
//            e.printStackTrace();
//            return List.of();
//        }
//    }
//削除
    public int delete(int p) {
        try(var connection = DbUtil.getConnection()) {
            var productDao = new UserMSDao(connection);
            return productDao.delete(p);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
//削除されたものを表示
    public List<AlmostRecord> findDeleteDisplay(int p){//idに入った値がpに入る
        try(var connection = DbUtil.getConnection()) {
            var userDao = new UserMSDao(connection);
            return userDao.findDeleteDisplay(p);//UMSDaoにp投げる
        } catch (SQLException e){
            e.printStackTrace();
            return List.of();
        }
    }
//更新
    public static int update(UserRecord p) {//userUpdateの値がpに入る
        try(var connection = DbUtil.getConnection()) {
            var productDao = new UserMSDao(connection);
            return productDao.update(p);//UMSDaoのupdateにp投げる
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
/*
1718272847486768
*/