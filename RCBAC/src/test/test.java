package test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

public class test {
    @Test
    public void testjson(){
        File file = new File("D:\\item_dedup.json");
        String f1 = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSON.parseObject(f1);
        JSONArray features = jsonObject.getJSONArray("features");

    }
}
