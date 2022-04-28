---
layout: post
title:  "Ford-Fulkerson"
date:   2022-04-27 20:12:04 +0900
categories: jekyll update
---





# Ford-Fulkerson 활용 문제

한 농사꾼은 소들이 충분한 물을 마시길 원했다. 그래서 농장에 우물에서 외양간을 잇는 N개의 배수관의 지도를 만들기로 했다. 존은 아주 다양한 크기의 배수관들이 완전히 우연한 방법으로 연결돼있음을 알았다. 농사꾼은 파이프를 통과하는 유량을 계산하고 싶다.

두개의 배수관이 한줄로 연결 돼 있을 때 두 관의 유량 중 최솟값으로 흐르게 된다. 예를 들어 용량이 5인 파이프가 용량이 3인 파이프와 연결되면 한개의 용량 3짜리 파이프가 된다.

![problem1](https://user-images.githubusercontent.com/63089470/165748320-54a1faa5-f610-48ad-b098-aebc7ba04079.jpg)

게다가, 병렬로 연결돼 있는 배수관들은 각 용량의 합만큼의 물을 보낼 수 있다.

![problem2](https://user-images.githubusercontent.com/63089470/165749179-b982ba88-8f99-437c-b206-de012128fa24.jpg)

마지막으로, 어떤 것에도 연결돼 있지 않은 파이프는 물을 흐르게 하지 못하므로 제거된다.

![problem3](https://user-images.githubusercontent.com/63089470/165749350-eb548a63-add8-4ddf-93c4-b726aedb95ec.jpg)

이로 인해 복잡하게 연결된 모든 배수관들은 한개의 최대 유량을 갖는 배수관으로 만들어진다.

주어진 파이프들의 맵으로부터 우물(A)와 외양간(Z) 사이의 유량을 결정하자.

각 노드의 이름은 알파벳으로 지어져 있다.

![problem4](https://user-images.githubusercontent.com/63089470/165749582-502fcde4-b711-468c-b903-bb529ef07319.jpg)

파이프 BC와 CD는 합쳐질 수 있다.

![problem5](https://user-images.githubusercontent.com/63089470/165749827-71b013cc-8ad1-4154-986e-287189f6fef1.jpg)

그러면 BD와 DZ 역시 합쳐질 수 있다.

![problem6](https://user-images.githubusercontent.com/63089470/165750050-4c3db0d9-8b99-4da4-b935-3ae4c706a750.jpg)

병렬 연결된 BZ 역시 합쳐진다.

![problem7](https://user-images.githubusercontent.com/63089470/165750053-15d98564-2587-43b5-b19e-61079ad795e9.jpg)

그러면 AB와 BZ 역시 합쳐질 수 있고 용량 3인 배수관 하나가 만들어진다.

![problem8](https://user-images.githubusercontent.com/63089470/165750054-496482f6-9cb0-4df6-870a-421785f65462.jpg)

위의 내용들에 대한 코딩을 작성해보았다.

```
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
```

※ 결과

```
5
A B 3
B C 3
C D 5
D Z 4
B Z 6
A->B 간선의 유량은 3
B->C 간선의 유량은 3
C->D 간선의 유량은 5
D->Z 간선의 유량은 4
B->Z 간선의 유량은 6

A->B로 흐른 유량: 3
B->Z로 흐른 유량: 3
누적 유량은 3

```


---