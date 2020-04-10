package RCBAC;

import Entity.Duty;
import Entity.File;
import Entity.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Overall {

    public static boolean rcbac(){ //rcbac执行程序
        //计算行为风险
        //计算历史风险
        //授权评估
        return false;
    }

    public double BRC(User u, File f){  //行为风险计算
        double RiskB=0;
        double RiskBD=0;
        double RiskBC=0;
        ArrayList<Duty> du = new ArrayList<>();
        ArrayList<Duty> df = new ArrayList<>();
        du=u.getDutyList();
        df=f.getDutyList();
        if(!du.retainAll(df)){ //职责有交集
            return RiskB;    //无风险，返回值为0
        }
        //两层循环，遍历获得RMAT关系矩阵,求用户职责和目标文件职责的相关性的最大值
        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<String> union = new ArrayList<>();//并集
        ArrayList<String> intersection = new ArrayList<>();//交集
        double r=0;//杰卡德系数
        double temp_r=0;
        for(Duty duty_u:u.getDutyList()){ //遍历用户职责
            for(Duty duty_f:f.getDutyList()){  //遍历目标文件职责
                temp2 = duty_f.getkeywordList();
                //求并集（去重)
                temp1 = duty_u.getkeywordList();
                //temp2 = duty_f.getkeywordList();
                temp1.retainAll(temp2);
                temp1.addAll(temp2);
                union=temp1;
                System.out.println(union);
                //求交集
                temp1 = duty_u.getkeywordList();
                //temp2 = duty_f.getkeywordList();
                temp1.retainAll(temp2);
                intersection = temp1;
                System.out.println(intersection);
                //求杰卡德系数,并取最大值
                temp_r = intersection.size()/union.size();
                if(temp_r > r){
                    r = temp_r;
                }
            }
        }
        RiskBD=1-r; //得职责风险
        //循环遍历用户基础集中文件和目标文件的关键字获得内容相关性，取最大值
        r=0;
        temp2 = f.getKeywordList();
        for(File bf:u.getBs()){
            //求并集（去重）
            temp1 = bf.getKeywordList();
            temp1.retainAll(temp2);
            temp1.addAll(temp2);
            union = temp1;
            System.out.println(union);
            //求交集
            temp1 = bf.getKeywordList();
            temp1.retainAll(temp2);
            intersection = temp1;
            //求杰卡德系数，并取最大值
            temp_r = intersection.size()/union.size();
            if(temp_r > r){
                r = temp_r;
            }
            RiskBC=1-r;//得内容风险
            RiskB=RiskBD*RiskBC;//得行为风险值
        }
        return RiskB;
    }

    public double HRC(User u,File f){

        return 0;
    }

    public static void main(String[] args){

        if (rcbac())
        {
            System.out.println("授权成功");
        }else{
            System.out.println("授权失败");
        }
    }
}
