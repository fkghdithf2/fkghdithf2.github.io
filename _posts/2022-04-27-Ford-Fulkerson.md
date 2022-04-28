---
layout: post
title:  "Ford-Fulkerson"
date:   2022-04-28 20:12:04 +0900
categories: jekyll update
---

# 네트워크 플로우

네트워크 플로우란 그래프에 용량이 존재하고, 시작하는 부분을 source 끝나는 부분을 sink라고 한다.

이때 source에서 sink로 최대한 많이 유량을 흘려내려면 어떻게 하는지를 다루는 문제를 네트워크 플로우라고 한다.

· 유량(flow) : 두 정점 사이에서 흐르는 양


· 용량(capacity) : 두 정점 사이에 최대로 흐를수 있는 양


· 소스(source) : 유량이 시작되는 정점. 보통 S로 많이 나타낸다


· 싱크(sink) : 유량이 도착하는 정점. 보통 T로 많이 나타낸다


· 증가경로(augmenting path) : 소스에서 싱크로 유량을 보내는 경로 


· 잔여용량(residual capacity) : 현재 흐를수 있는 유량. 즉 용량-유량을 의미한다.

간단한 예시로 알아보자.

![그림](https://user-images.githubusercontent.com/63089470/165753867-fd2c27a9-baa5-490a-bd50-4751c6f7c448.jpg)

이러한 방향 그래프가 있다. 간선이 나타내는 의미는 유량/용량을 나타내고 s에서 시작해 t로 끝나게 된다.

s에서 t로 위로 흘려 보낼 수 있는 걸 살펴보면 유량을 2 s->1 은, s->3은 3이라 2만큼 유량을 흘려 보낼 수 있다.

![그림2](https://user-images.githubusercontent.com/63089470/165754440-ec0e58f9-6d4c-452e-ad76-30a2b8ff2052.jpg)

위로 2를 흘려 보내었다.

![그림3](https://user-images.githubusercontent.com/63089470/165754447-3d84f391-3dbf-4231-b83b-89afa5f53c6e.jpg)

아래로도 2를 흘러 보내었다.

그럼 전체적으로 우린 s에서 t로 4를 흘려보내주게 되고 위 그래프에서는 최대 4를 유량을 흘러보내줄 수 있다 라고 한다.

이제 이걸 어떻게 해결하는지 알고리즘을 살펴보겠다.

들어가기에 앞서 네트워크 플로우가 성립하기 위해서는 몇가지 조건이 있다.

·  c(u,v) : capacity의 약자로 정점 u에서 v로 가는 간선의 용량

·  f(u,v) : flow의 약자로 정점 u에서 v로 실제 흐르는 유량이라고 쓸때 아래와 같은 3가지 조건을 만족해야만 한다.

1) 용량 제한 속성 f(u,v) <= c(u,v) : 
두 간선에서 흐르는 유량은 용량을 넘을 수 없다.

2) 유량의 대칭성 f(u,v) = -f(v,u) :
u->v로 10만큼 흐른다면 v->u엔 -10만큼 흐른다라 생각해도 된다.
즉 나중에 10만큼을 v->u로 흘러보내도 괜찮다 이렇게 생각할 수 있다.

3) 유량 보존의 법칙 ∑f(u,v)=0 :
source 와 sink를 제외하고는 각 정점에서 들어오는 유량과 나가는 유량이 일치해야한다.
2)의 대칭성 때문에 당연히 합하면 0이 되어야하고, source와 sink는 들어오고 나가는 부분이기때문에 이부분은 제외한다.

# Ford-Fulkerson 알고리즘 동작 과정

![포드1](https://user-images.githubusercontent.com/63089470/165756463-8f781327-c5f5-4d74-aefc-6b8dd67c1b8c.jpg)

간선 당 용량을 입력받고 초기 유량은 전부 0으로 초기화 되었다.

1번 노드를 소스, 4번 노드를 싱크로 두고 유량을 보내는 경로인 증가 경로를 찾아보겠다.

![포드2](https://user-images.githubusercontent.com/63089470/165756468-036a39ec-bf4b-4fb4-b733-34d43f5aaf9f.jpg)

소스부터 싱크까지 증가 경로를 찾아가보았다. (1-2-3-4)

증가 경로를 따라 흘려 보내는 유량은 경로 상 간선의 잔여 용량 최소치로 결정된다.

간서의 잔여 용량은 간선의 용량에서 유량을 뺀 값이다.

residual(1,2) = capacity(1,2) - flow(1,2) = 1 - 0 = 1

residual(2,3) = capacity(2,3) - flow(2,3) = 1 - 0 = 1

residual(3,4) = capacity(3,4) - flow(3,4) = 2 - 0 = 2

이 중에서 최소치인 1만큼 증가 경로를 타고 소스에서 싱크까지 흘려보낸다.

![포드3](https://user-images.githubusercontent.com/63089470/165756471-10c2e6a2-2de4-4056-b69f-0de2cae58c36.jpg)

소스부터 싱크까지 증가 경로 (1-3-4)

residual(1,3) = capacity(1,3) - flow(1,3) = 3 - 0 = 3


residual(3,4) = capacity(3,4) - flow(3,4) = 2 - 1 = 1

이 중에서 최소치인 1만큼 증가 경로를 타고 소스에서 싱크까지 흘려보낸다.

![포드4](https://user-images.githubusercontent.com/63089470/165756474-05d213e2-e64e-47e7-87cb-f07644812606.jpg)

소스부터 싱크까지 증가 경로를 찾아가보자.
소스에서 유량이 흐를 수 있는 경로는 (1-3)이 있지만
capacity(3,2) =0 , residual(3,4) = 0 이라서 증가 경로가 없을 것 같다. 정말 증가 경로는 더 이상 찾을 수 없는 것일까?

여기서 유량의 대칭성이 힘을 발휘한다. flow(3,2) = -flow(2,3) = -1

잔여 용량을 확인해보자. residual(3,2) = capacity(3,2) - flow(3,2) = 0 -(-1) = 1

용량 제한 속성을 보면 flow(3,2) <= capacity(3,2) 도 성립한다.

capacity(3,2) = 0 이기 때문에 유량을 흘려보낼 수 없을 것 같지만 flow(2,3) 와 flow(3,2)을 상쇄 시킨다면 어떨까.

![포드5](https://user-images.githubusercontent.com/63089470/165756478-f52fd793-7d2c-4cef-ab48-7b4958b59fcf.jpg)

네트워크 유량의 법칙을 만족시키면서 증가 경로를 하나 더 찾을 수 있는 셈입니다. (1-3-2-4)

 

포드-풀커슨 알고리즘의 과정을 다시 정리해보자.

 

1. 각 간선의 용량을 입력받는다.

2. DFS 또는 BFS 를 이용하여 증가 경로를 찾는다.

3. 증가 경로 상의 간선 중 잔여 용량이 가장 낮은 것을 찾는다.

4. 잔여 용량 최소치만큼 소스에서 싱크까지 유량을 흘려보낸다.

5. 더 이상 증가 경로가 발견이 되지 않을 때까지 반복한다.

# Ford-Fulkerson 활용 문제

한 농사꾼은 소들이 충분한 물을 마시길 원했다. 그래서 농장에 우물에서 외양간을 잇는 N개의 배수관의 지도를 만들기로 했다. 농사꾼은 아주 다양한 크기의 배수관들이 완전히 우연한 방법으로 연결돼있음을 알았다. 농사꾼은 파이프를 통과하는 유량을 계산하고 싶다.

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
결과에 대한 그림

![결과1](https://user-images.githubusercontent.com/63089470/165764653-723ed452-dc45-4926-9ac0-463c607a8180.jpg)

---