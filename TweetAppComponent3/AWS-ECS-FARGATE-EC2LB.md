

step 1: 
	In Ecs create cluster -> network only -> give cluster name 
	->create vpc -> select create 

step 2: 
	In ECS create task definition -> select launch type fargate
	->Give task defination name -> select task role as none 
	-> task memory 1gb -> task cpu 0.5 ->  click Add container
	->Give container name -> In image give docker hub image link 
	->In port mapping give port number of app -> Add -> create 

Step 3: 
	Goto load balancers ->create a new load balancer ->
	select Application load balancer -> give name to load balancer
	-> select internet facing -> ipv4 -> select created vpc in ecs cluster
	->select all mappings after selcting vpc created 
	->create a new security group -> give a name -> select created vpc in ecs cluster
	->add inbound & outbound rules as(all traffic) -> create.
	->In listener and routing give application port mapping  and create target groups for each port mapping
	->In create target groups select ip address ->give target group name -> 
	-> give application port number and ipaddress -> select vpc that we created in ECS
	->create

step 4: select cluster we created -> select services and select create -> select fargate
	->select task definition we created ->select cluster we created -> Give service name 
	-> number task one -> click nextstep -> select application load balancer ->
	-> select load balancer we created -> select container port -> select add load balancer
	->select production lister port ->select target group name -> click on next
	
	-> In vpc select vpc created and both subnets 
	-> IN select security group ->click existing security and select security group we created.
