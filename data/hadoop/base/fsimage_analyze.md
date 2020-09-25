<span id="catalog"></span>

<span style='font-size:18px'>目录---FsImage、Edits解析示例</span>

- [准备镜像文件需要执行的操作](#准备镜像文件需要执行的操作)
- [fsimage分析](#fsimage分析)
- [](#)

# 准备镜像文件需要执行的操作
[top](#catalog)
1. 初始化 NN
2. 执行 hdfs 操作
    1. 创建目录: `hdfs dfs -mkdir -p /user/input`
    2. 上传数据: `hdfs dfs -put /opt/software/hadoop-2.7.2.tar.gz /user/input/`

# fsimage分析
[top](#catalog)
- 检查已经生成的 fsimage
    ```sh
    [hduser@hd02 hadoop-2.7.2]$ ll data/tmp/dfs/name/current/
    total 1052
    -rw-rw-r--. 1 hduser hduser     194 Sep  5 21:30 edits_0000000000000000001-0000000000000000004
    -rw-rw-r--. 1 hduser hduser 1048576 Sep  5 21:30 edits_inprogress_0000000000000000005
    -rw-rw-r--. 1 hduser hduser     353 Sep  5 21:28 fsimage_0000000000000000000
    -rw-rw-r--. 1 hduser hduser      62 Sep  5 21:28 fsimage_0000000000000000000.md5
    -rw-rw-r--. 1 hduser hduser     494 Sep  5 21:30 fsimage_0000000000000000004
    -rw-rw-r--. 1 hduser hduser      62 Sep  5 21:30 fsimage_0000000000000000004.md5
    -rw-rw-r--. 1 hduser hduser       2 Sep  5 21:30 seen_txid
    -rw-rw-r--. 1 hduser hduser     207 Sep  5 21:28 VERSION
    [hduser@hd02 hadoop-2.7.2]$
    ```
- 将生成的 fsimage 转换成xml
    ```
    hdfs oiv -p XML -i data/tmp/dfs/name/current/fsimage_0000000000000000004 -o fsimage.xml
    ```
- 参考结果
    - [src/fsimage_analyze/oiv.xml](src/fsimage_analyze/oiv.xml)
- xml内容
    ```xml
    <?xml version="1.0"?>
    <fsimage>
        <NameSection>
            <genstampV1>1000</genstampV1>
            <genstampV2>1000</genstampV2>
            <genstampV1Limit>0</genstampV1Limit>
            <lastAllocatedBlockId>1073741824</lastAllocatedBlockId>
            <txid>4</txid>
        </NameSection>
        <INodeSection>
            <lastInodeId>16387</lastInodeId>
            <inode>
                <id>16385</id>
                <type>DIRECTORY</type>
                <name></name>
                <mtime>1599312606428</mtime>
                <permission>hduser:supergroup:rwxr-xr-x</permission>
                <nsquota>9223372036854775807</nsquota>
                <dsquota>-1</dsquota>
            </inode>
            <inode>
                <id>16386</id>
                <type>DIRECTORY</type>
                <name>user</name>
                <mtime>1599312606453</mtime>
                <permission>hduser:supergroup:rwxr-xr-x</permission>
                <nsquota>-1</nsquota>
                <dsquota>-1</dsquota>
            </inode>
            <inode>
                <id>16387</id>
                <type>DIRECTORY</type>
                <name>input</name>
                <mtime>1599312606453</mtime>
                <permission>hduser:supergroup:rwxr-xr-x</permission>
                <nsquota>-1</nsquota>
                <dsquota>-1</dsquota>
            </inode>
        </INodeSection>
        <INodeReferenceSection></INodeReferenceSection>
        <SnapshotSection>
            <snapshotCounter>0</snapshotCounter>
        </SnapshotSection>
        <INodeDirectorySection>
            <directory>
                <parent>16385</parent>
                <inode>16386</inode>
            </directory>
            <directory>
                <parent>16386</parent>
                <inode>16387</inode>
            </directory>
        </INodeDirectorySection>
        <FileUnderConstructionSection></FileUnderConstructionSection>
        <SnapshotDiffSection>
            <diff>
                <inodeid>16385</inodeid>
            </diff>
        </SnapshotDiffSection>
        <SecretManagerSection>
            <currentId>0</currentId>
            <tokenSequenceNumber>0</tokenSequenceNumber>
        </SecretManagerSection>
        <CacheManagerSection>
            <nextDirectiveId>1</nextDirectiveId>
        </CacheManagerSection>
    </fsimage>
    ```

# Edits分析
[top](#catalog)
- 检查已经生成的 edits
    ```sh
    [hduser@hd02 hadoop-2.7.2]$ ll data/tmp/dfs/name/current/
    total 1052
    -rw-rw-r--. 1 hduser hduser     194 Sep  5 21:30 edits_0000000000000000001-0000000000000000004
    #有一部分在 2NN上，需要等一会合并后再输出
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    -rw-rw-r--. 1 hduser hduser 1048576 Sep  5 21:30 edits_inprogress_0000000000000000005
    -rw-rw-r--. 1 hduser hduser     353 Sep  5 21:28 fsimage_0000000000000000000
    -rw-rw-r--. 1 hduser hduser      62 Sep  5 21:28 fsimage_0000000000000000000.md5
    -rw-rw-r--. 1 hduser hduser     494 Sep  5 21:30 fsimage_0000000000000000004
    -rw-rw-r--. 1 hduser hduser      62 Sep  5 21:30 fsimage_0000000000000000004.md5
    -rw-rw-r--. 1 hduser hduser       2 Sep  5 21:30 seen_txid
    -rw-rw-r--. 1 hduser hduser     207 Sep  5 21:28 VERSION
    ```
- 将 edits 转换成 xml
    ```
    hdfs oev -p XML -i data/tmp/dfs/name/current/edits_inprogress_0000000000000000005 -o edits.xml
    ```
- 参考结果
    - [src/fsimage_analyze/oev.xml](src/fsimage_analyze/oev.xml)
- xml内容
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <EDITS>
        <EDITS_VERSION>-63</EDITS_VERSION>
        <RECORD>
            <OPCODE>OP_START_LOG_SEGMENT</OPCODE>
            <DATA>
                <TXID>5</TXID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_ADD</OPCODE>
            <DATA>
                <TXID>6</TXID>
                <LENGTH>0</LENGTH>
                <INODEID>16388</INODEID>
                <PATH>/user/input/hadoop-2.7.2.tar.gz._COPYING_</PATH>
                <REPLICATION>3</REPLICATION>
                <MTIME>1599312651123</MTIME>
                <ATIME>1599312651123</ATIME>
                <BLOCKSIZE>134217728</BLOCKSIZE>
                <CLIENT_NAME>DFSClient_NONMAPREDUCE_-1594485523_1</CLIENT_NAME>
                <CLIENT_MACHINE>192.168.226.102</CLIENT_MACHINE>
                <OVERWRITE>true</OVERWRITE>
                <PERMISSION_STATUS>
                    <USERNAME>hduser</USERNAME>
                    <GROUPNAME>supergroup</GROUPNAME>
                    <MODE>420</MODE>
                </PERMISSION_STATUS>
                <RPC_CLIENTID>38ec4ecf-7744-4834-87ea-dff5c3ab79d7</RPC_CLIENTID>
                <RPC_CALLID>3</RPC_CALLID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_ALLOCATE_BLOCK_ID</OPCODE>
            <DATA>
                <TXID>7</TXID>
                <BLOCK_ID>1073741825</BLOCK_ID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_SET_GENSTAMP_V2</OPCODE>
            <DATA>
                <TXID>8</TXID>
                <GENSTAMPV2>1001</GENSTAMPV2>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_ADD_BLOCK</OPCODE>
            <DATA>
                <TXID>9</TXID>
                <PATH>/user/input/hadoop-2.7.2.tar.gz._COPYING_</PATH>
                <BLOCK>
                    <BLOCK_ID>1073741825</BLOCK_ID>
                    <NUM_BYTES>0</NUM_BYTES>
                    <GENSTAMP>1001</GENSTAMP>
                </BLOCK>
                <RPC_CLIENTID></RPC_CLIENTID>
                <RPC_CALLID>-2</RPC_CALLID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_ALLOCATE_BLOCK_ID</OPCODE>
            <DATA>
                <TXID>10</TXID>
                <BLOCK_ID>1073741826</BLOCK_ID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_SET_GENSTAMP_V2</OPCODE>
            <DATA>
                <TXID>11</TXID>
                <GENSTAMPV2>1002</GENSTAMPV2>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_ADD_BLOCK</OPCODE>
            <DATA>
                <TXID>12</TXID>
                <PATH>/user/input/hadoop-2.7.2.tar.gz._COPYING_</PATH>
                <BLOCK>
                    <BLOCK_ID>1073741825</BLOCK_ID>
                    <NUM_BYTES>134217728</NUM_BYTES>
                    <GENSTAMP>1001</GENSTAMP>
                </BLOCK>
                <BLOCK>
                    <BLOCK_ID>1073741826</BLOCK_ID>
                    <NUM_BYTES>0</NUM_BYTES>
                    <GENSTAMP>1002</GENSTAMP>
                </BLOCK>
                <RPC_CLIENTID></RPC_CLIENTID>
                <RPC_CALLID>-2</RPC_CALLID>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_CLOSE</OPCODE>
            <DATA>
                <TXID>13</TXID>
                <LENGTH>0</LENGTH>
                <INODEID>0</INODEID>
                <PATH>/user/input/hadoop-2.7.2.tar.gz._COPYING_</PATH>
                <REPLICATION>3</REPLICATION>
                <MTIME>1599312657395</MTIME>
                <ATIME>1599312651123</ATIME>
                <BLOCKSIZE>134217728</BLOCKSIZE>
                <CLIENT_NAME></CLIENT_NAME>
                <CLIENT_MACHINE></CLIENT_MACHINE>
                <OVERWRITE>false</OVERWRITE>
                <BLOCK>
                    <BLOCK_ID>1073741825</BLOCK_ID>
                    <NUM_BYTES>134217728</NUM_BYTES>
                    <GENSTAMP>1001</GENSTAMP>
                </BLOCK>
                <BLOCK>
                    <BLOCK_ID>1073741826</BLOCK_ID>
                    <NUM_BYTES>63439959</NUM_BYTES>
                    <GENSTAMP>1002</GENSTAMP>
                </BLOCK>
                <PERMISSION_STATUS>
                    <USERNAME>hduser</USERNAME>
                    <GROUPNAME>supergroup</GROUPNAME>
                    <MODE>420</MODE>
                </PERMISSION_STATUS>
            </DATA>
        </RECORD>
        <RECORD>
            <OPCODE>OP_RENAME_OLD</OPCODE>
            <DATA>
                <TXID>14</TXID>
                <LENGTH>0</LENGTH>
                <SRC>/user/input/hadoop-2.7.2.tar.gz._COPYING_</SRC>
                <DST>/user/input/hadoop-2.7.2.tar.gz</DST>
                <TIMESTAMP>1599312657407</TIMESTAMP>
                <RPC_CLIENTID>38ec4ecf-7744-4834-87ea-dff5c3ab79d7</RPC_CLIENTID>
                <RPC_CALLID>9</RPC_CALLID>
            </DATA>
        </RECORD>
    </EDITS>
    ```
