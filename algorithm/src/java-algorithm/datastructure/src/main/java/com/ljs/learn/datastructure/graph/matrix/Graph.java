package com.ljs.learn.datastructure.graph.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    private ArrayList<String> vertexList; // 存储顶点集合
    private int[][] edges;  // 存储邻接矩阵
    private int numOfEdges; // 记录边的数量

    // -----------构造图结构----------------------------------------
    // 通过结点个数初始化图
    public Graph(int n){
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        // 初始化时不知道有多少条边，所以初始化为0
        numOfEdges = 0;
    }

    // 插入结点
    public void addVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 一个顶点对应的下标
     * @param v2 另一个顶点对应的下标
     * @param weight 表示边(的权值)
     */
    public void addEdge(int v1, int v2, int weight){
        edges[v1][v2]=weight;
        edges[v2][v1]=weight;
        numOfEdges++;
    }

    // ------------------------------------------------------------
    // -----------深度优先遍历----------------------------------------
    /**
     * 遍历矩阵的第 index 行，搜索第一个有效的邻接结点
     * @param index 邻接矩阵的行index
     * @return 如果存在则返回列 index， 如果不存在则返回 -1
     */
    public int getFirstNeighbor(int index){
        //
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0){
                return i;
            }
        }

        // 如果没有找到则返回 -1
        return -1;
    }

    /**
     * 搜索一个邻接结点的下一个邻接结点
     * @param lineIndex 邻接矩阵的行index
     * @param startIndex 搜索的起始邻接结点（应该从它的下一个开始搜索）
     * @return 下一个邻接结点的index，没有则返回 -1
     */
    public int getNextNeighbor(int lineIndex, int startIndex){
        // 从目标结点的下一个结点开始搜索
        for (int i = startIndex + 1; i < vertexList.size(); i++) {
            if (edges[lineIndex][i] > 0){
                return i;
            }
        }

        // 如果没有找到则返回 -1
        return -1;
    }

    /**
     * 深度优先遍历
     * @param isVisited 访问状态列表
     * @param lineIndex 从哪个结点开始遍历
     */
    public void dfs(boolean[] isVisited, int lineIndex){
        // 1. 访问当前结点
        System.out.println(getValueByIndex(lineIndex));

        // 2. 将当前结点设置为已访问
        isVisited[lineIndex] = true;

        // 3. 查找当前结点的第一个邻接结点
        int b = getFirstNeighbor(lineIndex);

        while (b != -1){
            // 4. 如果邻接结点存在，并且未被访问，则对该结点进行深度优先遍历
            if(!isVisited[b]){
                dfs(isVisited, b);
            }
            // 5. 如果 B 已被访问，则搜索B的下一个邻接结点
            b = getNextNeighbor(lineIndex, b);
        }
    }

    // 启动深度优先遍历
    public void dfs(){
        // 定义一个数组，记录某个结点是否被访问过
        boolean[] isVisited = new boolean[vertexList.size()];
        // 遍历每一个结点，并做深度优先遍历
        // 即：如果B不存在，则回到第1步，将从邻接矩阵中A的下一个结点D继续
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]){
                dfs(isVisited, i);
            }
        }
    }
    // ------------------------------------------------------------
    // -----------广度优先遍历----------------------------------------
    public void bfs(){
        // 初始化访问状态数组
        boolean[] isVisited = new boolean[vertexList.size()];

        // 1. 将第一个顶点放入队的队尾
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(0);
        isVisited[0] = true;

        // 2. 从第一个顶点开始遍历，直至遍历到队列为空
        int curNode;
        int[] curRow;
        while (!queue.isEmpty()){
            // 3. 从队列的对头弹出一个顶点A
            curNode = queue.poll();

            // 4. 输出该结点
            System.out.println(vertexList.get(curNode));
            // 应该在顶点被添加到队列时，设置为已访问
            // 防止多个结点与目标结点有关系，导致结点的重复访问
            // isVisited[i] = true;

            // 5. 顺次访问顶点A的邻接结点
            curRow = edges[curNode];
            for (int i = 0; i < curRow.length; i++) {
                // 6. 如果邻接结点没有被访问过，则将邻接结点添加到队列中
                // tips:邻接矩阵的每列也是结点的index，将列的index当作结点添加到队列
                if (curRow[i] == 1 && isVisited[i] == false){
                    queue.addLast(i);
                    // 7. 将该结点设置为已访问
                    isVisited[i] = true;
                }
            }
        }
    }

    //----————----————----————----————----————----————----——
    // 通用函数

    // 返回结点的个数
    public int getNumOfVertex(){
        return vertexList.size();
    }

    // 返回边的个数
    public int getNumOfEdges(){
        return numOfEdges;
    }

    // 返回结点i对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    // 返回边的权值
    public int getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    // 显示图对应的矩阵
    public void showGraph(){
        for (int[] row : edges) {
            System.out.println(Arrays.toString(row));
        }
    }
}
