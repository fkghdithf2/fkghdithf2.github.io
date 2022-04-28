import java.util.*;
import java.io.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[][] capacity, flow;

    public static int atoi(char c) {
        if ('A' <= c && c <= 'Z') return (c - 65);
        else if ('a' <= c && c <= 'z') return (c - 71);
        return 0;
    }

    // 시간 복잡도: O(V+E)F
    public static void main(String[] args) throws IOException {
        StringTokenizer token = new StringTokenizer(br.readLine());
        Stack<String> s = new Stack<>();

        // 그래프 생성
        capacity = new int[52][52];
        flow = new int[52][52];

        int n = Integer.parseInt(token.nextToken());
        for (int i = 0; i < n; i++) {
            token = new StringTokenizer(br.readLine());

            int start = atoi(token.nextToken().charAt(0));
            int dest = atoi(token.nextToken().charAt(0));
            int w = Integer.parseInt(token.nextToken());

            capacity[start][dest] += w;
            capacity[dest][start] += w;

            char c1 = (char) (start + 65);
            char c2 = (char) (dest + 65);
            System.out.println(c1 + "->" + c2 + " 간선의 유량은 " + w);
        }

        int total = 0;
        int src = 0, sink = 25;
        while (true) {
            int[] parent = new int[52];
            Queue<Integer> q = new PriorityQueue();
            for (int i = 0; i < 52; i++) parent[i] = -1;
            parent[src] = src;
            q.add(src);

            // BFS를 사용하여 유량의 경로를 탐색
            while (!q.isEmpty() && parent[sink] == -1) {
                int here = q.poll();
                for (int there = 0; there < 52; there++) {
                    if (capacity[here][there] - flow[here][there] > 0
                            && parent[there] == -1) {
                        q.add(there);
                        parent[there] = here;
                    }
                }
            }

            // 증가 경로가 없으면 종료
            if (parent[sink] == -1) break;

            // 증가 경로를 찾았으면 유량을 결정
            int amount = Integer.MAX_VALUE;
            for (int i = sink; i != src; i = parent[i]) {
                amount = Math.min(capacity[parent[i]][i] - flow[parent[i]][i], amount);
            }


            // 증가 경로로 유량을 보냄
            System.out.println();
            for (int i = sink; i != src; i = parent[i]) {
                flow[parent[i]][i] += amount;
                flow[i][parent[i]] -= amount;
                char c1 = (char) (parent[i] + 65);
                char c2 = (char) (i + 65);
                s.push(c1+"->"+c2+"로 흐른 유량: " +amount);
            }

            total += amount;
        }

        while (!s.empty())
            System.out.println(s.pop());
        bw.write("누적 유량은 " + total + "\n");
        bw.close();
    }
}