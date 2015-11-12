import java.util.*;
import java.io.*;

class Node {
    public final String value;
    public int pathCost;
    public Edge[] adjacencies;
    public int number_edges;
    public Node parent;

    public Node(String val) {

        value = val;

    }

    public String getValue(String value) {
        return value;
    }

    public Node(Node node) {
        int i = 0;
        if (node.adjacencies != null) {
            adjacencies = new Edge[node.adjacencies.length];
            for (Edge e : node.adjacencies) {
                adjacencies[i++] = e;
            }
        }
        number_edges = 0;
        value = node.value;
        pathCost = node.pathCost;
        parent = node.parent;
    }

    public String toString() {
        return value + " " + pathCost;
    }
}

 class Edge {
     public final int cost;
     public final Node target;
     public final ArrayList<String> timeRange;
     public int number_ranges;
     public boolean visited;


     public Edge(){
         cost = 0;
         number_ranges = 0;
         target = null;
         timeRange = null;
         visited = false;

     }

     public Edge(Node targetNode, int costVal) {
         cost = costVal;
         number_ranges = 0;
         target = targetNode;
         timeRange = null;
         visited = false;
     }

     public Edge(Node targetNode, int costVal, ArrayList<String> range, int range_size) {
         cost = costVal;
         target = targetNode;
         number_ranges = range_size;
         timeRange = new ArrayList<String>();
         for (String r : range) {
             timeRange.add(r);
         }
         visited = false;

     }
 }
class NodeSort implements Comparator<Edge> {
    public int compare(Edge o1, Edge o2) {
        return o1.target.value.compareTo(o2.target.value);
    }
}

public class waterFlow {

        public static void main(String[] args) {

          ArrayList<String> output = new ArrayList<String>();
          String Filename = args[1];
          if(!Filename.toLowerCase().contains(".txt"))
            Filename = Filename + ".txt";
            try {
               	File file = new File(Filename);
                BufferedReader in = new BufferedReader(new FileReader(file));
                int number_cases = Integer.parseInt(in.readLine());
                for (int input_number = 1; input_number <= number_cases; input_number++) {
                    ArrayList<String> input = new ArrayList<String>();
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.isEmpty() == true) {
                            break;
                        }
                        input.add(line);
                    }

                    Iterator<String> itr = input.iterator();
                    itr.next();
                    ArrayList<String> splited = new ArrayList<String>();

                    int i = 0;

                    while (i <= 2) {
                        String element = itr.next();
                        for (String s : element.split(" ")) {
                            splited.add(s);
                        }
                        i++;
                    }

                    ArrayList<Node> nodes = new ArrayList<Node>();

                    for (int j = 0; j < splited.size(); j++) {
                        Node node = new Node(splited.get(j)); // assuming you have a default constructor
                        nodes.add(node);
                    }

                    ArrayList<Node> nodes_source = new ArrayList<Node>();
                    ArrayList<Node> nodes_desti = new ArrayList<Node>();

                    for (int k = 5; k < input.size() - 1; k++) {
                        for (Node node : nodes) {
                            String A = node.value;
                            String s[] = input.get(k).split("\\s+");
                            String B = s[0];
                            if (A.equals(B)) {
                                nodes_source.add(node);
                                break;
                            }
                        }
                    }

                    for (int k = 5; k < input.size() - 1; k++) {
                        for (Node node : nodes) {
                            String A = node.value;
                            String s[] = input.get(k).split("\\s+");
                            String B = s[1];
                            if (A.equals(B)) {
                                nodes_desti.add(node);
                                break;
                            }
                        }
                    }

                    for (Node node : nodes_source) {
                        node.adjacencies = new Edge[Collections.frequency(nodes_source, node)];
                    }

                    int number_of_paths = Integer.parseInt(input.get(4));

                    int k = 5;
                    for (int l = 0; l < number_of_paths; l++, k++) {
                        if (l == 0 || nodes_source.get(l).number_edges==0) {
                            nodes_source.get(l).number_edges+=1;
                            if(Integer.parseInt(input.get(k).split("\\s+")[3])==0) {
                                nodes_source.get(l).adjacencies[0] = new Edge(nodes_desti.get(l), Integer.parseInt(input.get(k).split("\\s+")[2]));
                            }
                            else{
                                nodes_source.get(l).adjacencies[0] = new Edge(nodes_desti.get(l), Integer.parseInt(input.get(k).split("\\s+")[2]), new ArrayList<String>(), Integer.parseInt(input.get(k).split("\\s+")[3]));
                            }
                        } else {
                            if(Integer.parseInt(input.get(k).split("\\s+")[3])==0) {
                                nodes_source.get(l).adjacencies[nodes_source.get(l).number_edges] = new Edge(nodes_desti.get(l), Integer.parseInt(input.get(k).split("\\s+")[2]));
                            }
                            else{
                                nodes_source.get(l).adjacencies[nodes_source.get(l).number_edges] = new Edge(nodes_desti.get(l), Integer.parseInt(input.get(k).split("\\s+")[2]), new ArrayList<String>(),Integer.parseInt(input.get(k).split("\\s+")[3]));
                            }
                            nodes_source.get(l).number_edges+=1;
                        }
                    }

                    int x = 5; int edge_number = 0;
                    for (int l = 0; l < number_of_paths; l++, x++) {
                        if(Integer.parseInt(input.get(x).split("\\s+")[3])==0){
                            continue;
                        }
                        for(int b = 0 ; b < nodes_source.get(l).number_edges; b++) {
                            if (nodes_source.get(l).adjacencies[b].timeRange != null && !nodes_source.get(l).adjacencies[b].visited) {
                                edge_number = b;
                                break;
                            }
                        }
                        for (int con = 1, lo = 4;con <=Integer.parseInt(input.get(x).split("\\s+")[3]); con++, lo++){
                            nodes_source.get(l).adjacencies[edge_number].timeRange.add(input.get(x).split("\\s+")[lo]);
                        }
                        nodes_source.get(l).adjacencies[edge_number].visited = true;
                    }

                    Node source = null;
                    List<Node> destination = new ArrayList<Node>();

                    for (Node node : nodes) {
                        String A = node.value;
                        String s = input.get(1);
                        String B = s;
                        if (A.equals(B)) {
                            source = node;
                            break;
                        }
                    }
                    String s[] = input.get(2).split("\\s+");
                    for (int count = 0; count < s.length; count++) {
                        for (Node node : nodes) {
                            String A = node.value;
                            String B = s[count];
                            if (A.equals(B)) {
                                destination.add(node);
                                break;
                            }
                        }
                    }

                    int start = Integer.parseInt((input.get(input.size()-1)));

                    switch (input.get(0)) {
                        case "BFS":
                            output.add(BreadthFirstSearch(source, destination, start));
                            break;
                        case "DFS":
                            output.add(DepthFirstSearch(source, destination, start));
                            break;
                        case "UCS":
                            output.add(UniformCostSearch(source, destination, start));
                            break;
                    }
                }
            } catch (Exception FileNotFoundException) {
                System.out.println(FileNotFoundException);
            }
            try
            {
                BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
                int p = 1;
                for(String ou : output) {
                    out.write(ou);
                    if (p < output.size()) {
                        out.newLine();
                    }
                    p++;
                }
                out.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }

        }

        public static String UniformCostSearch(Node source, List<Node> destination, int start) {

            source.pathCost = start;
            PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                    new Comparator<Node>() {

                        // override compare method
                        public int compare(Node i, Node j) {
                            if ((i.pathCost > j.pathCost)) {
                                return 1;
                            }

                            else if (i.pathCost < j.pathCost) {
                                return -1;
                            }

                            else {
                                return i.value.compareTo(j.value);
                            }
                        }
                    }

            );

            queue.add(source);
            Set<Node> explored = new HashSet<Node>();

            do {

                Node current = queue.poll();
                explored.add(current);

                for (Node desti : destination) {
                    if (current.value.equals(desti.value)) {
                        desti.parent = current.parent;
                        desti.pathCost = (current.pathCost)%24;
                        return desti.toString();
                    }
                }

                if(current.adjacencies==null){
                    continue;
                }

                int flag = 0;
                for (Edge e : current.adjacencies) {
                    if(e.timeRange != null) {
                        for(int bcount = 0 ; bcount < e.number_ranges; bcount++){
                            if (Between((current.pathCost)%24, Integer.parseInt(e.timeRange.get(bcount).split("-")[0]),Integer.parseInt(e.timeRange.get(bcount).split("-")[1])))
                            {
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 1) {
                            flag = 0;
                            continue;
                        }
                    }
                    Node child = e.target;
                    int cost = e.cost;
                    if(queue.contains(child))
                    {
                        if(child.pathCost < (current.pathCost + cost))
                            continue;
                    }
                    child.pathCost = current.pathCost + cost;
                    if(!explored.contains(child) && !queue.contains(child)){
                        child.parent = current;
                        queue.add(child);
                    }
                    else if((queue.contains(child))&&(child.pathCost>current.pathCost)){
                        child.parent=current;
                        queue.remove(child);
                        queue.add(child);
                    }
                }
            } while (!queue.isEmpty());
            return "None";
        }

        public static Boolean Between(int value, int left, int right)
        {

            return value >= left && value <= right;
        }

        public static String BreadthFirstSearch(Node source, List<Node> destination, int start) {

            List<Node> list = new ArrayList<Node>();
            source.pathCost = start;
            Queue<Node> queue = new LinkedList<Node>();
            queue.add(source);
            Set<Node> explored = new HashSet<Node>();
            explored.add(source);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                explored.add(current);
                if (current.adjacencies !=null) {
                    List<Edge> sortlist = Arrays.asList(current.adjacencies);
                    Collections.sort(sortlist, new NodeSort());
                    current.adjacencies = sortlist.toArray(new Edge[sortlist.size()]);

                    for (Edge e : current.adjacencies) {
                        Node child = e.target;
                        if(!queue.contains(child))
                        child.pathCost = current.pathCost + 1;
                        for (Node desti : destination) {
                            if (child.value.equals(desti.value)) {
                                desti.pathCost = (child.pathCost)%24;
                                return desti.toString();
                            }
                        }
                        if (!queue.contains(child) && !explored.contains(child)) {
                            child.parent = current;
                            queue.add(child);
                        }

                    }
                }
            }
            return "None";
        }

        public static String DepthFirstSearch(Node source, List<Node> destination, int start) {

        Stack<Node> stack = new Stack<Node>();
        Set<Node> explored = new HashSet<Node>();
        source.pathCost = start;
        stack.push(source);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            explored.add(current);
            for (Node desti : destination) {
                if (current.value.equals(desti.value)) {
                    desti.pathCost = (current.pathCost)%24;
                    return desti.toString();
                }
            }
            if(current.adjacencies == null)
                continue;
            List<Edge> list = Arrays.asList(current.adjacencies);
            Collections.sort(list, new NodeSort());
            Collections.reverse(list);
            current.adjacencies = list.toArray(new Edge[list.size()]);
            for (Edge e : current.adjacencies) {
                Node child = e.target;
                if (!explored.contains(child)) {
                    child.pathCost = current.pathCost + 1;
                    child.parent = current;
                    stack.push(child);
                }
            }
        }
        return "None";
    }
}
