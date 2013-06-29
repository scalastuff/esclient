# Elastic Search Client for Scala

The scala client is a thin wrapper around the java client. It features:

 - Asynchronous interface
 - Based on Scala 2.10 Futures
 - Single dispatch method (execute)
 - Type-safe Request / Response pairs using 'magnet' pattern
 
The signature of the execute method (slightly simplified):
  
   	package  org.scalastuff.esclient
  	import org.elasticsearch.client.Client
  	
 	implicit class ESClient(client : Client) extends AnyVal {
	  def execute[Request, Response](request: Request): Future[Response]
	}

### Usage

	 libraryDependencies += "org.scalastuff" %% "esclient" % "0.90.2"
	 
Note that the esclient versioning is aligned with the elastic search version. This is the second esclient release based on 
Elastic Search version 0.90.x.

### Sample

	import scala.concurrent.Await
	import scala.concurrent.Future
	import scala.concurrent.duration.DurationInt
	
	import org.elasticsearch.action.index.IndexRequest
	import org.elasticsearch.action.index.IndexResponse
	import org.elasticsearch.client.Client
	import org.elasticsearch.node.NodeBuilder.nodeBuilder
	import org.scalastuff.esclient.ESClient
	
	object TestES extends App {
	
	  val client : Client = 
	    nodeBuilder.node.client
	  
	  val response : Future[IndexResponse] = 
	    client.execute(new IndexRequest) 
	      
	  println("Document id: " + Await.result(response, 5 seconds).id)
	}
	
### License

This software is released under the Apache License, Version 2.0

http://www.apache.org/licenses/LICENSE-2.0.html
