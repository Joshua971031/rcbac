package rcbac;

import entity.Duty;
import entity.File;
import entity.History;
import entity.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Rcbac {

    public void Overall(User u, File f) { //rcbac执行程序
        double RiskB;
        double RiskH;
        double Risk;
        System.out.println("用户"+u.getId()+"请求访问文件"+f.getId()+"。");
        System.out.println("进行访问行为风险评估：");
        RiskB = BRC(u, f);//计算行为风险
        System.out.println("进行历史风险评估：");
        RiskH = HRC(u, f, RiskB);//计算历史风险
        if (Authorization(RiskB, RiskH, u, 0.5)){
            //风险配额和管理授权
            System.out.println("授权成功！！！！");
        }else{
            System.out.println("授权失败。");
        }
    }

    public ArrayList<Duty> Union_duty(ArrayList<Duty> a1,ArrayList<Duty> a2){ //求并集
        a1.removeAll(a2);
        a1.addAll(a2);
        return a1;
    }

    public ArrayList<Duty> Union_duty2(ArrayList<Duty> a1,ArrayList<Duty> a2){ //求并集（不去重
        a1.addAll(a2);
        return a1;
    }


    public double BRC(User u, File f){  //行为风险计算
        double RiskB=0;
        double RiskBD=0;
        double RiskBC=0;
        ArrayList<Duty> du = new ArrayList<>();
        ArrayList<Duty> df = new ArrayList<>();
        du.addAll(u.getDutyList());
        df.addAll(f.getDutyList());
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
        DecimalFormat dF = new DecimalFormat("0.0000"); //设置小数位数
        for(Duty duty_u:u.getDutyList()){ //遍历用户职责
            for(Duty duty_f:f.getDutyList()){  //遍历目标文件职责
                temp2.addAll(duty_f.getkeywordList());
                //求并集（去重)
                temp1.addAll(duty_u.getkeywordList());
                temp1.removeAll(temp2);
                temp1.addAll(temp2);
                union.addAll(temp1);
                temp1.clear();
                //System.out.println(union+"1");
                //求交集
                temp1.addAll(duty_u.getkeywordList());
                temp1.retainAll(temp2);
                intersection.addAll(temp1);
                temp1.clear();temp2.clear();
                //System.out.println(intersection+"2");
                //求杰卡德系数,并取最大值
                temp_r = Double.valueOf(dF.format((float)intersection.size()/union.size()));
                if(temp_r > r){
                    r = temp_r;
                }
                union.clear();intersection.clear();
            }
        }
        RiskBD=1-r; //得职责风险
        System.out.println("RiskBD="+RiskBD);
        //循环遍历用户基础集中文件和目标文件的关键字获得内容相关性，取最大值
        r=0;
        temp2.addAll(f.getKeywordList());

        for(File bf:u.getBs()){
            //求并集（去重）
            temp1.addAll(bf.getKeywordList());
            temp1.removeAll(temp2);
            temp1.addAll(temp2);
            union.addAll(temp1);
            temp1.clear();
            //System.out.println(union+"3");
            //求交集
            temp1.addAll(bf.getKeywordList());
            temp1.retainAll(temp2);
            intersection.addAll(temp1);
            temp1.clear();
            //System.out.println(intersection+"4");
            //求杰卡德系数，并取最大值
            temp_r = Double.valueOf(dF.format((float)intersection.size()/union.size()));
            if(temp_r > r){
                r = temp_r;
            }
            union.clear();intersection.clear();
        }
        RiskBC=1-r;//得内容风险
        System.out.println("RiskBC="+RiskBC);
        RiskB=RiskBD*RiskBC;//得行为风险值
        System.out.println("访问行为风险值RiskB="+RiskB);
        return RiskB;
    }

    public static <T> List<List<T>> averageAssign(List<T> source,int n){  //等分List，用于设置滑动窗口
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n; //(先计算出余数)
        int number=source.size()/n; //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }



    public double HRC_T(User u,File f,double RiskB, int t){ //历史风险值计算，分时期
        //计算历史职责风险,用熵权法计算职责风险
        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<String> union = new ArrayList<>();//并集
        ArrayList<String> intersection = new ArrayList<>();//交集
        double RiskHD=0; //历史职责风险值
        double RiskHC=0; //历史内容风险值
        double RiskH=0; //历史风险值
        double r=0;//杰卡德系数
        double temp_r=0;
        double pi=0;   //di在窗口中出现几率
        double w=0;  //职责加权熵
        List<List<History>> hll = averageAssign(u.getHistoryList(),3);
        ArrayList<History> hl = new ArrayList<>();
        if(t==1){
            hl.addAll(hll.get(0));
        }else if (t==2){
            hl.addAll(hll.get(0));
            hl.addAll(hll.get(1));
        }else if (t==3){
            hl=u.getHistoryList();
        }
        DecimalFormat dF = new DecimalFormat("0.0000"); //设置小数位数
        ArrayList<Duty> DSW = new ArrayList<>(); //从滑动窗口中获得DSW，即所有出现的职责（去重）
        ArrayList<Duty> DO = new ArrayList<>();  //滑动窗口中出现的职责（带重复）
        for(History h:hl){
            DSW = Union_duty(DSW, h.getDutyList());
        }
        for(History h:hl){
            DO = Union_duty2(DO, h.getDutyList());
        }
        for(Duty di:DSW){
            for(Duty dj:u.getDutyList()){   //计算获得每一个W(u,di)
                temp2.addAll(di.getkeywordList());
                //求并集（去重)
                temp1.addAll(dj.getkeywordList());
                temp1.removeAll(temp2);
                temp1.addAll(temp2);
                union.addAll(temp1);
                temp1.clear();
                //System.out.println(union+"5");
                //求交集
                temp1.addAll(dj.getkeywordList());
                temp1.retainAll(temp2);
                intersection.addAll(temp1);
                temp1.clear();
                //System.out.println(intersection+"6");
                //求杰卡德系数,并取最大值
                temp_r = Double.valueOf(dF.format((float)intersection.size()/union.size()));
                if(temp_r > r){
                    r = temp_r;
                }
                union.clear();intersection.clear();
            }
            w = 1-r; //求得di的权
            //System.out.println("w="+w);
            int count = Collections.frequency(DO,di); //di在窗口中的出现次数
            pi=Double.valueOf(dF.format((float)count/ DO.size()));  //得di出现概率
            RiskHD+=Double.valueOf(dF.format((float)-w*pi*Math.log(pi)));   //累加计算得到历史职责风险值
        }
        System.out.println("RiskHD="+RiskHD);
        //计算历史内容风险
        for(History h:hl){
            RiskHC+=Double.valueOf(dF.format((float)h.getRiskB()/hl.size())); //计算得到历史行为风险值
        }
        System.out.println("RiskHC="+RiskHC);
        //计算历史风险
        double B=0.5;//设置比重值
        RiskH=B*RiskHD+(1-B)*RiskHC;
        System.out.println("RiskH="+RiskH);
        return RiskH;

    }

    public double HRC(User u,File f,double RiskB){  //历史风险值计算
        u.addHistory(f,RiskB);//将本次访问加入滑动窗口
        //计算历史职责风险,用熵权法计算职责风险
        DecimalFormat dF = new DecimalFormat("0.0000"); //设置小数位数
        System.out.println("近期历史风险为：");
        double RiskH1 = HRC_T(u,f,RiskB,1);
        System.out.println("中期历史风险为：");
        double RiskH2 = HRC_T(u,f,RiskB,2);
        System.out.println("全期历史风险为：");
        double RiskH3 = HRC_T(u,f,RiskB,3);
        System.out.println("最终历史风险值RiskH="+Double.valueOf(dF.format((float)(RiskH1+RiskH2+RiskH3)/3)));
        return Double.valueOf(dF.format((float)(RiskH1+RiskH2+RiskH3)/3));
    }

    public boolean Authorization(double RiskB, double RiskH, User u,double T){  //风险配额和管理，最终的授权
        double riskquota = u.getRiskquota();//获得用户的风险配额
        double A=0.5;  //设置权重系数
        DecimalFormat dF = new DecimalFormat("0.0000"); //设置小数位数
        double Risk = Double.valueOf(dF.format((float)A*RiskB+(1-A)*RiskH));//计算总风险值
        System.out.println("Risk="+Risk);
        if(Risk>T){  //风险值超过阈值
            riskquota-=Risk;
            u.setRiskquota(riskquota);
            System.out.println("用户剩余风险配额:"+riskquota);
            return false;  //拒绝授权
        }else if(riskquota>=Risk){ //风险值没超过阈值
            riskquota-=Risk;
            u.setRiskquota(riskquota);
            System.out.println("用户剩余风险配额:"+riskquota);
            return true;  //允许授权
        }else{
            System.out.println("用户剩余风险配额:"+riskquota+"配额不足");
            return false;
        }
    }

   /* public void init(User u , File f1, File f2){
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

    }*/


    /*public static void main(String[] args){
        User u =new User();
        File f1 = new File();
        File f2 = new File();
        Rcbac rcbac = new Rcbac();
        rcbac.init(u,f1,f2);
        rcbac.Overall(u,f1);
        rcbac.Overall(u,f2);
    }*/
}
