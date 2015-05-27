

/**
 * Auto Generated Java Class.
 */
public class WeightedQuickUnionUF {

  private int[] size;
  private int[] parent;
  private int count;

  public WeightedQuickUnionUF(int N) {
    count = N;
    parent = new int[N];

    for (int i = 0; i < N; i++) {
      parent[i] = i;
    }

    size = new int[N];

    for (int i = 0; i < N; i++) {
      size[i] = 1;
    }
  }

  private int root(int i) {
    //System.out.println("root(i: " + i + ")");
    while (i != parent[i]) {
      i = parent[i];
    }
    return i;
  }

  public boolean connected(int p, int q) {
    return (root(p) == root(q));
  }

  public void union(int p, int q) {
    //System.out.println("union(p: " + p + ";q: " + q + ")");
    int rootOfP = root(p);
    int rootOfQ = root(q);

    if (rootOfP == rootOfQ) {
      return;
    }

    if (size[rootOfP] < size[rootOfQ]) {
      parent[rootOfP] = rootOfQ;
      size[rootOfQ] += size[rootOfP];
    } else {
      parent[rootOfQ] = rootOfP;
      size[rootOfP] += size[rootOfQ];
    }
    count--;

    //id[i] = j;
  }
}
