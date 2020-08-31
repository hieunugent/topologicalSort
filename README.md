# topologicalSort
- given a list of arbitrary job that need to be completed ;
- these jobs are represented bu distinct integers.
- you are also given a lisr of dependencies
- a dependency is represented as a pair of jobs where the first job js a prerequisite of the second one
- in order words the second job depends on the first one 
it can only be completed once the first job is complete
- wite a function that takes in a lisr of job and a llist of dependencies and return  a list containing a valid order in which the given jobs can be completed
- if no such order exists, the function should return an empty array
