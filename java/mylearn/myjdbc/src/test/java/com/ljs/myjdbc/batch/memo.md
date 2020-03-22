批量插入的测试
- connection.setAutoCommit(false);，批量插入前，取消自动commit
- addBatch()，在批量执行的范围内，保存sql而不执行
- executeBatch()，达到保存的阀值是，批量执行
- clearBatch()，执行之后，将保存的sql全部清空
- connection.commit();，全部sql执行完成之后，commit