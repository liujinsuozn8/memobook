package com.ljs.learn.datastructure.graph.matrix;

import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {
    // 图构建测试
    @Test
    public void createGraph(){
        // 1. 定义顶点中的数据
        String vertexes[] = {"A", "B", "C", "D", "E", "F"};

        // 2. 使用顶点个数初始化图
        Graph graph = new Graph(vertexes.length);

        // 3. 添加结点
        for (String vertex : vertexes) {
            graph.addVertex(vertex);
        }

        // 4. 添加边
        graph.addEdge( 0, 1, 1);
        graph.addEdge( 0, 4, 1);
        graph.addEdge( 1, 4, 1);
        graph.addEdge( 1, 2, 1);
        graph.addEdge( 2, 5, 1);
        graph.addEdge( 3, 4, 1);
        graph.addEdge( 3, 5, 1);
        graph.addEdge( 4, 5, 1);

        // 5. 输出图
        graph.showGraph();

        // 输出:
        // [0, 1, 0, 0, 1, 0]
        // [1, 0, 1, 0, 1, 0]
        // [0, 1, 0, 0, 0, 1]
        // [0, 0, 0, 0, 1, 1]
        // [1, 1, 0, 1, 0, 1]
        // [0, 0, 1, 1, 1, 0]
    }

    // 深度优先遍历测试
    @Test
    public void dfs(){
        // 1. 定义顶点中的数据
        String vertexes[] = {"A", "B", "C", "D", "E", "F"};

        // 2. 使用顶点个数初始化图
        Graph graph = new Graph(vertexes.length);

        // 3. 添加结点
        for (String vertex : vertexes) {
            graph.addVertex(vertex);
        }

        // 4. 添加边
        graph.addEdge( 0, 1, 1);
        graph.addEdge( 0, 4, 1);
        graph.addEdge( 1, 4, 1);
        graph.addEdge( 1, 2, 1);
        graph.addEdge( 2, 5, 1);
        graph.addEdge( 3, 4, 1);
        graph.addEdge( 3, 5, 1);
        graph.addEdge( 4, 5, 1);

        graph.dfs();
    }

    // 广度优先遍历测试
    @Test
    public void bfs(){
        // 1. 定义顶点中的数据
        String vertexes[] = {"A", "B", "C", "D", "E", "F"};

        // 2. 使用顶点个数初始化图
        Graph graph = new Graph(vertexes.length);

        // 3. 添加结点
        for (String vertex : vertexes) {
            graph.addVertex(vertex);
        }

        // 4. 添加边
        graph.addEdge( 0, 1, 1);
        graph.addEdge( 0, 4, 1);
        graph.addEdge( 1, 4, 1);
        graph.addEdge( 1, 2, 1);
        graph.addEdge( 2, 5, 1);
        graph.addEdge( 3, 4, 1);
        graph.addEdge( 3, 5, 1);
        graph.addEdge( 4, 5, 1);

        graph.bfs();
    }
}