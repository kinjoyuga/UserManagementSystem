package UserManagementSystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/*
DBから結果を表すオブジェクト。Getなんちゃらが使えるようになる。
nextで結果セットの次の行に行きデータにアクセスできる
*/
import java.sql.PreparedStatement;//SQLインジェクション対策になる。stmtが使えるようになる
public class UserMSDao {
    private Connection connection;
//変更される可能性があるため、ソースコード内に分散 → 変更があった際に不便

    public UserMSDao(Connection connection) {
        this.connection = connection;
    }

//やりたい事：usersの一覧を表示
    public List<AlmostRecord> findList(){//1...?
                        /*　usersのid   companiesのname      usersのname         usersのscore*/
        final var SQL = "SELECT u.id,c.name AS company_name,u.name AS user_name,u.score FROM users u JOIN companies c ON u.company_id = c.id ORDER BY id";
//          final var SQL = "SELECT * FROM users";
        /*
         FROMでusers(u.),JOIN追加でcompanies(c.)のテーブルからユーザーid & name & score とカンパニーnameを取得する。
         ONでu.company_idはc.idと同じ扱いにする
        */
        var AlmostRecord = new ArrayList<AlmostRecord>();//インスタンス(実体)生成。
        try (PreparedStatement stmt = this.connection.prepareStatement(SQL)) {//SQLの実行準備。this(ryからメソッドprepa(ry...(SQL)を呼び出しstmtに入れる
            ResultSet rs = stmt.executeQuery();//resultSet。DBからの結果を返すことができる

            while (rs.next()) {//次の行にデータがあるか判断
                var user = new AlmostRecord(rs.getInt("id"), rs.getString("company_name"), rs.getString("user_name"), rs.getInt("score"));
                //ユーザーid & name & score とカンパニーnameを取得する
                AlmostRecord.add(user);//取得したものをAlmostRecordに入れる
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return AlmostRecord;
    }

    //所属企業
    public List<CompanyRecord> findCompany() {
        final var SQL = "SELECT * FROM companies ORDER BY id";//id順でカンパニー全表示
        var CompanyRecord = new ArrayList<CompanyRecord>();

        try(PreparedStatement stmt = this.connection.prepareStatement(SQL)) {//(ry こういうもんだと思ったほうが早い
            ResultSet rs = stmt.executeQuery();//(ry

            while ((rs.next())){//企業の数だけループして表示。
                var company = new CompanyRecord(rs.getInt("id"), rs.getString("name"));
                //companyのidとnameを取得
                CompanyRecord.add(company);//今のをCompanyRecordに追加する
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompanyRecord;
    }

    //レコードの追加
    public int insert(UserRecord p) {
        final var INSERT_SQL = "INSERT INTO users (name,company_id,score) VALUES ('" + p.name() + "',"  + p.company_id() + ", " + p.score() +  ")";
        int result = 0;
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //追加したレコードの表示
    public List<AlmostRecord> findInsertDisplay() {
        final var SQL = "SELECT u.id,c.name AS company_name,u.name AS user_name,u.score FROM users u JOIN companies c ON u.company_id = c.id";// ORDER BY u.id//　DESC LIMIT 1 DESCは降順、LIMITで数字行だけ取得する
        var AlmostRecord = new ArrayList<AlmostRecord>();

        try(PreparedStatement stmt = this.connection.prepareStatement(SQL)) {
            ResultSet rs = stmt.executeQuery();

            while ((rs.next())){
                var company = new AlmostRecord(rs.getInt("id"), rs.getString("company_name"), rs.getString("user_name"), rs.getInt("score"));
                AlmostRecord.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return AlmostRecord;
    }

    //デリート
    public int delete(int p) {
        final var SQL = "DELETE FROM users WHERE id = " + p;//usersテーブルのid=pを削除
        int result = 0;
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            result = stmt.executeUpdate();//実行されると削除されたものがresultに残り続ける
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //デリートするレコードを表示
    public List<AlmostRecord> findDeleteDisplay(int p) {
        final var SQL = "SELECT u.id,c.name AS company_name,u.name AS user_name,u.score FROM users u JOIN companies c ON u.company_id = c.id WHERE u.id =" + p ;//id=pの情報を取得
        var AlmostRecord = new ArrayList<AlmostRecord>();

        try(PreparedStatement stmt = this.connection.prepareStatement(SQL)) {
            ResultSet rs = stmt.executeQuery();

            while ((rs.next())){
                var company = new AlmostRecord(rs.getInt("id"), rs.getString("company_name"), rs.getString("user_name"), rs.getInt("score"));
                AlmostRecord.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return AlmostRecord;
    }

    //アップデート
    public int update(UserRecord p) {//入力した値がSQL内に追記される
        final var SQL = "UPDATE users SET company_id = " + p.company_id() + ", name = '" + p.name() + "',score = " + p.score() + " WHERE id = " + p.id() ;
        int result = 0;
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            result = stmt.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}