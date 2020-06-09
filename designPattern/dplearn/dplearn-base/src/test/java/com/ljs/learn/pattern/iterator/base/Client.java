package com.ljs.learn.pattern.iterator.base;

import com.ljs.learn.pattern.iterator.base.aggregate.College;
import com.ljs.learn.pattern.iterator.base.aggregate.ComputerCollege;
import com.ljs.learn.pattern.iterator.base.aggregate.InfoCollege;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Client {
    @Test
    public void test01(){
        // 创建学院和系
        ComputerCollege computerCollege = new ComputerCollege();
        computerCollege.addDepartment("Da", "Da");
        computerCollege.addDepartment("Db", "Db");
        computerCollege.addDepartment("Dc", "Dc");
        computerCollege.addDepartment("Dd", "Dd");

        InfoCollege infoCollege = new InfoCollege();
        infoCollege.addDepartment("ia", "ia");
        infoCollege.addDepartment("ib", "ib");
        infoCollege.addDepartment("ic", "ic");
        infoCollege.addDepartment("id", "id");

        // 创建学院集合
        List<College> cs = new ArrayList<>();
        cs.add(computerCollege);
        cs.add(infoCollege);

        // 创建输出器，并打印
        OutputImpl output = new OutputImpl(cs);
        output.printCollege();
    }
}
