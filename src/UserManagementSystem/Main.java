package UserManagementSystem;

import java.util.Scanner;
public class Main {

    private static final int MODE_DISPLAY = 1;
    private static final int MODE_NEW = 2;
    private static final int MODE_DELETE = 3;
    private static final int MODE_UPDATE = 4;
    private static final int MODE_EXIT = 9;


    public static void main(String[] args) {


        start();


//入力する inputに代入
        while(true) {
            var sc = new Scanner(System.in);
            var input = sc.nextInt();
//入力が１なら...
            if(input == MODE_DISPLAY) {//inputの値がMODE_DISPLAYと同じなら
                displayUserList();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_NEW) {//input=2
                addUser();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_DELETE) {//input=3
                deleteUser();
                System.out.println("");
                displayMenu();
            } else if ((input == MODE_UPDATE)) {
                updateUser();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_EXIT) {
                System.out.println("終了します。");
                break;
            } else {
                System.out.println("もう一度入力してください。");
            }

        }
    }

    private static void start() {
        System.out.println("------------------------------");
        System.out.println("ユーザー管理システム\n");

        displayMenu();//真下へ
    }
    private static void displayMenu() {

        System.out.println("メニューを選択してください。");
        System.out.println("1：一覧表示");
        System.out.println("2：新規追加");
        System.out.println("3：削除");
        System.out.println("4：更新");
        System.out.println("9：終了");
        //startに戻る
    }




//入力した値で表示、一覧
    private static void displayUserList() {//1の場合
        var UserMSService = new UserMSService();//インスタンス（実体）生成。Serviceのメソッドが使えるように
        var userList = UserMSService.findList();//UserListにUserMSServiceのfindListの結果を渡す
        userList.stream().forEach(System.out::println);//streamを取得しforEachでprintlnを実行し続ける
        System.out.println("一覧を表示しました。");
    }


//ユーザーの追加
    private static void addUser() {//2の場合
        System.out.println("------------------------------");
        System.out.println("ユーザー追加");
        System.out.println("所属企業のid(数字)を選択してください。");

    //企業の表示
        var UserMSService = new UserMSService();//インスタンス（実体）生成
        var userCompany = UserMSService.findCompany();//userCompanyにUserMSServiceのfindCompanyの結果を渡す
        userCompany.stream().forEach(System.out::println);//streamを取得しforEachでprintlnを実行し続ける
        var sc = new Scanner(System.in);
        var companyId = sc.nextInt();//所属企業を入力

        System.out.println("");
        System.out.println("名前を入力してください。");
        var sc2 = new Scanner(System.in);
        var name = sc2.nextLine();//名前

        System.out.println("");
        System.out.println("スコアを入力してください。");
        var score = sc.nextInt();//スコア

        var userInsert = new UserRecord(0,companyId, name, score);//idが0なのは自動的に割り振られるから。他はすでに入力済
/*var Result = */UserMSService.insert(userInsert);//まだDBに登録されていない状態なのでUMSSのinsertにuserInsertの値を送る、
            System.out.println("ユーザーが追加されました。");
/*
            var userInsertDisplay = UserMSService.findInsertDisplay();//userInsertDisplayにUserMSServiceのfindInsertDisplayの結果を渡す
            userInsertDisplay.stream().forEach(System.out::println);
*/
    }

//ユーザーの削除
    private static void deleteUser() {
        System.out.println("------------------------------");
        System.out.println("ユーザー削除");
        System.out.println("削除するユーザーのIDを入力してください。");

        var sc = new Scanner(System.in);
        var id = sc.nextInt();

        var Service = new UserMSService();

        System.out.println("以下のユーザーを削除しました。");
        var userDeleteDisplay = Service.findDeleteDisplay(id);//UMSSのfindDeleteDisplayにidには消す人の数が代入。
        userDeleteDisplay.stream().forEach(System.out::println);//入力したidに該当する情報を表示

/*var userdelte = */Service.delete(id);


    }
//ユーザーの更新
    private static void updateUser() {
        System.out.println("------------------------------");
        System.out.println("ユーザー更新");
        System.out.println("更新するユーザーのIDを入力してください。");

        var Service = new UserMSService();

        var sc = new Scanner(System.in);
        var id = sc.nextInt();

        System.out.println("");
        System.out.println("名前を入力してください。");
        var sc2 = new Scanner(System.in);
        var name = sc2.nextLine();

        System.out.println("");
        System.out.println("所属企業のidを選択してください。");
        var userCompany = Service.findCompany();
        userCompany.stream().forEach(System.out::println);
        var sc3 = new Scanner(System.in);
        var companyId = sc3.nextInt();

        System.out.println("");
        System.out.println("スコアを入力してください。");
        var sc4 = new Scanner(System.in);
        var score = sc4.nextInt();

        var userUpdate = new UserRecord(id,companyId,name,score);
/*var update = */UserMSService.update(userUpdate);//UMSSのupdateに新しい配列userUpdateを渡す
//        System.out.println(update);

        System.out.println("ユーザーが更新されました。");
//        var updateUser = UserMSService.findInsertDisplay();
//        updateUser.stream().forEach(System.out::println);
    }

}