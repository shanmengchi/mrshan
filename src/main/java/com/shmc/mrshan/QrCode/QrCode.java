package com.shmc.mrshan.QrCode;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shmc.mrshan.utils.SendSignHttpsClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class QrCode {
    public static void main(String[] args) throws URISyntaxException, IOException {
        //获取数据
        List<QrCodeEnity> list = new ArrayList<>();
        JSONObject paramArr = getParam();

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("async-visual-");
        threadPoolTaskExecutor.setCorePoolSize(30);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        //安全关闭线程池
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //设置等待时间120s
        threadPoolTaskExecutor.setAwaitTerminationSeconds(120);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.afterPropertiesSet();



        long startTime = System.currentTimeMillis();
        List<Future> listF = new ArrayList<>();

        for (Map.Entry<String,Object> entry : paramArr.entrySet()) {

            Future<?> submit = threadPoolTaskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = new JSONObject();
                    object.put("qrcode", entry.getKey());
                    object.put("pageNum", 1);
                    object.put("pageSize", 100);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = SendSignHttpsClient.doPostForJson("http://221.226.145.180:8084/myServlet", object);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jsonObject);

                    JSONArray deviceInfoList = jsonObject.getJSONArray("deviceInfoList");
                    for (int i = 0; i < deviceInfoList.size(); i++) {
                        JSONObject jsonObject1 = deviceInfoList.getJSONObject(i);
                        QrCodeEnity qrCodeEnity = new QrCodeEnity();
                        qrCodeEnity.setQrcode(object.getString("qrcode"));

                        qrCodeEnity.setDeptId(jsonObject1.getString("deptId"));
                        qrCodeEnity.setRemark(jsonObject1.getString("remark"));
                        qrCodeEnity.setDeviceid(jsonObject1.getString("deviceid"));
                        qrCodeEnity.setDevicetype(jsonObject1.getString("devicetype"));
                        qrCodeEnity.setRiskType(jsonObject1.getString("riskType"));
                        qrCodeEnity.setDeptname(jsonObject1.getString("DEPTNAME"));
                        qrCodeEnity.setDevicename(jsonObject1.getString("devicename"));
                        qrCodeEnity.setPosition(jsonObject1.getString("position"));
                        qrCodeEnity.setResponsible_org(jsonObject1.getString("responsible_org"));
                        list.add(qrCodeEnity);
                    }
                }
            });
            listF.add(submit);

        }

        while(true) {
            if(isDone(listF)) {
                break;
            }
            System.out.println("=====================================主线程等待...");
            try {
                Thread.sleep(5000);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("用时========================"+(endTime-startTime)/1000+"秒");

        //将数据导出到Excel
        //参数1:ExportParams对象 参数2:导出的类型  参数3:导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("二维码数据", "二维码数据"), QrCodeEnity.class, list);
        //使用流将excel写入到指定的位置
        FileOutputStream outputStream = new FileOutputStream("C:/Users/shmc/Desktop/QrCode.xls");//指定写出的位置
        workbook.write(outputStream);//将数据输出
        //关闭流
        workbook.close();
        outputStream.close();

        threadPoolTaskExecutor.shutdown();

    }

    public static boolean isDone(List<Future> listF){
        boolean flag = true;
        for (Future future : listF) {
            if(!future.isDone()){
                flag = false;
            }
        }
        return flag;
    }
    public static JSONObject getParam(){
        
        JSONObject object = new JSONObject();
        object.put("YGJT11501001","应天大街隧道管理中心");
        object.put("YGJT11501002","应天大街隧道应急救援基地");
        object.put("YGJT11501003","应天大街隧道江北工作井");
        object.put("YGJT11501004","应天大街隧道江南工作井");
        object.put("YGJT11501005","应天大街隧道夹江桥");
        object.put("YGJT11501006","应天大街隧道梅子洲风塔");
        object.put("YGJT11501007","应天大街隧道管养车辆");
        object.put("YGJT11501008","应天大街隧道清排障车辆");
        object.put("YGJT11501009","应天大街隧道巡视车辆");
        object.put("YGJT11501010","定淮门隧道隧道管理中心");
        object.put("YGJT11501011","定淮门隧道江南风塔");
        object.put("YGJT11501012","定淮门隧道江北风塔");
        object.put("YGJT11501013","定淮门隧道梅子洲风塔");
        object.put("YGJT11501014","定淮门隧道行车层");
        object.put("YGJT11501015","定淮门隧道管养车辆");
        object.put("YGJT11501016","定淮门隧道清排障车辆");
        object.put("YGJT11501017","定淮门隧道巡视车辆");
        object.put("YGJT11501018","公建中心区域");
        object.put("YGJT11501019","江心洲大桥江北工区");
        object.put("YGJT11501020","江心洲大桥梅子洲风塔");
        object.put("YGJT11501021","江心洲大桥江南风塔");
        object.put("YGJT11501022","江心洲大桥");
        object.put("YGJT11501023","夹江隧道及青奥轴线隧道");
        object.put("YGJT11501024","江心洲大桥管养车辆");
        object.put("YGJT11501025","江心洲大桥清排障车辆");
        object.put("YGJT11501026","江心洲大桥巡视车辆");

        return object;
    }
}
