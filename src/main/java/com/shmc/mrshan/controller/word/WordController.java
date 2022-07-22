package com.shmc.mrshan.controller.word;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.shmc.mrshan.utils.SendSignHttpsClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word")
public class WordController {
    @GetMapping("/word")
    public void word (String ticket, HttpServletResponse response) throws IOException {
        //        http://218.2.99.139:17601/v2/api-docs
        String result = SendSignHttpsClient.get("http://218.2.99.139:17601/v2/api-docs");

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject enityObj = jsonObject.getJSONObject("definitions");
        JSONObject pathsObj = jsonObject.getJSONObject("paths");
        JSONArray tagsArr = jsonObject.getJSONArray("tags");

        List<Map> listMax = new ArrayList<>();


        for (int i = 0; i < tagsArr.size(); i++) {
            Map map = new HashMap();

            List<Map> list = new ArrayList<>();
            JSONObject arrObject = tagsArr.getJSONObject(i);
            String tag = arrObject.getString("name");

            //存放当前tag下所有接口
            JSONObject tagsObj = new JSONObject();
            pathsObj.forEach((key, value) -> {
                JSONObject get = ((JSONObject)value).getJSONObject("get");
                JSONObject post = ((JSONObject)value).getJSONObject("post");
                JSONObject method = (get==null || get.isEmpty() ? post : get);

                String tags = method.getJSONArray("tags").get(0).toString();
                if(tags==null){

                }else if(tag.equals(tags)){
                    tagsObj.put(key,value);
                }
            });
            getQuaryTable(enityObj, tagsObj, list);
            map.put("item",list);
            map.put("maxtitle",tag);

            listMax.add(map);
        }


        Map finalmap = new HashMap();
        finalmap.put("maxitem",listMax);

        Resource resource = new ClassPathResource("");
        System.out.println(resource.getFile().getAbsolutePath());

        XWPFTemplate template = XWPFTemplate.compile(resource.getFile().getAbsolutePath()+"/word/template.docx")
                .render(finalmap);
//        FileOutputStream out;
//        out = new FileOutputStream("D:/workspace/MyTestRepository/trunk/src/main/resources/word/template3.docx");
        // 设置响应输出的头类型及下载文件的默认名称
        String fileName = new String("template3.docx".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/ms-word;charset=gb2312");
        // 解决：excel文件下载成功后打开文件遇到错误的问题
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        template.write(baos);
        response.setHeader("Content-Length", String.valueOf(baos.size()));
        OutputStream out = response.getOutputStream();
        out.write(baos.toByteArray());
        out.flush();
        out.close();
        template.close();
    }
    private static void getQuaryTable(JSONObject enityObj, JSONObject pathsObj, List<Map> list) {
        pathsObj.forEach((key, value) -> {
            Map<String, Object> data = new HashMap<>();

            JSONObject get = ((JSONObject)value).getJSONObject("get");
            JSONObject post = ((JSONObject)value).getJSONObject("post");
            JSONObject method = (get==null || get.isEmpty() ? post : get);
            data.put("title", method.getString("description"));
            data.put("url", key);
            data.put("menthod", (get==null || get.isEmpty() ) ? "post" : "get");

            //参数请求表格
            RowRenderData header = RowRenderData.build(new TextRenderData("000000", "参数"), new TextRenderData("000000", "名称"), new TextRenderData("000000", "类型"), new TextRenderData("000000", "是否必传"), new TextRenderData("000000", "说明"));
            List<RowRenderData> rows = new ArrayList<>();
            //接口返回表格
            RowRenderData headerReturnTable = RowRenderData.build(new TextRenderData("000000", "参数"), new TextRenderData("000000", "名称"), new TextRenderData("000000", "类型"),  new TextRenderData("000000", "说明"));
            List<RowRenderData> rowsReturnTable = new ArrayList<>();

            //解析输入参数
            JSONArray parametersArr = method.getJSONArray("parameters");
            for (int i = 0; i < parametersArr.size(); i++) {
                JSONObject param = parametersArr.getJSONObject(i);
                if(param.getString("in")==null){
                    System.out.println(param);
                    rows.add(RowRenderData.build(param.getString("name"), param.getString("description"),param.getString("type"),param.getBoolean("required")?"是":"否",""));
                    continue;
                }
                switch (param.getString("in")){
                    case "header":
                        rows.add(RowRenderData.build(param.getString("name"), param.getString("description"),param.getString("type"),param.getBoolean("required")?"是":"否",""));
                        break;
                    case "body":
//                        standardBudgetItem items
                        JSONObject params = null;
                        if(param.getJSONObject("schema").getJSONObject("items")==null){
                            if(param.getJSONObject("schema")==null){
                                System.out.println(param);
                            }
                            if(param.getJSONObject("schema").getString("originalRef")==null){
                                System.out.println(param);
                            }
                            if(enityObj.getJSONObject(param.getJSONObject("schema").getString("originalRef"))==null){
                                System.out.println(param);
                                rows.add(RowRenderData.build(param.getString("name"), param.getString("description"),param.getString("type"),param.getBoolean("required")?"是":"否",""));
                                break;
                            }
                            params = enityObj.getJSONObject(param.getJSONObject("schema").getString("originalRef")).getJSONObject("properties");
                        }else{
                            params = enityObj.getJSONObject(param.getJSONObject("schema").getJSONObject("items").getString("originalRef")).getJSONObject("properties");
                        }
                        params.forEach((key1, value1) -> {
                            JSONObject parseObject = ((JSONObject)value1);
                            //TODO 是否必填
                            rows.add(RowRenderData.build(key1,parseObject.getString("description"),parseObject.getString("type"),"",""));
                        });
                        break;
                    case "query":
                        rows.add(RowRenderData.build(param.getString("name"), param.getString("description"),param.getString("type"),param.getBoolean("required")?"是":"否",""));
                        break;
                }
            }
            //解析输出参数表格
            JSONObject responsesObj = method.getJSONObject("responses");
            String enity = responsesObj.getJSONObject("200").getJSONObject("schema").getString("originalRef");
            if(enity == null){
                return;
            }
            JSONObject enityObjJSONObject = enityObj.getJSONObject(enity);
            if(enityObjJSONObject==null){
                System.out.println(enityObjJSONObject);
            }
            JSONObject properties = enityObjJSONObject.getJSONObject("properties");
            properties.forEach((key1, value1) -> {
                JSONObject value1Obj = (JSONObject)value1;
//                if(value1Obj.getString("type") == null){
//                    System.out.println(value1);
//                    return;
//                }

                if("/api/sys/standard/standardFireMaintenanceRecord/select".equals(key)){
                    System.out.println();
                }
                if(value1Obj.getString("originalRef")!=null){
                    //select
                    String childrenEnity = value1Obj.getString("originalRef");
                    JSONObject childEnityObjJSONObject = enityObj.getJSONObject(childrenEnity);
                    JSONObject childProperties = childEnityObjJSONObject.getJSONObject("properties");
                    childProperties.forEach((key2, value2) -> {
                        JSONObject childValueObj = (JSONObject)value2;
                        rowsReturnTable.add(RowRenderData.build(key1+"."+key2,childValueObj.getString("description"), childValueObj.getString("type"),""));
                    });

                }else if(value1Obj.getString("items")!=null){
                    //list page
                    String childrenEnity = value1Obj.getJSONObject("items").getString("originalRef");
                    JSONObject childEnityObjJSONObject = enityObj.getJSONObject(childrenEnity);
                    JSONObject childProperties = childEnityObjJSONObject.getJSONObject("properties");
                    childProperties.forEach((key2, value2) -> {
                        JSONObject childValueObj = (JSONObject)value2;
                        rowsReturnTable.add(RowRenderData.build(key1+"[0]."+key2,childValueObj.getString("description"), childValueObj.getString("type"),""));
                    });

                }else {
                    //普通属性
                    rowsReturnTable.add(RowRenderData.build(key1,value1Obj.getString("description"), value1Obj.getString("type"),""));
                }


//                switch(value1Obj.getString("type")){
//                    case "array":
//                        String childrenEnity = value1Obj.getJSONObject("items").getString("originalRef");
//                        JSONObject childEnityObjJSONObject = enityObj.getJSONObject(childrenEnity);
//                        JSONObject childProperties = childEnityObjJSONObject.getJSONObject("properties");
//                        childProperties.forEach((key2, value2) -> {
//                            JSONObject childValueObj = (JSONObject)value2;
//                            rowsReturnTable.add(RowRenderData.build(key1+"."+key2,childValueObj.getString("description"), childValueObj.getString("type"),""));
//                        });
//                        break;
////                    case "object":
////                        String childrenEnity1 = value1Obj.getJSONObject("properties").getString("originalRef");
////                        JSONObject childEnityObjJSONObject1 = enityObj.getJSONObject(childrenEnity1);
////                        JSONObject childProperties1 = childEnityObjJSONObject1.getJSONObject("properties");
////                        childProperties1.forEach((key2, value2) -> {
////                            JSONObject childValueObj = (JSONObject)value2;
////                            rowsReturnTable.add(RowRenderData.build(key1+"."+key2,childValueObj.getString("description"), childValueObj.getString("type"),""));
////                        });
////
////                        break;
//                    default:
//                        rowsReturnTable.add(RowRenderData.build(key1,value1Obj.getString("description"), value1Obj.getString("type"),""));
//                        break;
//                }

            });

            data.put("quarytable",new MiniTableRenderData(header,rows));
            data.put("returntable",new MiniTableRenderData(headerReturnTable,rowsReturnTable));
            list.add(data);
        });
    }


}
