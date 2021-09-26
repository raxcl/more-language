package raxcl;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceWithView {
    public void buildMoreLanguage(String inputNeedTxt) {
        //获取第一列数据----多语言KEY
        String key = columnOne(inputNeedTxt);
        //获取第二列数据----多语言CODE
        List<String> codeList = columnTwo(inputNeedTxt);
        //获取第三列数据----语言
        String chinese = "zh_CN";
        String english = "en_US";
        //获取第四列数据----描述
        //中文
        List<String> describeChinese = columnFourChinese(inputNeedTxt);
        //英文
        List<String> describeEnglish = columnFourEnglish(inputNeedTxt);
        //执行多语言导出方法
        moreLanguageExport(key,codeList,chinese,english,describeChinese,describeEnglish);
    }

    private String columnOne(String inputNeedTxt) {
        //1. 定义匹配规则
        String ruleOne = "(?<=Code = ').*?(?=')";
        Pattern pattern = Pattern.compile(ruleOne);
        Matcher matcher = pattern.matcher(inputNeedTxt);
        matcher.find();
        return matcher.group();
    }

    private List<String> columnTwo(String inputNeedTxt) {
        //1. 定义匹配规则
        String ruleTwo = "(?<=Code}.).*?(?=`)";
        Pattern pattern = Pattern.compile(ruleTwo);
        Matcher matcher = pattern.matcher(inputNeedTxt);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    private List<String> columnFourChinese(String inputNeedTxt) {
        //1. 定义匹配规则
        String ruleFour = "(?<=.d\\(').*?(?=')";
        Pattern pattern = Pattern.compile(ruleFour);
        Matcher matcher = pattern.matcher(inputNeedTxt);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    private List<String> columnFourEnglish(String inputNeedTxt) {
        //1. 定义匹配规则
        String ruleFour = "(?<=model.).*?(?=`)";
        Pattern pattern = Pattern.compile(ruleFour);
        Matcher matcher = pattern.matcher(inputNeedTxt);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    private void moreLanguageExport(String key, List<String> codeList, String chinese, String english, List<String> describeChinese,List<String> describeEnglish) {
        String fileName = "D:\\IdeaProjects\\my\\more-language\\src\\main\\resources\\easyexcel.xlsx";
        String templateName = "src/main/resources/模板.xlsx";
        List<List<String>> list = new ArrayList<>();
        //按行排列数据

        for (int i = 0; i < codeList.size(); i++) {
            //写入中文描述行
            List<String> listColumn = new ArrayList<>();
            Collections.addAll(listColumn,key, codeList.get(i),chinese,describeChinese.get(i));
            list.add(listColumn);
            //写入英文描述行
            List<String> listColumnTwo = new ArrayList<>();
            Collections.addAll(listColumnTwo,key, codeList.get(i),english,describeEnglish.get(i));
            list.add(listColumnTwo);
        }
        EasyExcel.write(fileName).withTemplate(templateName).sheet("平台多语言导入").doWrite(list);
    }
}
