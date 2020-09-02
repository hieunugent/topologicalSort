import java.util.*;

class Program {
  public static List<Integer> topologicalSort(List<Integer> jobs, List<Integer[]> deps) {
    // Write your code here.
		JobGraph jobGraph = createJobGraph(jobs,deps);
    return  getOrderedJobs(jobGraph);
  }
	// create a graph that content the job and it dependency
	public static JobGraph createJobGraph (List <Integer> jobs, List<Integer[]> deps){
		JobGraph graph = new JobGraph(jobs);
		for (Integer[] dep : deps){
			graph.addDep(dep[0], dep[1]);
		}
		return graph;
	}
	// get the order job by the job has no dependcy.
	// run until the all node with no dependency is gone.
	// at each time add the no dependency node in the final order, must remove that node out of the dependency of other node
	// if no node with no prereqs exit, and. no edge, return the final order
	// else return the empty order since it must be asyclic graph.
	public static  List<Integer> getOrderedJobs(JobGraph graph){
		List<Integer> orderedJobs = new ArrayList<Integer>();
		List<JobNode> nodesWithNoPrereqs = new ArrayList<JobNode>();
		for(JobNode node: graph.nodes){
			if(node.numOfPrereqs == 0){
				nodesWithNoPrereqs.add(node);
			}
		}
		
		while(nodesWithNoPrereqs.size()>0){
			JobNode node = nodesWithNoPrereqs.get(nodesWithNoPrereqs.size()-1);
			nodesWithNoPrereqs.remove(nodesWithNoPrereqs.size()-1);
			orderedJobs.add(node.job);
			removeDeps(node, nodesWithNoPrereqs);
		}
		boolean graphHasEdges = false;
		for (JobNode node : graph.nodes){
			if(node.numOfPrereqs >0){
				graphHasEdges = true;
			}
		}
		
		return graphHasEdges ? new ArrayList<Integer>() : orderedJobs;
	}
	// remove dependency from a node, if the current node  depdency still have , get that dependency and remove that depend form current node
	public static void removeDeps(JobNode node, List<JobNode> nodesWithNoPrereqs){
		while(node.deps.size() > 0){
			JobNode dep = node.deps.get(node.deps.size()-1);
			node.deps.remove(node.deps.size()-1);
			dep.numOfPrereqs--;
			if(dep.numOfPrereqs == 0 )nodesWithNoPrereqs.add(dep);
		}
	}
	// define Object JobGraph with contain a graph of JobNode.
	// a Node will prepresent at a list of JobNode, 
	// each Jobnode will map by their Prereqs  or Deps 
	// add nDeps increase the numOfPrereqs
	// an addDep function will add the dependency of each Jobs
	// an addNode funciton will add a new node in the graph and in the List node
	// a getNode will get a node in the graph add new node if not in graph or return the node.
	static class JobGraph{
		public List<JobNode> nodes;
		public Map<Integer, JobNode> graph;
		
		public JobGraph(List<Integer> jobs){
			nodes = new ArrayList<JobNode> ();
			graph = new HashMap<Integer, JobNode>();
			for (Integer job : jobs){
				addNode(job);
			}
		}
		public void addDep(Integer job, Integer dep)
		{
			JobNode jobNode = getNode(job);
			JobNode depNode = getNode(dep);
			jobNode.deps.add(depNode);
			depNode.numOfPrereqs++;
		}
		
		
		public void addNode(Integer job){
			graph.put(job, new JobNode(job));
			nodes.add(graph.get(job));
		}
		public JobNode getNode(Integer job){
	    if(!graph.containsKey(job)) addNode(job);
			return graph.get(job);
		}
		
	}
	
	// define a object class that is a node with containt Jobs and list of it dependency, and number of prerequisite deps its need
	static class JobNode{
		public Integer job;
		public List<JobNode> deps;
		public Integer numOfPrereqs;
		public JobNode(Integer job){
			this.job = job;
			deps = new ArrayList<JobNode>();
			numOfPrereqs = 0;
		}
	}
}
