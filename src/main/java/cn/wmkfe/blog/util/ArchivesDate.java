package cn.wmkfe.blog.util;

import cn.wmkfe.blog.model.Archives;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArchivesDate {
    public static List<Archives> dateList(){
        List<Archives> list=new ArrayList<>();
        for (int i=0;i<6;i++){
            LocalDate date = LocalDate.now();
            Archives archives=new Archives();
            date = date.minusMonths(i);
            String CNFormat = date.format(DateTimeFormatter.ofPattern("yyyy 年 MM 月"));
            archives.setCNFormat(CNFormat);

            String slashFormat= date.format(DateTimeFormatter.ofPattern("yyyy/MM"));
            archives.setSlashFormat(slashFormat);
            list.add(archives);
        }
        return list;
    }
}
