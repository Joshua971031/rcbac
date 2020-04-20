package test;
import entity.Duty;
import entity.File;
import entity.User;
import rcbac.Rcbac;

import java.util.ArrayList;

public class Test {

    public void init(User u , File f1, File f2){
        /*初始化一个用户，职责为医学，初始化三个文件
          文件1职责为疫苗，职责关键字为“生物、抗体、药物，文件关键字为“病毒”，充当用户基础集
          文件2职责为制药，职责关键字为“生物、科学、药物”，文件关键字为“病毒、生化”
          文件3职责为电脑，职责关键字为“电脑、数码、硬件”，文件关键字为“科技”
        */
        ArrayList<String> keyword1 = new ArrayList<>(); //“医学”职责关键字
        keyword1.add("医生");
        keyword1.add("手术");
        keyword1.add("药物");
        ArrayList<String> keyword2 = new ArrayList<>();//“制药”职责关键字
        keyword2.add("生物");
        keyword2.add("科学");
        keyword2.add("药物");
        ArrayList<String> keyword3 = new ArrayList<>();//“疫苗”职责关键字
        keyword3.add("生物");
        keyword3.add("抗体");
        keyword3.add("药物");
        ArrayList<String> keyword4 = new ArrayList<>();//“电脑”职责关键字
        keyword4.add("电脑");
        keyword4.add("数码");
        keyword4.add("硬件");
        Duty d1 = new Duty("医学",keyword1);
        Duty d2 = new Duty("制药",keyword2);
        Duty d3 = new Duty("疫苗",keyword3);
        Duty d4 = new Duty("电脑",keyword4);
        ArrayList<Duty> duty_u = new ArrayList<>();
        ArrayList<Duty> duty_f = new ArrayList<>();
        ArrayList<Duty> duty_bs1 = new ArrayList<>();
        duty_u.add(d1);
        duty_f.add(d2);
        duty_bs1.add(d3);
        ArrayList<File> bs = new ArrayList<>();
        ArrayList<String> keyword_bs1 = new ArrayList<>();
        keyword_bs1.add("病毒");
        ArrayList<String> keyword_f = new ArrayList<>();
        keyword_f.add("病毒");
        keyword_f.add("生化");
        File fbs = new File(1,duty_bs1,keyword_bs1); //基础集文件
        bs.add(fbs);

        u.setId(1);
        u.setDutyList(duty_u);
        u.setBs(bs);
        u.setRiskquota(5);

        f1.setId(2);
        f1.setDutyList(duty_f);
        f1.setKeywordList(keyword_f);

        ArrayList<Duty> duty_f2 = new ArrayList<>();
        duty_f2.add(d4);
        ArrayList<String> keyword_f2 = new ArrayList<>();
        keyword_f2.add("科技");
        f2.setId(3);
        f2.setDutyList(duty_f2);
        f2.setKeywordList(keyword_f2);

        //System.out.println(f.getDutyList().get(0).getkeywordList());

    }
    @org.junit.Test
    public void test_run(){
        User u =new User();
        File f1 = new File();
        File f2 = new File();
        Test test = new Test();
        test.init(u,f1,f2);
        Rcbac rcbac = new Rcbac();
        rcbac.Overall(u,f1); //访问文件2
        System.out.println();
        rcbac.Overall(u,f2); //访问文件3
    }
}
