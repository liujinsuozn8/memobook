package com.ljs.myjdbc.blob;

import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class blobTest {
    // 插入blob字段
    @Test
    public void insertBlobTest() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers (name, email, birth, photo) values(?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, "blobTest");
        ps.setObject(2, "test@com");
        ps.setObject(3, "1999-10-10");
        FileInputStream is = new FileInputStream("resource/blobTest.png");
        ps.setBlob(4, is);
        ps.execute();

        JDBCUtils.closeResources(connection, ps);
    }

    //查询blob字段
    @Test
    public void selectBlobTest() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select photo from customers where name = 'blobTest'";
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(1);
                // 下载文件并保存到本地
                is = blob.getBinaryStream();
                fos = new FileOutputStream("resource/blobTestDownload.png");
                byte[] bs = new byte[1024];
                int len;
                while ((len = is.read(bs)) != -1) {
                    fos.write(bs, 0, len);
                }
                fos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JDBCUtils.closeResources(connection, ps, resultSet);
        }
    }
}
